package com.edge.app.modules.api;

public class AppAPIConstants {

    // WallController
    public static final String URL_LOAD_NEW_PROFILES = "/api/unsecured/loadNewProfiles";
    public static final String URL_LOAD_WALL_PROFILES = "/api/secured/loadWallProfiles";
    public static final String URL_ONE_TIME_SEARCH = "/api/secured/oneTimeSearch";
    public static final String URL_GUEST_SEARCH = "/api/unsecured/guestSearch";
    public static final String URL_LOAD_BRIDES = "/api/unsecured/loadBrides";
    public static final String URL_LOAD_GROOMS = "/api/unsecured/loadGrooms";


    // Profile Internal Info Controller
    public static final String URL_REMOVE_FROM_WALL = "/api/secured/removeFromWallById";
    public static final String URL_SHORTLIST_PROFILE = "/api/secured/shortlistProfile";
    public static final String URL_LOAD_SHORTLISTED_PROFILES = "/api/secured/loadShortlistedProfiles";
    public static final String URL_LOAD_REMOVED_PROFILES = "/api/secured/loadRemovedProfiles";
    public static final String URL_UNDO_REMOVE_FROM_WALL = "/api/secured/undoRemoveFromWall";
    public static final String URL_LOAD_BUY_CONTACTS_PROFILES = "/api/secured/loadBuyContactsProfiles";


    // Profile Connection Controller
    public static final String URL_SEND_CONNECTION_REQUEST = "/api/secured/sendConnectionRequest";
    public static final String URL_SEARCH_PROFILES = "/api/secured/searchProfiles";
    public static final String URL_WITHDRAW_REQUEST = "/api/secured/withdrawRequest";
    public static final String URL_ACTION_REQUEST = "/api/secured/actionRequest";
    public static final String URL_GET_SECURE_DETAILS = "/api/secured/getSecureDetails";
    public static final String URL_BUY_SECURE_DETAILS = "/api/secured/buySecureDetails";


    // ExpectationsController
    public static final String URL_LOAD_EXPECTATIONS = "/api/secured/loadExpectations";
    public static final String URL_SET_EXPECTATIONS = "/api/secured/setExpectations";

    // Notifications Controller
    public static final String URL_LOAD_NOTIFICATIONS = "/api/secured/loadNotifications";
    public static final String URL_LOAD_UNREAD_NOTIFICATIONS = "/api/unsecured/loadUnreadNotifications";
    public static final String URL_MARK_NOTIFICATIONS_READ = "/api/secured/markNotificationAsRead";

    // Profile Controller

    public static final String URL_UPLOAD_IMAGE = "/api/secured/uploadImage";
    public static final String URL_GET_IMAGE = "/api/secured/getImage/{fileType}/{entityId}/{fileName}";
    public static final String URL_GET_THUMBNAIL = "/api/secured/thumbnail/{entityId}";
    public static final String URL_GET_LOGGED_IN_PROFILE = "/api/unsecured/profile/getLoggedInProfile";
    public static final String URL_UPDATE_MY_PROFILE = "/api/secured/profile/updateMyProfile";
    public static final String URL_DELETE_MY_PROFILE = "/api/secured/profile/deleteMyProfile";
    public static final String URL_SEARCH_BY_ID = "/api/unsecured/searchById";


    // Wallet Controller
    public static final String URL_LOAD_WALLET_DETAILS = "/api/secured/loadWalletDetails";
    public static final String URL_LOAD_WALLET_TRANSACTIONS = "/api/secured/loadWalletTransactions";
    public static final String URL_RECHARGE_WALLET = "/api/secured/rechargeWallet";
    public static final String URL_WALLET_SUCCESS = "/api/unsecured/walletSuccess";
    public static final String URL_WALLET_FAILURE = "/api/unsecured/walletFailure";
    public static final String URL_WALLET_SUCCESS_WEBHOOK = "/api/unsecured/57297c487bbe5b0b15211670cd3986e0ebf09cbe7db571ff5f82360b560b1";
    public static final String URL_WALLET_FAILURE_WEBHOOK = "/api/unsecured/57297c487bbe5b0b15211670cd3986e0ebf09cbe7db571ff5f82360b560b9";


    // Admin Controller
    public static final String URL_ADMIN_WALLET_SUCCESS = "/server/admin/Success";
    public static final String URL_ADMIN_WALLET_FAILURE = "/server/admin/Failure";
    public static final String URL_ADMIN_REGISTERED = "/server/admin/registered";
    public static final String URL_ADMIN_REQUESTED = "/server/admin/requested";
    public static final String URL_ADMIN_SEND_RANDOM_SMS = "/server/admin/sendRandomSms";
    public static final String URL_ADMIN_GET_PAYMENT_STATUS = "/server/admin/getAdminPaymentStatus";
    public static final String URL_ADMIN_DELETE_ACCOUNT = "/server/admin/deleteAccount";
    public static final String URL_ADMIN_GET_PROFILE_ID = "/server/admin/getProfileId";
    public static final String URL_ADMIN_DELETE_PIC = "/server/admin/deletePic";
}

