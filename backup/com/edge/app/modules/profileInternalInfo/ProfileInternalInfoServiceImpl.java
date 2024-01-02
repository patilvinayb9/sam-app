package com.edge.app.modules.profileInternalInfo;

import com.edge.app.modules.common.AppConstants;
import com.edge.app.modules.common.ConnectionStatusEnum;
import com.edge.app.modules.profile.dto.ProfileDetailsDto;
import com.edge.app.modules.profile.nonSecure.ProfileDetails;
import com.edge.app.modules.profileConnection.ProfileConnection;
import com.edge.repositories.ProfileInternalInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
public class ProfileInternalInfoServiceImpl implements ProfileInternalInfoService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ProfileInternalInfoRepository profileInternalInfoRepo;

    @Override
    public List<ProfileDetailsDto> loadRemovedProfilesById(String internalId) {

        List<ProfileDetailsDto> profileDetailsDtos = new ArrayList();
        ProfileConnection dummyConnection = new ProfileConnection();
        dummyConnection.setConnectionStatus(ConnectionStatusEnum.Removed);

        Query query = new BasicQuery("{ internalId:{ $in: [ " + getRemovedProfiles(internalId) + " ] }}").limit(AppConstants.MAX_REMOVED_SIZE);
        List<ProfileDetails> list = mongoTemplate.find(query, ProfileDetails.class); //setMaxResults(AppConstants.MAX_REMOVED_SIZE);

        list.forEach(curr -> {
            ProfileDetailsDto dto = new ProfileDetailsDto();
            dto.setProfileDetails(curr);
            dto.setProfileConnection(dummyConnection);
            profileDetailsDtos.add(dto);
        });

        return profileDetailsDtos;
    }

    @Override
    public List<ProfileDetailsDto> loadShortlistedProfilesById(String internalId) {

        List<ProfileDetailsDto> profileDetailsDtos = new ArrayList();
        ProfileConnection dummyConnection = new ProfileConnection();
        dummyConnection.setConnectionStatus(ConnectionStatusEnum.Shortlisted);

        Query query = new BasicQuery("{ internalId:{ $in: [ " + getShortlistedProfiles(internalId) + " ] }}").limit(AppConstants.MAX_SHORTLISTED_SIZE);
        List<ProfileDetails> list = mongoTemplate.find(query, ProfileDetails.class); //setMaxResults(AppConstants.MAX_REMOVED_SIZE);

        list.forEach(curr -> {
            ProfileDetailsDto dto = new ProfileDetailsDto();
            dto.setProfileDetails(curr);
            dto.setProfileConnection(dummyConnection);
            profileDetailsDtos.add(dto);
        });

        return profileDetailsDtos;
    }

    @Override
    public List<ProfileDetailsDto> loadBuyContactsProfilesById(String internalId) {

        List<ProfileDetailsDto> profileDetailsDtos = new ArrayList();
        ProfileConnection dummyConnection = new ProfileConnection();
        dummyConnection.setConnectionStatus(ConnectionStatusEnum.ContactViewed);

        Query query = new BasicQuery("{ internalId:{ $in: [ " + getBuyContactsProfiles(internalId) + " ] }}");
        List<ProfileDetails> list = mongoTemplate.find(query, ProfileDetails.class); //setMaxResults(AppConstants.MAX_REMOVED_SIZE);

        list.forEach(curr -> {
            ProfileDetailsDto dto = new ProfileDetailsDto();
            dto.setProfileDetails(curr);
            dto.setProfileConnection(dummyConnection);
            profileDetailsDtos.add(dto);
        });

        return profileDetailsDtos;
    }

    @Override
    public String getUnwantedProfiles(String internalId) {

        String resultValue = "'" + internalId + "'";

        Optional<ProfileInternalInfo> profileInternalInfoO = profileInternalInfoRepo.findById(internalId);
        if (profileInternalInfoO.isPresent()) {
            ProfileInternalInfo profileInternalInfo = profileInternalInfoO.get();
            resultValue += profileInternalInfo.getRemovedProfiles();
            resultValue += "," + profileInternalInfo.getReadProfiles();
            resultValue += "," + profileInternalInfo.getShortlistedProfiles();
            resultValue += "," + profileInternalInfo.getBuyContactsProfiles();
        }
        return resultValue;
    }

    @Override
    public String getRemovedProfiles(String internalId) {
        Optional<ProfileInternalInfo> profileInternalInfoOptional = profileInternalInfoRepo.findById(internalId);
        if (!profileInternalInfoOptional.isPresent()) {
            return "'1'";
        }
        return profileInternalInfoOptional.get().getRemovedProfiles();
    }

    @Override
    public String getReadProfiles(String internalId) {
        Optional<ProfileInternalInfo> profileInternalInfoOptional = profileInternalInfoRepo.findById(internalId);
        if (!profileInternalInfoOptional.isPresent()) {
            return "'1'";
        }
        return profileInternalInfoOptional.get().getReadProfiles();
    }

    @Override
    public String getShortlistedProfiles(String internalId) {
        Optional<ProfileInternalInfo> profileInternalInfoOptional = profileInternalInfoRepo.findById(internalId);
        if (!profileInternalInfoOptional.isPresent()) {
            return "'1'";
        }
        return profileInternalInfoOptional.get().getShortlistedProfiles();
    }

    @Override
    public String getBuyContactsProfiles(String internalId) {
        Optional<ProfileInternalInfo> profileInternalInfoOptional = profileInternalInfoRepo.findById(internalId);
        if (!profileInternalInfoOptional.isPresent()) {
            return "'1'";
        }
        return profileInternalInfoOptional.get().getBuyContactsProfiles();
    }

    @Override
    @Transactional
    public void undoRemoveFromWall(String internalId, String idToAdd) {

        Optional<ProfileInternalInfo> profileInternalInfoOptional = profileInternalInfoRepo.findById(internalId);
        if (profileInternalInfoOptional.isPresent()) {
            ProfileInternalInfo profileInternalInfo = profileInternalInfoOptional.get();
            profileInternalInfo.setRemovedProfiles(removeFromCsvString(profileInternalInfo.getRemovedProfiles(),idToAdd));
            profileInternalInfoRepo.save(profileInternalInfo);
        }

    }

    private String removeFromCsvString(String csvString, String idToRemove) {
        if (csvString.contains(idToRemove)) {
            csvString = csvString.replaceAll("'" + idToRemove + "',", "");
        }
        return csvString;
    }

    private String addToCsvString(String csvString, String idToAdd) {
        if (!csvString.contains(idToAdd)) {
            csvString = "'" + idToAdd + "'," + csvString;
        }
        return csvString;
    }

    @Override
    @Transactional
    public void removeFromWallById(String internalId, String toRemove) {

        Optional<ProfileInternalInfo> profileInternalInfoOptional = profileInternalInfoRepo.findById(internalId);
        if (profileInternalInfoOptional.isPresent()) {
            ProfileInternalInfo profileInternalInfo = profileInternalInfoOptional.get();
            profileInternalInfo.setRemovedProfiles(addToCsvString(profileInternalInfo.getRemovedProfiles(),toRemove));
            profileInternalInfo.setShortlistedProfiles(removeFromCsvString(profileInternalInfo.getShortlistedProfiles(),toRemove));
            profileInternalInfoRepo.save(profileInternalInfo);
        }else{
            ProfileInternalInfo profileInternalInfo = new ProfileInternalInfo();
            profileInternalInfo.setInternalId(internalId);
            profileInternalInfo.setRemovedProfiles(addToCsvString(profileInternalInfo.getRemovedProfiles(),toRemove));
            profileInternalInfoRepo.save(profileInternalInfo);
        }

    }

    @Override
    @Transactional
    public void removeFromShortlistedIfAnyById(String internalId, String toRemove) {
        Optional<ProfileInternalInfo> profileInternalInfoOptional = profileInternalInfoRepo.findById(internalId);
        if (profileInternalInfoOptional.isPresent()) {
            ProfileInternalInfo profileInternalInfo = profileInternalInfoOptional.get();
            profileInternalInfo.setShortlistedProfiles(removeFromCsvString(profileInternalInfo.getShortlistedProfiles(),toRemove));
            profileInternalInfoRepo.save(profileInternalInfo);
        }
    }

    @Override
    @Transactional
    public void shortlistProfileById(String internalId, String profileTo) {
        Optional<ProfileInternalInfo> profileInternalInfoOptional = profileInternalInfoRepo.findById(internalId);
        if (profileInternalInfoOptional.isPresent()) {
            ProfileInternalInfo profileInternalInfo = profileInternalInfoOptional.get();
            profileInternalInfo.setShortlistedProfiles(addToCsvString(profileInternalInfo.getShortlistedProfiles(),profileTo));
            profileInternalInfoRepo.save(profileInternalInfo);
        }else{
            ProfileInternalInfo profileInternalInfo = new ProfileInternalInfo();
            profileInternalInfo.setInternalId(internalId);
            profileInternalInfo.setShortlistedProfiles(addToCsvString(profileInternalInfo.getShortlistedProfiles(),profileTo));
            profileInternalInfoRepo.save(profileInternalInfo);
        }
    }

    @Override
    @Transactional
    public void addToReadProfiles(String profileFrom, String profileTo) {
        Optional<ProfileInternalInfo> profileInternalInfoOptional = profileInternalInfoRepo.findById(profileFrom);
        if (profileInternalInfoOptional.isPresent()) {
            ProfileInternalInfo profileInternalInfo = profileInternalInfoOptional.get();
            profileInternalInfo.setReadProfiles(addToCsvString(profileInternalInfo.getReadProfiles(),profileTo));
            profileInternalInfoRepo.save(profileInternalInfo);
        }else{
            ProfileInternalInfo profileInternalInfo = new ProfileInternalInfo();
            profileInternalInfo.setInternalId(profileFrom);
            profileInternalInfo.setReadProfiles(addToCsvString(profileInternalInfo.getReadProfiles(),profileTo));
            profileInternalInfoRepo.save(profileInternalInfo);
        }
    }

    @Override
    public void addToBuyContactsProfiles(String profileFrom, String profileTo) {
        Optional<ProfileInternalInfo> profileInternalInfoOptional = profileInternalInfoRepo.findById(profileFrom);
        if (profileInternalInfoOptional.isPresent()) {
            ProfileInternalInfo profileInternalInfo = profileInternalInfoOptional.get();
            profileInternalInfo.setBuyContactsProfiles(addToCsvString(profileInternalInfo.getBuyContactsProfiles(),profileTo));

            profileInternalInfoRepo.save(profileInternalInfo);
        }else{
            ProfileInternalInfo profileInternalInfo = new ProfileInternalInfo();
            profileInternalInfo.setInternalId(profileFrom);
            profileInternalInfo.setBuyContactsProfiles(addToCsvString(profileInternalInfo.getBuyContactsProfiles(),profileTo));
            profileInternalInfoRepo.save(profileInternalInfo);
        }
    }


}
