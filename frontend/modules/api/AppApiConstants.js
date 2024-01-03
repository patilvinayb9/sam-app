
 //WallController
URL_LOAD_NEW_PROFILES = BASE_URI + "/api/unsecured/loadNewProfiles";
URL_LOAD_WALL_PROFILES = BASE_URI + "/api/secured/loadWallProfiles";
URL_ONE_TIME_SEARCH = BASE_URI + "/api/secured/oneTimeSearch";
URL_GUEST_SEARCH = BASE_URI + "/api/unsecured/guestSearch";
URL_LOAD_BRIDES = BASE_URI + "/api/unsecured/loadBrides";
URL_LOAD_GROOMS = BASE_URI + "/api/unsecured/loadGrooms";


 //Profile Internal Info Controller
URL_REMOVE_FROM_WALL = BASE_URI + "/api/secured/removeFromWallById";
URL_SHORTLIST_PROFILE = BASE_URI + "/api/secured/shortlistProfile";
URL_LOAD_SHORTLISTED_PROFILES = BASE_URI + "/api/secured/loadShortlistedProfiles";
URL_LOAD_BUY_CONTACTS_PROFILES = BASE_URI + "/api/secured/loadBuyContactsProfiles";
URL_LOAD_REMOVED_PROFILES = BASE_URI + "/api/secured/loadRemovedProfiles";
URL_UNDO_REMOVE_FROM_WALL = BASE_URI + "/api/secured/undoRemoveFromWall";


 //Profile Connection Controller
URL_SEND_CONNECTION_REQUEST = BASE_URI + "/api/secured/sendConnectionRequest";
URL_SEARCH_PROFILES = BASE_URI + "/api/secured/searchProfiles";
URL_WITHDRAW_REQUEST = BASE_URI + "/api/secured/withdrawRequest";
URL_ACTION_REQUEST = BASE_URI + "/api/secured/actionRequest";
URL_GET_SECURE_DETAILS = BASE_URI + "/api/secured/getSecureDetails";
URL_BUY_SECURE_DETAILS = BASE_URI + "/api/secured/buySecureDetails";


 //ExpectationsController
URL_LOAD_EXPECTATIONS = BASE_URI + "/api/secured/loadExpectations";
URL_SET_EXPECTATIONS = BASE_URI + "/api/secured/setExpectations";

 //Notifications Controller
URL_LOAD_NOTIFICATIONS = BASE_URI + "/api/secured/loadNotifications";
URL_LOAD_UNREAD_NOTIFICATIONS = BASE_URI + "/api/unsecured/loadUnreadNotifications";
URL_MARK_NOTIFICATIONS_READ = BASE_URI + "/api/secured/markNotificationAsRead";

 //Profile Controller

URL_UPLOAD_IMAGE = BASE_URI + "/api/secured/uploadImage";
URL_GET_IMAGE = BASE_URI + "/api/secured/getImage/{fileType}/{entityId}/{fileName}";
URL_GET_THUMBNAIL = BASE_URI + "/api/secured/thumbnail/{entityId}";
URL_GET_LOGGED_IN_PROFILE = BASE_URI + "/api/unsecured/profile/getLoggedInProfile";
URL_UPDATE_MY_PROFILE = BASE_URI + "/api/secured/profile/updateMyProfile";
URL_DELETE_MY_PROFILE = BASE_URI + "/api/secured/profile/deleteMyProfile";
URL_SEARCH_BY_ID = BASE_URI + "/api/unsecured/searchById";


 //Wallet Controller
URL_LOAD_WALLET_DETAILS = BASE_URI + "/api/secured/loadWalletDetails";
URL_LOAD_WALLET_TRANSACTIONS = BASE_URI + "/api/secured/loadWalletTransactions";
URL_RECHARGE_WALLET = BASE_URI + "/api/secured/rechargeWallet";
URL_WALLET_SUCCESS = BASE_URI + "/api/unsecured/walletSuccess";
URL_WALLET_FAILURE = BASE_URI + "/api/unsecured/walletFailure";
URL_WALLET_SUCCESS_WEBHOOK = BASE_URI + "/api/unsecured/57297c487bbe5b0b15211670cd3986e0ebf09cbe7db571ff5f82360b560b1";
URL_WALLET_FAILURE_WEBHOOK = BASE_URI + "/api/unsecured/57297c487bbe5b0b15211670cd3986e0ebf09cbe7db571ff5f82360b560b9";


 //Admin Controller
URL_ADMIN_WALLET_SUCCESS = BASE_URI + "/api/admin/Success";
URL_ADMIN_WALLET_FAILURE = BASE_URI + "/api/admin/Failure";
URL_ADMIN_REGISTERED = BASE_URI + "/api/admin/registered";
URL_ADMIN_REQUESTED = BASE_URI + "/api/admin/requested";
URL_ADMIN_SEND_RANDOM_SMS = BASE_URI + "/api/admin/sendRandomSms";
URL_ADMIN_GET_PAYMENT_STATUS = BASE_URI + "/api/admin/getAdminPaymentStatus";
URL_ADMIN_DELETE_ACCOUNT = BASE_URI + "/api/admin/deleteAccount";
URL_ADMIN_GET_PROFILE_ID = BASE_URI + "/api/admin/getProfileId";
URL_ADMIN_DELETE_PIC = BASE_URI + "/api/admin/deletePic";