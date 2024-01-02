package com.edge.core.api;

public interface APIConstants {

    // UserController

    String URL_CREATE_USER = "/api/unsecured/core/createUser";
    String URL_CREATE_ADMIN = "/api/secured/core/createAdmin";

    String URL_RESET_PASSWORD = "/api/unsecured/core/resetPassword";
    String URL_LOGIN_USER = "/api/unsecured/core/getLoggedInUser";
    String URL_LOGIN_ROLE = "/api/secured/core/getLoggedInRole";
    String URL_AUTH_FAILED = "/api/unsecured/core/authFailed";

    String URL_VERIFICATION_CODE = "/api/unsecured/core/sendVerificationCode";

}


