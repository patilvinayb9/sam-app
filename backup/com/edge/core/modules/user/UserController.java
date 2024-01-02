package com.edge.core.modules.user;

import com.edge.core.api.APIConstants;
import com.edge.core.exception.AppRuntimeException;
import com.edge.core.modules.auth.UserViewModel;
import com.edge.core.modules.common.EdgeResponse;
import com.edge.core.modules.language.CoreMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private CoreMessages coreMessages;

    @Autowired
    private UserService userService;

    @ResponseBody
    @PostMapping(value = {APIConstants.URL_CREATE_USER})
    public EdgeResponse<UserViewModel> createUser(
            @RequestBody UserViewModel userViewModel
    ) {
        try {
            userViewModel = userService.createUser(userViewModel, "ROLE_USER", null);
        } catch (AppRuntimeException ex) {
            return EdgeResponse.createExceptionResponse(ex);
        } catch (Exception ex) {
            return EdgeResponse.createExceptionResponse(new AppRuntimeException(ex, "Internal Error"));
        }
        return EdgeResponse.createDataResponse(
                userViewModel,
                "Congratulations, Your account has been successfully created! " +
                        " Your Profile Id is : '" + userViewModel.getProfileId() + "'. " +
                        " Please Sign In to continue, Thank You!.");

    }

    @ResponseBody
    @PostMapping(value = {APIConstants.URL_CREATE_ADMIN})
    public EdgeResponse<String> createAdmin(
            @RequestBody AdminCreationRequest adminCreationRequest, Principal principal
    ) throws Exception {
        String result = userService.createAdmin(adminCreationRequest.getId(), adminCreationRequest.getPassword(), adminCreationRequest.getCode(), adminCreationRequest.getCompleteNumber());
        if (result != null) {
            return EdgeResponse.createDataResponse(
                    null,
                    result);
        }
        return EdgeResponse.createDataResponse(
                null,
                "Failure");
    }

    @ResponseBody
    @PostMapping(value = {APIConstants.URL_VERIFICATION_CODE})
    public EdgeResponse<String> sendVerificationCode(
            @RequestBody String completeNumber
    ) throws Exception {

        try {
            if (StringUtils.isEmpty(completeNumber)) {
                return EdgeResponse.createErrorResponse(coreMessages.msg_invalidPhoneNumber(completeNumber), null, null, null);
            }

            String verificationCode = userService.getVerificationCode(completeNumber, true);

            if (verificationCode != null) {
                return EdgeResponse.createSuccessResponse(coreMessages.msg_verificationCodeSent(), null, null, null);
            } else {
                return EdgeResponse.createErrorResponse(coreMessages.msg_invalidPhoneNumber(completeNumber), null, null, null);
            }
        } catch (AppRuntimeException ex) {
            return EdgeResponse.createExceptionResponse(ex);
        }

    }

    @ResponseBody
    @PostMapping(value = {APIConstants.URL_RESET_PASSWORD})
    public EdgeResponse<UserViewModel> resetPassword(
            @RequestBody UserViewModel userViewModel
    ) {
        try {
            userViewModel = userService.resetPassword(userViewModel);
        } catch (AppRuntimeException ex) {
            return EdgeResponse.createExceptionResponse(ex);
        }
        return EdgeResponse.createDataResponse(
                userViewModel,
                coreMessages.msg_pwdUpdated());

    }

    @ResponseBody
    @PostMapping(value = {APIConstants.URL_LOGIN_USER})
    public EdgeResponse<UserViewModel> getLoggedInUser(
            Principal principal
    ) {
        UserViewModel userViewModel = null;
        if (principal != null) {
            userViewModel = userService.getLoggedInUser(principal.getName());
        } else {
            userViewModel = null;
        }

        return EdgeResponse.createDataResponse(userViewModel, null);
    }

    @ResponseBody
    @PostMapping(value = {APIConstants.URL_LOGIN_ROLE})
    public EdgeResponse<String> getLoggedInRole(
            Principal principal
    ) {
        String role = "";
        if (principal != null) {
            UserViewModel userViewModel = userService.getLoggedInUser(principal.getName());
            role = userViewModel.getRole();
        }
        return EdgeResponse.createDataResponse(role, null);
    }

    @ResponseBody
    @PostMapping(value = {APIConstants.URL_AUTH_FAILED})
    public EdgeResponse<String> authFailed(
            Principal principal
    ) {

        return EdgeResponse.createErrorResponse("RedirectLoginFailure", null, null, null);
    }

}
