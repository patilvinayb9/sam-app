package com.edge.app.modules.wall;

import com.edge.app.modules.common.AppConstants;
import com.edge.app.modules.expectations.Expectations;
import com.edge.app.modules.expectations.ExpectationsService;
import com.edge.app.modules.profile.dto.ProfileDetailsDto;
import com.edge.app.modules.profile.nonSecure.ProfileDetails;
import com.edge.app.modules.profileInternalInfo.ProfileInternalInfoService;
import com.edge.repositories.ProfileDetailsRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Component
public class WallServiceImpl implements WallService {

    private static final Logger logger = LoggerFactory.getLogger(WallServiceImpl.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ProfileInternalInfoService profileInternalInfoService;

    @Autowired
    private ExpectationsService expectationsService;

    @Autowired
    private ProfileDetailsRepository profileDetailsRepo;

    @Override
    public List<ProfileDetailsDto> oneTimeSearchById(Expectations expectations, String internalId) throws ParseException {
        String oneTimeExpectations = expectationsService.getOneTimeExpectationsById(expectations, internalId);
        return loadWallProfilesById(internalId, oneTimeExpectations);
    }

    @Override
    public List<ProfileDetailsDto> guestSearch(Expectations expectations) throws ParseException {
        String guestExpectations = expectationsService.deriveExpectations(expectations, expectations.getGender(), true);
        return loadGuestProfiles(guestExpectations);
    }

    @Override
    public List<ProfileDetailsDto> loadGuestProfiles(String expectationsClause) {

        List<ProfileDetailsDto> profileDetailsDtos = new ArrayList();

        String queryClause = "";


        if (StringUtils.isNotBlank(expectationsClause)) {
            queryClause += expectationsClause;
        }

        queryClause += ", membershipValidTill : { $gte : new Date() }  "; // Membership has to be valid

        Query query = new BasicQuery(" { "
                + queryClause
                + " } ");
        query.with(Sort.by(Sort.Direction.DESC, "lastLoggedIn"));
        query.limit(AppConstants.MAX_WALL_SIZE);
        List<ProfileDetails> list = mongoTemplate.find(query, ProfileDetails.class); //setMaxResults(AppConstants.MAX_REMOVED_SIZE);

        list.forEach(curr -> {
            ProfileDetailsDto dto = new ProfileDetailsDto();
            dto.setProfileDetails(curr);
            profileDetailsDtos.add(dto);
        });

        return profileDetailsDtos;

    }

    @Override
    public List<ProfileDetailsDto> loadWallProfilesById(String internalId, String expectationsClause) {

        List<ProfileDetailsDto> profileDetailsDtos = new ArrayList();

        String unwantedProfiles = profileInternalInfoService.getUnwantedProfiles(internalId);
        String queryClause = " internalId:{ $nin: [ " + unwantedProfiles + " ] }";

        if (expectationsClause == null) {
            expectationsClause = expectationsService.getExpectations(internalId);
        }

        if (StringUtils.isNotBlank(expectationsClause)) {
            queryClause += "," + expectationsClause;
        } else {
            if (internalId.startsWith("F")) {
                queryClause += " gender : 'Male' ";
            } else if (internalId.startsWith("M")) {
                queryClause += " gender : 'Female' ";
            } else {
                queryClause += " gender : 'LGBT' ";
            }
        }

        queryClause += ", membershipValidTill : { $gte : new Date() }  "; // Membership has to be valid

        Query query = new BasicQuery(" { "
                + queryClause
                + " } ");
        query.with(Sort.by(Sort.Direction.DESC, "lastLoggedIn"));
        query.limit(AppConstants.MAX_WALL_SIZE);
        List<ProfileDetails> list = mongoTemplate.find(query, ProfileDetails.class); //setMaxResults(AppConstants.MAX_REMOVED_SIZE);

        list.forEach(curr -> {
            ProfileDetailsDto dto = new ProfileDetailsDto();
            dto.setProfileDetails(curr);
            profileDetailsDtos.add(dto);
        });

        return profileDetailsDtos;

    }

    @Override
    public List<ProfileDetailsDto> loadNewProfiles() {
        List<ProfileDetailsDto> profileDetailsDtos = new ArrayList();

        List<ProfileDetails> females = getNewProfiles("Female", 20);
        List<ProfileDetails> males = getNewProfiles("Male", 20);

        //Collections.sort(profileDetails, Comparator.comparing(ProfileDetails::getGender).thenComparing(ProfileDetails::getProfilePic));

        females.forEach(curr -> {
            if (curr != null && curr.getProfilePic() != null && !curr.getProfilePic().equals("NA")) {
                ProfileDetailsDto dto = new ProfileDetailsDto();
                dto.setProfileDetails(curr);
                profileDetailsDtos.add(dto);
            }

        });

        males.forEach(curr -> {
            if (curr != null && curr.getProfilePic() != null && !curr.getProfilePic().equals("NA")) {
                ProfileDetailsDto dto = new ProfileDetailsDto();
                dto.setProfileDetails(curr);
                profileDetailsDtos.add(dto);
            }

        });


        females.forEach(curr -> {
            if (curr != null && curr.getProfilePic() != null && curr.getProfilePic().equals("NA")) {
                ProfileDetailsDto dto = new ProfileDetailsDto();
                dto.setProfileDetails(curr);
                profileDetailsDtos.add(dto);
            }

        });

        males.forEach(curr -> {
            if (curr != null && curr.getProfilePic() != null && curr.getProfilePic().equals("NA")) {
                ProfileDetailsDto dto = new ProfileDetailsDto();
                dto.setProfileDetails(curr);
                profileDetailsDtos.add(dto);
            }

        });

        return profileDetailsDtos;
    }

    private List<ProfileDetails> getNewProfiles(String gender, int count) {
        String queryClause = " ";

        queryClause += " gender : '" + gender + "' ";

        queryClause += ", membershipValidTill : { $gte : new Date() }  "; // Membership has to be valid

        Query query = new BasicQuery(" { "
                + queryClause
                + " } ");

        query.with(Sort.by(Sort.Direction.DESC, "lastLoggedIn"));
        query.limit(count);

        List<ProfileDetails> profileDetails = mongoTemplate.find(query, ProfileDetails.class);

        if (profileDetails == null) {
            profileDetails = new ArrayList<>();
        }

        return profileDetails;
    }

    @Override
    public List<ProfileDetailsDto> loadGrooms(String searchType) throws ParseException {

        List<ProfileDetailsDto> profileDetailsDtos = new ArrayList();

        Expectations expectations = new Expectations();
        //expectations.setEarningFrom(1);

        String queryClause = expectationsService.deriveExpectations(expectations, "FInternalId", true);

        queryClause += ", membershipValidTill : { $gte : new Date() }  "; // Membership has to be valid
        queryClause += ", degreeType : { $ne: 'Other' } ";
        //queryClause += ", profilePic : { $ne: 'NA' } ";


        Query query = new BasicQuery(" { "
                + queryClause
                + " } ");
        query.with(Sort.by(Sort.Direction.DESC, "lastLoggedIn"));


        query.limit(AppConstants.MAX_WALL_SIZE);
        List<ProfileDetails> list = mongoTemplate.find(query, ProfileDetails.class);

        list.forEach(curr -> {
            ProfileDetailsDto dto = new ProfileDetailsDto();
            dto.setProfileDetails(curr);
            profileDetailsDtos.add(dto);
        });

        return profileDetailsDtos;

    }

    @Override
    public List<ProfileDetailsDto> loadBrides(String searchType) throws ParseException {

        List<ProfileDetailsDto> profileDetailsDtos = new ArrayList();

        Expectations expectations = new Expectations();
        //expectations.setEarningFrom(1);

        String queryClause = expectationsService.deriveExpectations(expectations, "MInternalId", true);

        queryClause += ", membershipValidTill : { $gte : new Date() }  "; // Membership has to be valid
        queryClause += ", degreeType : { $ne: 'Other' } ";
        //queryClause += ", profilePic : { $ne: 'NA' } ";

        Query query = new BasicQuery(" { "
                + queryClause
                + " } ");

        query.with(Sort.by(Sort.Direction.DESC, "lastLoggedIn"));

        query.limit(AppConstants.MAX_WALL_SIZE);
        List<ProfileDetails> list = mongoTemplate.find(query, ProfileDetails.class);

        list.forEach(curr -> {
            ProfileDetailsDto dto = new ProfileDetailsDto();
            dto.setProfileDetails(curr);
            profileDetailsDtos.add(dto);
        });

        return profileDetailsDtos;

    }

}
