package com.edge.app.modules.profile;

import com.edge.app.modules.common.AppConstants;
import com.edge.app.modules.language.LangMessages;
import com.edge.app.modules.profile.dto.ProfileDetailsDto;
import com.edge.app.modules.profile.nonSecure.ProfileDetails;
import com.edge.app.modules.profile.profileSecure.SecureProfileDetails;
import com.edge.app.modules.profile.profileSecure.SecureProfileDetailsService;
import com.edge.app.modules.profileConnection.ProfileConnection;
import com.edge.app.modules.profileConnection.ProfileConnectionService;
import com.edge.core.config.CoreConstants;
import com.edge.core.exception.AppRuntimeException;
import com.edge.core.modules.auth.SpringSecurityUtil;
import com.edge.core.modules.files.FileService;
import com.edge.core.modules.security.EdgeUserDetails;
import com.edge.core.security.Encrypter;
import com.edge.core.utils.CoreDateUtils;
import com.edge.repositories.EdgeUserDetailsRepository;
import com.edge.repositories.ProfileDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProfileReadServiceImpl implements ProfileReadService{

    private static final Logger logger = LoggerFactory.getLogger(ProfileReadServiceImpl.class);

    @Autowired
    private LangMessages langMessages;

//    @Autowired
//    private CommunicationSender meetMateCommunicationService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private FileService fileService;

    @Autowired
    private ProfileDetailsRepository profileDetailsRepo;

    @Autowired
    private SecureProfileDetailsService secureProfileDetailsService;

    @Autowired
    private EdgeUserDetailsRepository edgeUserDetailsRepository;

    @Autowired
    private Encrypter encrypter;

    @Autowired
    private ProfileConnectionService profileConnectionService;

    @Override
    public FileSystemResource getImage(@PathVariable("entityId") String entityId, @PathVariable("fileType") String fileType, @PathVariable("fileName") String fileName) {
        return new FileSystemResource(fileService.getFile(entityId, fileType, fileName));
    }

    @Override
    public ProfileDetailsDto getProfileDetails(String internalId) {
        ProfileDetails profileDetails = getProfileDetailsById(internalId);
        SecureProfileDetails secureDetails = getSecureDetailsById(internalId);

        ProfileDetailsDto profileDetailsDto = new ProfileDetailsDto();
        profileDetailsDto.setProfileDetails(profileDetails);
        profileDetailsDto.setSecureDetails(secureDetails);
        return profileDetailsDto;
    }

    @Override
    public ProfileDetails getProfileDetailsById(String internalId) {
        return profileDetailsRepo.findById(internalId).get();
    }

    @Override
    public SecureProfileDetails getSecureDetailsById(String internalId) {
        return secureProfileDetailsService.findById(internalId);
    }


    @Override
    public String getProfileIdByCompleteNumber(String completeNumber) {

        completeNumber = completeNumber.toLowerCase();
        completeNumber = encrypter.encrypt(completeNumber);
        EdgeUserDetails userDetails = edgeUserDetailsRepository.findByCompleteNumber(completeNumber);
        if (userDetails != null) {
            String internalId = userDetails.getInternalId();
            return profileDetailsRepo.findById(internalId).get().getProfileId();
        } else {
            throw new AppRuntimeException(null, langMessages.msg_invalidPhoneNumber(completeNumber));
        }

    }


    @Override
    public ProfileDetails getProfileByProfileId(String profileId) {
        Optional<ProfileDetails> byProfileId = profileDetailsRepo.findByProfileId(profileId);
        if (byProfileId.isPresent()) {
            return byProfileId.get();
        } else {
            throw new AppRuntimeException(null, langMessages.msg_invalidProfileId(profileId));
        }
    }


    @Override
    public String sendRandomSms(String profileId, String smsText, Boolean smsMode) {


        try {
            Optional<ProfileDetails> byProfileId = profileDetailsRepo.findByProfileId(profileId);
            if (byProfileId != null) {
                ProfileDetails profileDetails = byProfileId.get();
                SecureProfileDetails secureProfileDetails = secureProfileDetailsService.findById(profileDetails.getInternalId());

                String completeNumber = secureProfileDetails.getCompleteNumber();
                if (completeNumber == null || completeNumber.startsWith(CoreConstants.DELETED_IDENTIFIER)) {
                    return CoreConstants.DELETED_IDENTIFIER;
                }

                if (smsMode) {
                    //meetMateCommunicationService.sendRandomSms(smsText, completeNumber, profileId, profileDetails.getFirstName());
                    return "COUNT";
                } else {
                    return secureProfileDetails.getCompleteNumber();
                }

            } else {
                return "";
            }
        } catch (Exception ex) {
            throw new AppRuntimeException(ex, ex.getMessage());
        }

    }

    @Override
    public Iterable<ProfileDetails> getAllActiveProfileDetails() {
        return profileDetailsRepo.findByMembershipValidTillGreaterThanEqual(CoreDateUtils.today());
    }


    @Override
    public String[] evaluateRandomExpression(String queryClause) {

        Query query = new BasicQuery(" { "
                + queryClause
                + " } ");

        query.limit(AppConstants.MAX_WALL_SIZE);
        List<ProfileDetails> list = mongoTemplate.find(query, ProfileDetails.class);

        List<String> itemList = list.stream().map(ProfileDetails::getProfileId).collect(Collectors.toList());

        String[] itemsArray = new String[itemList.size()];
        itemsArray = itemList.toArray(itemsArray);

        return itemsArray;
    }

    @Override
    public Optional<ProfileDetails> searchById(String internalId) {
        return profileDetailsRepo.findById(internalId.toUpperCase());
    }

    @Override
    public Optional<ProfileDetails> searchByProfileId(String profileId) {
        return profileDetailsRepo.findByProfileId(profileId.toUpperCase());
    }

    @Override
    public List<ProfileDetailsDto> searchDtoById(String profileId) {
        List<ProfileDetailsDto> profileDetailsDtoList = new ArrayList<ProfileDetailsDto>();

        profileId = profileId.toUpperCase();

        ProfileDetailsDto profileDetailsDto = new ProfileDetailsDto();

        Optional<ProfileDetails> profileDetailsOptional = searchByProfileId(profileId);

        if (!profileDetailsOptional.isPresent()) {
            throw new AppRuntimeException(null, langMessages.msg_invalidProfileId(profileId));
        }

        ProfileDetails profileDetails = profileDetailsOptional.get();
        String internalId = profileDetails.getInternalId();
        profileDetailsDto.setProfileDetails(profileDetails);

        String loggedInInternalId = SpringSecurityUtil.getLoggedInInternalId();
        if (loggedInInternalId != null) {
            ProfileConnection profileConnection = profileConnectionService.getIfConnectionExistsById(internalId, loggedInInternalId);
            profileDetailsDto.setProfileConnection(profileConnection);
        }

        profileDetailsDtoList.add(profileDetailsDto);
        return profileDetailsDtoList;
    }


}
