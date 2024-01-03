
BASE_URI = "http://localhost:8080";

// UserController

URL_CREATE_USER = BASE_URI + "/api/unsecured/core/createUser";
URL_CREATE_ADMIN = BASE_URI + "/api/secured/core/createAdmin";

URL_RESET_PASSWORD = BASE_URI + "/api/unsecured/core/resetPassword";
URL_LOGIN_USER = BASE_URI + "/api/unsecured/core/getLoggedInUser";
URL_LOGIN_ROLE = BASE_URI + "/api/secured/core/getLoggedInRole";
URL_AUTH_FAILED = BASE_URI + "/api/unsecured/core/authFailed";

URL_VERIFICATION_CODE = BASE_URI + "/api/unsecured/core/sendVerificationCode";


