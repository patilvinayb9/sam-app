package com.edge.app.modules.expectations;

import com.edge.app.modules.profile.ProfileReadService;
import com.edge.app.modules.profile.nonSecure.ProfileDetails;
import com.edge.core.helper.DbHelper;
import com.edge.core.modules.auth.SpringSecurityUtil;
import com.edge.repositories.ExpectationsRepository;
import com.edge.repositories.ProfileDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Optional;

@Component
public class ExpectationsServiceImpl implements ExpectationsService {

    private static final Logger logger = LoggerFactory.getLogger(ExpectationsServiceImpl.class);

    @Autowired
    private ExpectationsRepository expectationsRepository;

    @Autowired
    private ProfileReadService profileReadService;

    @Autowired
    private ProfileDetailsRepository profileDetailsRepo;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    @Qualifier("mongoDbHelper")
    private DbHelper dbHelper;

    @Override
    public String getExpectations(String internalId) {

        Optional<Expectations> expectationsO = expectationsRepository.findById(internalId);
        if (expectationsO.isPresent()) {
            return expectationsO.get().getExpectations();
        } else {
            return "";
        }
    }

    @Override
    public Expectations loadExpectationsById(String internalId) {

        Expectations expectations = null;

        Optional<Expectations> expectationsO = expectationsRepository.findById(internalId);

        if (expectationsO.isPresent()) {
            expectations = expectationsO.get();
        }

        return expectations;
    }

    @Override
    @Transactional
    public Expectations setExpectations(Expectations expectations) throws ParseException {

        expectations.setExpectations(deriveExpectations(expectations, expectations.getInternalId(), true));
        expectations.setRestrictions(deriveExpectations(expectations, expectations.getInternalId(), false));
        expectationsRepository.save(expectations);

        ProfileDetails profileDetails = profileReadService.getProfileDetailsById(SpringSecurityUtil.getLoggedInInternalId());
        if (profileDetails != null) {
            profileDetails.setExpectationsSet("Y");
            saveProfileDetails(profileDetails);
        }

        return expectations;

    }

    @Transactional
    public void saveProfileDetails(ProfileDetails profileDetails) {
        profileDetailsRepo.save(profileDetails);
    }

    @Override
    public String getOneTimeExpectationsById(Expectations expectations, String internalId) throws ParseException {
        return deriveExpectations(expectations, internalId, true);
    }

    @Override
    public String deriveExpectations(Expectations expectations, String internalId, boolean isExpectation) throws ParseException {

        String hql = " membershipValidTill : { $gte : new Date() }  "; // Membership has to be valid
        if (internalId != null && internalId.startsWith("F")) {
            hql += ", gender : 'Male' ";
        } else if (internalId != null && internalId.startsWith("M")) {
            hql += ", gender : 'Female' ";
        } else if (internalId != null) {
            hql += ", gender : 'LGBT' ";
        }

        hql += dbHelper.addINClause("bloodGroup", expectations.getBloodGroups());
        hql += dbHelper.addINClause("bodyType", expectations.getBodyTypes());

        hql += dbHelper.addINClause("skinColor", expectations.getSkinColors());
        hql += dbHelper.addINClause("maritalStatus", expectations.getMaritalStatuss());
        hql += dbHelper.addINClause("physicalStatus", expectations.getPhysicalStatuss());
        hql += dbHelper.addINClause("manglikStatus", expectations.getManglikStatuss());
        hql += dbHelper.addINClause("kundaliNadi", expectations.getKundaliNadis());
        hql += dbHelper.addINClause("kundaliCharan", expectations.getKundaliCharans());
        hql += dbHelper.addINClause("kundaliGan", expectations.getKundaliGans());
        hql += dbHelper.addINClause("professionalType", expectations.getProfessionalTypes());

        hql += dbHelper.addINClause("diet", expectations.getDiet());
        hql += dbHelper.addINClause("smoking", expectations.getSmoking());
        hql += dbHelper.addINClause("drinking", expectations.getDrinking());

        hql += dbHelper.addINClause("religion", expectations.getReligions());
        hql += dbHelper.addINClause("cast", expectations.getCasts());
        hql += dbHelper.addINClause("motherTongue", expectations.getMotherTongues());

        hql += dbHelper.addINClause("currentState", expectations.getCurrentState());
        hql += dbHelper.addINClause("currentCountry", expectations.getCurrentCountry());
        hql += dbHelper.addINClause("familyState", expectations.getFamilyState());
        hql += dbHelper.addINClause("familyCountry", expectations.getFamilyCountry());

        hql += dbHelper.addINClause("degreeType", expectations.getDegreeTypes(), "\\|");

        hql += dbHelper.addBETWEENClause("earning", expectations.getEarningFromInLakh(), expectations.getEarningToInLakh());
        hql += dbHelper.addBETWEENClause("weight", expectations.getWeightFrom(), expectations.getWeightTo());
        hql += dbHelper.addBETWEENClause("heightComplete", expectations.getHeightCompleteFrom(), expectations.getHeightCompleteTo());

        hql += dbHelper.addBETWEENClause("birthDate", expectations.getFromBirthDate(), expectations.getToBirthDate());

        if (isExpectation && expectations.isProfilesWithPicOnly()) {
            hql += ", profilePic : { $ne: 'NA' } ";
        }
        //hql += dbHelper.addINClause("", expectations.get);

        return hql;
    }

}

// WORKING: db.PROFILE_DETAILS.find({ $expr: { $gte: [ { $toDouble:"$earning"}, 500000 ] }   });

//   gender : 'Female' , maritalStatus : 'Never_Married'  ,  $expr: { $gte: [ { $toDouble:"$earning"}, 500000 ] }
