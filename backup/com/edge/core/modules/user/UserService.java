package com.edge.core.modules.user;

import com.edge.core.modules.auth.UserViewModel;
import com.edge.core.modules.security.EdgeUserDetails;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {

    UserViewModel createUser(UserViewModel userViewModel, String role, String id) throws Exception;

    @Transactional
    String createAdmin(String id, String password, String code, String completeNumber) throws Exception;

    EdgeUserDetails getUserByCompleteNumber(String completeNumber);

    String getVerificationCode(String completeNumber, boolean toSms) throws Exception;

    UserViewModel resetPassword(UserViewModel userViewModel);

    UserViewModel getLoggedInUser(String loggedInId);

    void deleteByInternalId(String internalId);

    EdgeUserDetails getUserById(String internalId);

    void updateCompleteNumber(String oldCompleteNUmber, String newCompleteNumber, String verificationCode) throws Exception;
}
