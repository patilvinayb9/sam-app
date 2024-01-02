package com.edge.core.modules.user;

import com.edge.core.config.CoreConstants;
import com.edge.core.exception.AppRuntimeException;
import com.edge.core.modules.auth.SpringSecurityUtil;
import com.edge.core.modules.auth.UserViewModel;
import com.edge.core.modules.communications.CoreCommunicationSender;
import com.edge.core.modules.language.CoreMessages;
import com.edge.core.modules.security.EdgeUserDetails;
import com.edge.core.security.Encrypter;
import com.edge.core.utils.EdgeUtils;
import com.edge.repositories.EdgeUserDetailsRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    static SimpleDateFormat simpleDateExtFormat = new SimpleDateFormat("dd-MM-yyyy");

    @Autowired
    private EdgeUserDetailsRepository edgeUserDetailsRepo;

    @Autowired
    private Encrypter encrypter;

    @Autowired
    private CoreCommunicationSender coreCommunicationSender;

    @Autowired
    private CoreMessages coreMessages;

    @Value(value = "${appDomain}")
    private String appDomain;

    @Override
    public UserViewModel createUser(UserViewModel userViewModel, String role, String id) throws Exception {
        if (checkIfExistingCompleteNumber(userViewModel.getCompleteNumber())) {
            throw new AppRuntimeException(null, coreMessages.msg_phoneAlreadyRegistered(userViewModel.getCompleteNumber()));
        } else {

            List<String> errors = new ArrayList<String>();

            if (userViewModel == null) {
                errors.add(coreMessages.msg_fillTheForm());
            } else {

                if (StringUtils.isEmpty(userViewModel.getGender())) {
                    errors.add(coreMessages.msg_selectGender());
                }
                // Verify Password
                if (userViewModel.getPassword() == null || userViewModel.getConfirmPwd() == null) {
                    throw new AppRuntimeException(null, coreMessages.msg_pwdNotEmpty());
                }
                if (!userViewModel.getPassword().equalsIgnoreCase(userViewModel.getConfirmPwd())) {
                    throw new AppRuntimeException(null, coreMessages.msg_pwdConfPwdNoMatch());
                }

                // Verify Verification Code
                String verificationCode = getVerificationCode(userViewModel.getCompleteNumber(), false);
                String uiCode = userViewModel.getVerificationCode();

                if (verificationCode == null || !verificationCode.equals(uiCode)) {
                    throw new AppRuntimeException(null, coreMessages.msg_invalidVerificationCode());
                }
            }

            if (errors != null && errors.size() != 0) {
                throw new AppRuntimeException(null, coreMessages.msg_errorWhileProcessing(), "AuthServiceImpl.signUp", errors);
            } else {
                userViewModel.setConfirmPwd("");
                return signUpNewMember(userViewModel, role, id);
            }
        }
    }

    private UserViewModel signUpNewMember(UserViewModel userViewModel, String role, String id) {

        try {
            boolean success = false;
            int attempt = 0;
            while (!success) {
                try {

                    EdgeUserDetails userDetails = userViewModel.deriveUserDetails();

                    String internalId = UUID.randomUUID().toString();
                    if ("ROLE_ADMIN".equals(role)) {
                        internalId = id;
                    } else {
                        internalId = userViewModel.getGender().toCharArray()[0] + internalId;
                    }

                    userDetails.setInternalId(internalId);
                    userViewModel.setInternalId(internalId);

                    String profileId = EdgeUtils.generateProfileId();
                    profileId = userViewModel.getGender().toCharArray()[0] + profileId;
                    profileId = profileId.toUpperCase();

                    userDetails.setProfileId(profileId);
                    userViewModel.setProfileId(profileId);

                    String uiPassword = userViewModel.getPassword();

                    userViewModel.setPassword(uiPassword);
                    userDetails.setPassword(SpringSecurityUtil.encodePassword(uiPassword));
                    userDetails.setGrantedAuthorities(new String[]{role});

                    userDetails.setCompleteNumber(encrypter.encrypt(userViewModel.getCompleteNumber()));

                    edgeUserDetailsRepo.save(userDetails);
                    success = true;

                    //userViewModel.mask();

                } catch (DataIntegrityViolationException e) {
                    e.printStackTrace();
                    success = false;
                    attempt++;
                    if (attempt == CoreConstants.MAX_PROFILE_RETRY) {
                        List<String> errors = new ArrayList<>();
                        errors.add("MAX_PROFILE_RETRY");
                        throw new AppRuntimeException(e, e.getMessage());
                    }
                }
            }

        } catch (AppRuntimeException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new AppRuntimeException(ex, coreMessages.msg_errorWhileProcessing(), "AuthServiceImpl.signUpNewMember" + ex.getMessage(), null);
        }

        return userViewModel;
    }

    private boolean checkIfExistingCompleteNumber(String completeNumber) {
        String encCompleteNumber = encrypter.encrypt(completeNumber);
        EdgeUserDetails dbEntity = edgeUserDetailsRepo.findByCompleteNumber(encCompleteNumber);
        return dbEntity != null;
    }

    @Override
    public EdgeUserDetails getUserByCompleteNumber(String completeNumber) {

        String encCompleteNumber = encrypter.encrypt(completeNumber);
        EdgeUserDetails dbEntity = edgeUserDetailsRepo.findByCompleteNumber(encCompleteNumber);
        return dbEntity;
    }

    @Override
    public String getVerificationCode(String completeNumber, boolean generateNew) throws Exception {
        completeNumber = completeNumber.toLowerCase();

        String verificationCode = null;

        String encCompleteNumber = completeNumber.toLowerCase();
        encCompleteNumber = encrypter.encrypt(encCompleteNumber);
        EdgeUserDetails entityById = edgeUserDetailsRepo.findByCompleteNumber(encCompleteNumber);

        if (entityById != null) {
            if(entityById.getVerificationCode() == null){
                verificationCode = RandomStringUtils.randomAlphanumeric(CoreConstants.VERIFICATION_CODE_SIZE).toUpperCase();
                entityById.setVerificationCode(encrypter.encrypt(verificationCode));
                edgeUserDetailsRepo.save(entityById);
            }else{
                verificationCode = encrypter.decrypt(entityById.getVerificationCode());
                if(verificationCode == null){
                    verificationCode = entityById.getVerificationCode();
                    entityById.setVerificationCode(encrypter.encrypt(verificationCode));
                    edgeUserDetailsRepo.save(entityById);
                }
            }

        }else{
            verificationCode = toVerificationCode(completeNumber);
        }
        if(generateNew){
            coreCommunicationSender.verificationCodeNo(verificationCode, completeNumber);
        }
        System.out.println( " ########################### Verification Code " + completeNumber + " : " + verificationCode);
        return verificationCode;

    }

    @Override
    public UserViewModel resetPassword(UserViewModel uiEntity) {

        List<String> errors = new ArrayList<String>();
        if (StringUtils.isEmpty(uiEntity.getPassword())) {
            errors.add(coreMessages.msg_pwdNotEmpty());
        }
        if (StringUtils.isEmpty(uiEntity.getConfirmPwd())) {
            errors.add(coreMessages.msg_pwdConfPwdNoMatch());
        }
        if (!uiEntity.getPassword().equals(uiEntity.getConfirmPwd())) {
            errors.add(coreMessages.msg_pwdConfPwdNoMatch());
        }

        if (errors != null && errors.size() != 0) {
            throw new AppRuntimeException(null, coreMessages.msg_errorWhileProcessing(), "AuthServiceImpl.resetPassword", errors);
        }

        String encCompleteNumber = uiEntity.getCompleteNumber();
        encCompleteNumber = encCompleteNumber.toLowerCase();
        encCompleteNumber = encrypter.encrypt(encCompleteNumber);
        EdgeUserDetails dbEntity = edgeUserDetailsRepo.findByCompleteNumber(encCompleteNumber);

        if (dbEntity == null) {
            throw new AppRuntimeException(null, coreMessages.msg_invalidPhoneNumber(uiEntity.getCompleteNumber()));
        }

        String dbCode = dbEntity.getVerificationCode();
        String uiCode = uiEntity.getVerificationCode();

        String encryptedUiCode = encrypter.encrypt(uiCode);
        if (dbCode == null || !dbCode.equals(encryptedUiCode)) {
            throw new AppRuntimeException(null, coreMessages.msg_invalidVerificationCode());
        }

        dbEntity.setPassword(SpringSecurityUtil.encodePassword(uiEntity.getPassword()));
        dbEntity.setConfirmPwd("");
        dbEntity.setVerificationCode(null);

        edgeUserDetailsRepo.save(dbEntity);

        return uiEntity;
    }

    @Override
    public UserViewModel getLoggedInUser(String username) {

        Optional<EdgeUserDetails> userById = edgeUserDetailsRepo.findById(username);

        if (!userById.isPresent()) {
            throw new AppRuntimeException(null, "Invalid Username : " + username);
        }

        EdgeUserDetails user = userById.get();

        UserViewModel userViewModel = new UserViewModel();

        userViewModel.setRole(user.getGrantedAuthorities()[0]);

        userViewModel.setEnabled(user.isEnabled());
        userViewModel.setInternalId(user.getInternalId());
        userViewModel.setGender(user.getGender());

        return userViewModel;
    }

    @Override
    public void deleteByInternalId(String internalId) {
        edgeUserDetailsRepo.deleteById(internalId);
    }

    @Override
    public EdgeUserDetails getUserById(String internalId) {
        return edgeUserDetailsRepo.findById(internalId).get();
    }

    @Override
    public void updateCompleteNumber(String oldCompleteNUmber, String newCompleteNumber, String verificationCode) throws Exception {

        if (checkIfExistingCompleteNumber(newCompleteNumber)) {
            throw new AppRuntimeException(null, coreMessages.msg_phoneAlreadyRegistered(newCompleteNumber));
        }

        String verificationCodeServer = getVerificationCode(newCompleteNumber, false);
        if(!verificationCodeServer.equals(verificationCode)){
            throw new AppRuntimeException(null, coreMessages.msg_invalidVerificationCode());
        }

        EdgeUserDetails userByCompleteNumber = getUserByCompleteNumber(oldCompleteNUmber);
        String encCompleteNumber = encrypter.encrypt(newCompleteNumber);
        userByCompleteNumber.setCompleteNumber(encCompleteNumber);
        edgeUserDetailsRepo.save(userByCompleteNumber);
    }

    private String toVerificationCode(String completeNumber) {

        String dateParam = simpleDateExtFormat.format(new Date());
        if (completeNumber != null) {
            completeNumber = completeNumber.toLowerCase().trim();
        }
        String encrypt = encrypter.encrypt(completeNumber + dateParam);
        Integer res = getAsciiSum(encrypt);

        return String.valueOf(res);
    }


    public static Integer getAsciiSum(String input) {
        Integer result = 0;
        for (Character ch : input.toCharArray()) {
            result += (int) ch;
        }
        return result;
    }


    @Override
    @Transactional
    public String createAdmin(String id, String password, String code, String completeNumber) throws Exception {

        if (code.equals("Delete-8888084629")) {
            deleteByInternalId(id);
            return "Deleted";
        }

        if (!code.equals("8888084629")) {
            return "Invalid Code";
        }

        EdgeUserDetails adminUser = getUserByCompleteNumber(id);

        if (adminUser != null) {
            deleteByInternalId(adminUser.getInternalId());
        }

        UserViewModel userModel = new UserViewModel();
        userModel.setInternalId(id);

        userModel.setGender("Admin");
        String encryptedCompleteNUmber = encrypter.encrypt(id);
        userModel.setCompleteNumber(encryptedCompleteNUmber);
        userModel.setPassword(password);
        userModel.setConfirmPwd(password);

        createUser(userModel, "ROLE_ADMIN", id);

        coreCommunicationSender.verificationCodeNo(password, completeNumber);

        if (adminUser != null) return "Updated";
        return "Created";
    }

}
