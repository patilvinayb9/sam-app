package com.edge.app.modules.profile;

import com.edge.app.modules.common.AppConstants;
import com.edge.app.modules.communications.CommunicationSender;
import com.edge.app.modules.expectations.ExpectationsService;
import com.edge.app.modules.language.LangMessages;
import com.edge.app.modules.profile.dto.ProfileDetailsDto;
import com.edge.app.modules.profile.nonSecure.ProfileDetails;
import com.edge.app.modules.profile.profileSecure.SecureProfileDetails;
import com.edge.app.modules.profile.profileSecure.SecureProfileDetailsService;
import com.edge.app.modules.trackDetails.TrackDetailsService;
import com.edge.app.modules.wallet.MeetMateWalletService;
import com.edge.app.modules.wallet.ProductInfoEnum;
import com.edge.core.config.CoreConstants;
import com.edge.core.exception.AppRuntimeException;
import com.edge.core.modules.auth.SpringSecurityUtil;
import com.edge.core.modules.files.FileService;
import com.edge.core.modules.security.EdgeUserDetails;
import com.edge.core.modules.user.UserService;
import com.edge.core.security.Encrypter;
import com.edge.core.utils.CoreDateUtils;
import com.edge.repositories.EdgeUserDetailsRepository;
import com.edge.repositories.ProfileDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

@Component
public class ProfileUpdateServiceImpl implements ProfileUpdateService{

    private static final Logger logger = LoggerFactory.getLogger(ProfileUpdateServiceImpl.class);

    @Autowired
    private LangMessages langMessages;

    @Autowired
    private CommunicationSender meetMateCommunicationService;

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
    private UserService userService;

    @Autowired
    private MeetMateWalletService meetMateWalletService;

    @Autowired
    private ExpectationsService expectationsService;

    @Autowired
    private TrackDetailsService trackDetailsService;

    @Override
    @Transactional
    public void uploadImage(MultipartFile file, String imageType, String internalId) throws Exception {
        String uploadPath = fileService.uploadImage(AppConstants.IMAGE_BUCKET, internalId, imageType, file, 600);
        handleImageUploaded(internalId, imageType, uploadPath);
    }

    @Override
    @Transactional
    public void updateMyProfile(ProfileDetailsDto profileDetailsDto) throws Exception {
        if(profileDetailsDto.getProfileDetails().getProfileId() == null){
            createProfile(profileDetailsDto);
        }else{
            updateProfileById(
                    profileDetailsDto,
                    SpringSecurityUtil.getLoggedInInternalId());
            profileDetailsDto.setCpassword(null);
        }
    }

    private RegisteredProfileDetailsDto createProfile(ProfileDetailsDto profileDetailsDto) throws Exception {
        String loggedInInternalId = SpringSecurityUtil.getLoggedInInternalId();
        EdgeUserDetails userById = userService.getUserById(loggedInInternalId);
        profileDetailsDto = createProfileForId(profileDetailsDto, loggedInInternalId, userById);
        trackDetailsService.trackRegistered(profileDetailsDto, profileDetailsDto.getProfileDetails().getProfileId());
        RegisteredProfileDetailsDto registeredProfileDetailsDto = new RegisteredProfileDetailsDto(profileDetailsDto);
        return registeredProfileDetailsDto;
    }

    @Override
    @Transactional
    public ProfileDetailsDto createProfileForId(ProfileDetailsDto profileDetailsDto, String internalId, EdgeUserDetails userDetails) throws Exception {
        try {

            // DERIVE
            ProfileDetails profileDetails = profileDetailsDto.getProfileDetails();
            profileDetails.setGender(userDetails.getGender());
            profileDetails.deriveValues();

            SecureProfileDetails secureDetails = profileDetailsDto.getSecureDetails();
            secureDetails.deriveValues();

            if (!CoreDateUtils.isAdult(profileDetails.getBirthDate())) {
                throw new AppRuntimeException(null, langMessages.msg_above18Age());
            }

            if (secureProfileDetailsService.checkIfExistingCompleteNumber(secureDetails.getCompleteNumber())) {
                throw new AppRuntimeException(null, langMessages.msg_invalidPhoneNumber(secureDetails.getCompleteNumber()));
            }

            String encCompleteNumber = userDetails.getCompleteNumber();
            String completeNumber = encrypter.decrypt(encCompleteNumber);

            if (! completeNumber.equals(secureDetails.getCompleteNumber())) {
                throw new AppRuntimeException(null, "Please enter registered mobile number");
            }

            // Saving in Database Starts

            profileDetails.setInternalId(internalId);
            secureDetails.setInternalId(internalId);

            profileDetails.setProfileId(userDetails.getProfileId());
            profileDetails.setMembershipValidTill(CoreDateUtils.addDays(new Date(), ProductInfoEnum.REGISTRATION.getDays()));
            profileDetails.setMembershipPlan("");
            profileDetails.setMembershipRenewDate(new Date());
            secureDetails.setProfileId(userDetails.getProfileId());

            List<String> errors = profileDetails.validate();
            if (errors == null || errors.size() == 0) {

                profileDetailsRepo.save(profileDetails);
                secureProfileDetailsService.saveEncoded(secureDetails);
                meetMateWalletService.createWallet(internalId);

                if (profileDetailsDto.getExpectations() != null) {
                    profileDetailsDto.getExpectations().setInternalId(internalId);
                    expectationsService.setExpectations(profileDetailsDto.getExpectations());
                }

                // Sending SMS - Should be at last
                meetMateCommunicationService.registrationSuccess(profileDetails, secureDetails);

                logger.debug("Account Successfully created for {} {}", secureDetails.getCompleteNumber(), profileDetails.getInternalId());


            } else {
                throw new AppRuntimeException(null, langMessages.msg_errorWhileProcessing(), "RSI.register", errors);
            }

        } catch (DuplicateKeyException ex) {
            ex.printStackTrace();
            throw new AppRuntimeException(ex, langMessages.msg_errorWhileProcessing(), ex.getMessage(), null);
        } catch (AppRuntimeException ex) {
            ex.printStackTrace();
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new AppRuntimeException(ex, ex.getMessage());
        }


        return profileDetailsDto;
    }

