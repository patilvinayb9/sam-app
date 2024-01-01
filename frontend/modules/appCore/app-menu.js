
var appInitMenu = function ($scope, $http, $modal, $log, $sce, $cookies, $window){

    $scope.es.extendedMenuMap = {
        "homeView": {
            id :"homeView",
            className:"fullClass",
            title :"Home",
            templateUrl :"modules/home/homeIndex.html",
            icon :"fa-home"
        },
        "searchView": {
            id :"searchView",
            className:"fullClass",
            title :"Search",
            templateUrl :"modules/search/searchIndex.html",
            icon :"fa-search"
        },
        "bridesView": {
            id :"bridesView",
            className:"fullClass",
            title :"Brides",
            templateUrl :"modules/search/bridesIndex.html",
            icon :"fa fa-female"
        },
        "groomsView": {
            id :"groomsView",
            className:"fullClass",
            title :"Grooms",
            templateUrl :"modules/search/groomsIndex.html",
            icon :"fa fa-male"
        },
        "settingsView": {
            id :"settingsView",
            className:"bodyClass",
            title :"Settings",
            templateUrl :"modules/settings/settingsIndex.html",
            icon :"fa fa-cog"
        },

        "supportView": {
            id :"supportView",
            className:"bodyClass",
            title :"Support",
            templateUrl :"modules/support/supportIndex.html",
            icon :"fa-commenting-o"
        },

        "registerView" : {
            id : "registerView",
            className: "fullClass",
            title : "Register",
            templateUrl : "modules/register/registerIndex.html",
            icon : "fa-user"
        },

        "donationsView": {
            id :"donationsView",
            className:"bodyClass",
            title :"Donations",
            templateUrl :"modules/donations/donationsIndex.html",
            icon :"fa-credit-card"
        },

        "walletsView": {
            id :"walletsView",
            className:"bodyClass",
            title :"Wallet",
            templateUrl :"modules/wallet/walletIndex.html",
            icon :"fa-credit-card"
        },


        "userView" : {
            id : "userView",
            className: "fullClass",
            title : "Home",
            templateUrl : "modules/wall/wallIndex.html",
            icon : "fa-home"
        },

        "testimonialsView" : {
            id : "testimonialsView",
            className: "bodyClass",
            title : "Testimonials",
            templateUrl : "modules/comingSoon/comingSoon.html",
            icon : "fa-comments-o"
        },
        "adminIndexView" : {
            id : "adminIndexView",
            className: "bodyClass",
            title : "Admin",
            templateUrl : "modules/admin/adminIndex.html",
            icon : "fa-black-tie"
        },

        "updateProfileView" : {
            id : "updateProfileView",
            className: "fullClass",
            title : "Update Profile",
            templateUrl : "modules/profile/updateProfileIndex.html",
            icon : ""
        },

        "manageAlbumView" : {
            id : "manageAlbumView",
            className: "fullClass",
            title : "Manage Album",
            templateUrl : "modules/profile/manageAlbum.html",
            icon : ""
        },

        "expectationsView" : {
            id : "expectationsView",
            className: "fullClass",
            title : "Expectations",
            templateUrl : "modules/expectations/expectationsIndex.html",
            icon : ""
        },

        "notificationsView" : {
            id : "notificationsView",
            className: "bodyClass",
            title : "Notifications",
            templateUrl : "modules/notifications/notificationsIndex.html",
            icon : ""
        }
        };

    $scope.es.popupMap = {
        "SIGN_IN_POP_UP" : {
            id : "SIGN_IN_POP_UP",
            className: "bodyClass",
            title : "Sign In",
            templateUrl : "modules/auth/signInPopup.html",
            /*size : "sm",*/
            controller : "edgeController"
        },
        "SIGN_UP_POP_UP" : {
            id : "SIGN_UP_POP_UP",
            className: "bodyClass",
            title : "Sign Up",
            templateUrl : "modules/auth/signUpPopup.html",
            /*size : "sm",*/
            controller : "edgeController"
        },
        "FORGOT_PWD_POP_UP" : {
            id : "FORGOT_PWD_POP_UP",
            className: "bodyClass",
            title : "Forgot Password",
            templateUrl : "modules/auth/forgotPasswordPopup.html",
            /*size : "sm",*/
            controller : "edgeController"
        },
        "FULL_PROFILE_POPUP" : {
            id : "FULL_PROFILE_POPUP",
            className: "bodyClass",
            title : "Enlarged Profile",
            templateUrl : "modules/profile/fullProfilePopup.html",
            size : "lg",
            controller : "edgeController"
        },
        "SECURE_PROFILE_POPUP" : {
            id : "SECURE_PROFILE_POPUP",
            className: "bodyClass",
            title : "Profile - Contact Details",
            templateUrl : "modules/profile/secureProfilePopup.html",
            /*size : "sm",*/
            controller : "edgeController"
        },
        "SLIDESHOW_POPUP" : {
            id : "SLIDESHOW_POPUP",
            className: "bodyClass",
            title : "Slideshow",
            templateUrl : "modules/utilities/slideshowPopup.html",
            /*size : "sm",*/
            controller : "edgeController"
        },
        "CONFIRM_POPUP" : {
            id : "CONFIRM_POPUP",
            className: "bodyClass",
            title : "Confirmation",
            templateUrl : "modules/utilities/confirmPopup.html",
            /*size : "sm",*/
            controller : "edgeController"
        },
        "BASIC_POPUP" : {
            id : "BASIC_POPUP",
            className: "bodyClass",
            title : "Popup",
            templateUrl : "modules/utilities/basicPopup.html",
            /*size : "sm",*/
            controller : "edgeController"
        },
        "ALERT_POPUP" : {
            id : "ALERT_POPUP",
            className: "bodyClass",
            title : "Information: ",
            templateUrl : "modules/utilities/alertPopup.html",
            /*size : "sm",*/
            controller : "edgeController"
        },
        "SET_LANGUAGE_POPUP" : {
            id : "SET_LANG_POPUP",
            className: "bodyClass",
            title : "Information: ",
            templateUrl : "modules/utilities/setLanguagePopup.html",
            size : "sm",
            controller : "edgeController"
        }
    };
}