    @Override
    @Transactional
    public ProfileDetails updateProfileById(
            ProfileDetailsDto profileDetailsDto, String internalId
    ) throws Exception {
        // Data Variable Extraction

        ProfileDetails profileDetails = profileDetailsDto.getProfileDetails();

        SecureProfileDetails secureDetails = profileDetailsDto.getSecureDetails();
        secureDetails.deriveValues();

        profileDetails.setInternalId(internalId);
        secureDetails.setInternalId(internalId);

        ProfileDetails profileDetailsDb = profileDetailsRepo.findById(internalId).get();

        SecureProfileDetails secureDetailsDb = secureProfileDetailsService.findById(internalId);
        secureDetailsDb.deriveValues();

        // Birth Date
        if (!CoreDateUtils.isAdult(profileDetails.getBirthDate())) {
            throw new AppRuntimeException(null, langMessages.msg_above18Age());
        }


        // Gender can not be updated
        profileDetails.setGender(profileDetailsDb.getGender());

        // Membership Validity Can Not be Updated
        profileDetails.setMembershipValidTill(profileDetailsDb.getMembershipValidTill());

        // Membership Plan Can Not be Updated
        profileDetails.setMembershipPlan(profileDetailsDb.getMembershipPlan());

        // Membership Renewal Can Not be Updated
        profileDetails.setMembershipRenewDate(profileDetailsDb.getMembershipRenewDate());

        // Admin Action Can Not be Updated
        profileDetails.setAdminAction(profileDetailsDb.getAdminAction());

        // Profile Id Can Not be Updated
        secureDetails.setProfileId(secureDetailsDb.getProfileId());

        // Cell
        secureDetails.setCompleteNumber(secureDetailsDb.getCompleteNumber());

        String oldCompleteNumber = secureDetailsDb.getCompleteNumber();
        String newCompleteNumber = "+91" + profileDetailsDto.getNewCell();

        if(profileDetailsDto.isUpdateCell()){
            userService.updateCompleteNumber(oldCompleteNumber, newCompleteNumber, profileDetailsDto.getVerificationCode());
            // verified
            secureDetails.setCell(profileDetailsDto.getNewCell());
            secureDetails.setCompleteNumber(newCompleteNumber);
        }

        // Verify Verification Code - Has to be the last check

        List<String> errors = profileDetails.validate();
        if (errors != null && errors.size() != 0) {
            throw new AppRuntimeException(null, langMessages.msg_errorWhileProcessing(), langMessages.msg_pleaseTryAfterSomeTime(), errors);
        } else {

            // Save Now
            profileDetails.deriveValues();
            profileDetailsRepo.save(profileDetails);

            secureDetails.deriveValues();
            secureProfileDetailsService.saveEncoded(secureDetails);

        }


        return profileDetails;
    }

    @Override
    @Transactional
    public void handleImageUploaded(String internalId, String imageType, String uploadPath) {

        deleteExisitingImageIfAny(internalId, imageType);
        Date now = new Date();

        mongoTemplate.updateFirst(query(where("internalId").is(internalId)), update(imageType, uploadPath), ProfileDetails.class);

    }

    @Override
    @Transactional
    public String deleteAccount(String completeNumber) {

        completeNumber = completeNumber.toLowerCase();
        completeNumber = encrypter.encrypt(completeNumber);
        EdgeUserDetails userDetails = edgeUserDetailsRepository.findByCompleteNumber(completeNumber);
        if (userDetails != null) {
            String internalId = userDetails.getInternalId();
            return deleteProfileByInternalId(internalId);
        } else {
            throw new AppRuntimeException(null, langMessages.msg_invalidPhoneNumber(completeNumber));
        }

    }

    @Override
    @Transactional
    public String deleteProfileByProfileId(String profileId) {
        Optional<ProfileDetails> byProfileId = profileDetailsRepo.findByProfileId(profileId);
        if (byProfileId.isPresent()) {
            String internalId = byProfileId.get().getInternalId();
            return deleteProfileByInternalId(internalId);
        } else {
            throw new AppRuntimeException(null, langMessages.msg_invalidProfileId(profileId));
        }
    }

    private String deleteProfileByInternalId(String internalId) {
        Optional<ProfileDetails> byId = profileDetailsRepo.findById(internalId);

        if (byId.isPresent()) {

            ProfileDetails profileDetails = byId.get();
            String profileId = profileDetails.getProfileId();

            SecureProfileDetails secureProfileDetails = secureProfileDetailsService.findById(internalId);
            secureProfileDetails.deriveValues();
            String completeNumber = secureProfileDetails.getCompleteNumber();

            String deleted = CoreConstants.DELETED_IDENTIFIER;
            String newDelSTring = deleted + UUID.randomUUID().toString();

            EdgeUserDetails userDetails = edgeUserDetailsRepository.findByInternalId(internalId);
            if (userDetails != null) {
                userDetails.setPassword("Null");
                userDetails.setConfirmPwd("Null");
                userDetails.setVerificationCode("Null");
                userDetails.setCompleteNumber(newDelSTring);
                userDetails.setEnabled(false);
                userDetails.setInactivationReason("ProfileDeleted");
                edgeUserDetailsRepository.save(userDetails);
            }

            if (secureProfileDetails != null) {

                secureProfileDetails.setCell(newDelSTring);
                secureProfileDetails.setCompleteNumber(newDelSTring);
                secureProfileDetails.setCellCountry(deleted);
                secureProfileDetails.setCellOthers(deleted);
                secureProfileDetails.setLastName(deleted);

                secureProfileDetailsService.saveEncoded(secureProfileDetails);
            }

            meetMateCommunicationService.accountDeleted(profileId, completeNumber);

            return profileId;
        } else {
            throw new AppRuntimeException(null, langMessages.msg_invalidPhoneNumber(""));
        }
    }

    @Override
    @Transactional
    public void saveProfileDetails(ProfileDetails profileDetails) {
        profileDetailsRepo.save(profileDetails);
    }

    @Override
    @Transactional
    public String deletePic(String profileId) {
        Optional<ProfileDetails> byProfileId = profileDetailsRepo.findByProfileId(profileId);
        if (byProfileId.isPresent()) {
            ProfileDetails profileDetails = byProfileId.get();

            deleteImageIfAny(profileDetails.getProfilePic());
            profileDetails.setProfilePic("NA");

            deleteImageIfAny(profileDetails.getAlbumImg1());
            profileDetails.setAlbumImg1("NA");

            deleteImageIfAny(profileDetails.getAlbumImg2());
            profileDetails.setAlbumImg2("NA");

            deleteImageIfAny(profileDetails.getAlbumImg3());
            profileDetails.setAlbumImg3("NA");

            profileDetailsRepo.save(profileDetails);
            return "Success";

        }
        return "Failed";
    }

    private String getUrlByFileType(String internalId, String fileType) {

        String retValue = "";
        Query query = query(where("internalId").is(internalId));
        query.fields().include(fileType);
        ProfileDetails profileDetails = mongoTemplate.findOne(query, ProfileDetails.class);
        switch (fileType) {
            case "profilePic":
                retValue = profileDetails.getProfilePic();
                break;
            case "albumImg1":
                retValue = profileDetails.getAlbumImg1();
                break;
            case "albumImg2":
                retValue = profileDetails.getAlbumImg2();
                break;
            case "albumImg3":
                retValue = profileDetails.getAlbumImg3();
                break;
        }
        return retValue;

    }

    private void deleteExisitingImageIfAny(String internalId, String fileType) {
        String imagePath = getUrlByFileType(internalId, fileType);
        deleteImageIfAny(imagePath);
    }

    private void deleteImageIfAny(String imagePath) {
        if (imagePath != null && imagePath.isEmpty() && !imagePath.equals("NA")) {
            fileService.deleteFile(imagePath);
        }
    }

    @Override
    @Transactional
    public String deleteProfileById(String internalId) {
        return deleteProfileByInternalId(internalId);
    }

}
