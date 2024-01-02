
var simulateWebHook = function ($scope, $http){	
	
	if(!$scope.es.tempBusinessReason){
		alert("Justification is must! Please enter.");
		return;
	}
	
	startAjax('SIMULATE_WH', $scope);
	
	if($scope.es.webhook=="Success"){
		$scope.es.simulate={"split_info":"17076370","customerName":"F0000003",
				"additionalCharges":"","paymentMode":"CC",
				"hash":"f11af47a8b8cf8846b88f61e8aee9a a678cf30017d7607db1dd07d166b91a5dc5275e441eb2588028b68a82fe3d89a19d56c11061ab846d668321c 80ccab3010","status":"Admin Override - Success",
				"error_Message":"No Error",
				"paymentId":"17076370","productInfo":"productInfo","customerEmail":"cxxxx.xx@gmai l.com",
				"customerPhone":"999999999",
				"merchantTransactionId":"85e9756cb51e798c184d",
				"amount":"19.0","udf2":"","notificationId":"443","udf1":"","udf5":"","udf4":"" ,"udf3":""};
		
	}else{
		$scope.es.simulate={"split_info":"17076370","customerName":"F0000003",
				"additionalCharges":"","paymentMode":"",
				"hash":"f11af47a8b8cf8846b88f61e8aee9a a678cf30017d7607db1dd07d166b91a5dc5275e441eb2588028b68a82fe3d89a19d56c11061ab846d668321c 80ccab3010","status":"Admin Override - Failed",
				"error_Message":"Bank denied transaction on the card.",
				"paymentId":"17076370","productInfo":"productInfo","customerEmail":"cxxxx.xx@gmai l.com",
				"customerPhone":"999999999",
				"merchantTransactionId":"85e9756cb51e798c184d",
				"amount":"19.0","udf2":"","notificationId":"443","udf1":"","udf5":"","udf4":"" ,"udf3":""};
		
	}
	
	$scope.es.simulate.customerName = $scope.es.tempCustName.trim();
	$scope.es.simulate.merchantTransactionId = $scope.es.tempTxnId.trim();
	$scope.es.simulate.amount = $scope.es.tempTxnAmt.trim();
	$scope.es.simulate.amount = $scope.es.simulate.amount - 100; // VINAYPA

	$scope.es.simulate.error_Message = $scope.es.tempBusinessReason;
	
	// $scope.es.urlToHit='server/admin/' + $scope.es.webhook + ''; #DEBUG  #TODO
	
	$scope.es.urlToHit='server/admin/' + $scope.es.webhook + '';
	alert($scope.es.urlToHit);
	
	$http.post($scope.es.urlToHit, $scope.es.simulate ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('SIMULATE_WH', $scope, data, status, headers, config);
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('SIMULATE_WH', $scope, data, status, headers, config);
    });
};


var simulateUIHook = function ($scope, $http){	
	
	$scope.es.urlToHit='server/admin/' + $scope.es.uihook + '';
	alert($scope.es.urlToHit);
	window.open($scope.es.urlToHit);
};

var simulateTest = function ($scope, $http){	
	
	$scope.es.urlToHit='server/admin/simulateTest';
    startAjax('SIMULATE_TEST', $scope);

	$http.post($scope.es.urlToHit, $scope.es.tempStr).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('SIMULATE_TEST', $scope, data, status, headers, config);
    	alert(data.responseData);
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('SIMULATE_TEST', $scope, data, status, headers, config);
    	alert(data.responseData);
    });
	
};


var createAdmin = function ($scope, $http){

    startAjax('CREATE_ADMIN', $scope);
    $http.post(URL_CREATE_ADMIN, $scope.es.createAdminRequest ).
      success(function(data, status, headers, config) {
        handleAjaxSuccess('CREATE_ADMIN', $scope, data, status, headers, config);
      }).
      error(function(data, status, headers, config) {
        handleAjaxError('CREATE_ADMIN', $scope, data, status, headers, config);
      });

};



var deleteAccount = function ($scope, $http){

    startAjax('DELETE_ACCOUNT', $scope);
    $http.post(URL_ADMIN_DELETE_ACCOUNT, $scope.es.adminDelete ).
      success(function(data, status, headers, config) {
        handleAjaxSuccess('DELETE_ACCOUNT', $scope, data, status, headers, config);
      }).
      error(function(data, status, headers, config) {
        handleAjaxError('DELETE_ACCOUNT', $scope, data, status, headers, config);
      });

};


var getProfileId = function ($scope, $http){

    startAjax('GET_PROFILE_ID', $scope);
    $http.post(URL_ADMIN_GET_PROFILE_ID, $scope.es.adminRequest ).
      success(function(data, status, headers, config) {
        handleAjaxSuccess('GET_PROFILE_ID', $scope, data, status, headers, config);
      }).
      error(function(data, status, headers, config) {
        handleAjaxError('GET_PROFILE_ID', $scope, data, status, headers, config);
      });

};

var deletePic = function ($scope, $http){

    startAjax('DELETE_PIC', $scope);
    $http.post(URL_ADMIN_DELETE_PIC, $scope.es.adminDeletePic ).
      success(function(data, status, headers, config) {
        handleAjaxSuccess('DELETE_PIC', $scope, data, status, headers, config);
      }).
      error(function(data, status, headers, config) {
        handleAjaxError('DELETE_PIC', $scope, data, status, headers, config);
      });

};

var sendRandomSms = function ($scope, $http){

    startAjax('SEND_RANDOM_SMS', $scope);
    $http.post(URL_ADMIN_SEND_RANDOM_SMS, $scope.es.sendSmsRequest ).
      success(function(data, status, headers, config) {
        handleAjaxSuccess('SEND_RANDOM_SMS', $scope, data, status, headers, config);
      }).
      error(function(data, status, headers, config) {
        handleAjaxError('SEND_RANDOM_SMS', $scope, data, status, headers, config);
      });

};

var getAdminPaymentStatus = function ($scope, $http){

    startAjax('GET_ADMIN_PAYMENT_STATUS', $scope);
    $http.post(URL_ADMIN_GET_PAYMENT_STATUS, $scope.es.apaymentStatus ).
      success(function(data, status, headers, config) {
        handleAjaxSuccess('GET_ADMIN_PAYMENT_STATUS', $scope, data, status, headers, config);
         $scope.es.adminPaymentTransactions = data.responseData;
      }).
      error(function(data, status, headers, config) {
        handleAjaxError('GET_ADMIN_PAYMENT_STATUS', $scope, data, status, headers, config);
      });

};



;
 //WallController
URL_LOAD_NEW_PROFILES = "api/unsecured/loadNewProfiles";
URL_LOAD_WALL_PROFILES = "api/secured/loadWallProfiles";
URL_ONE_TIME_SEARCH = "api/secured/oneTimeSearch";
URL_GUEST_SEARCH = "api/unsecured/guestSearch";
URL_LOAD_BRIDES = "api/unsecured/loadBrides";
URL_LOAD_GROOMS = "api/unsecured/loadGrooms";


 //Profile Internal Info Controller
URL_REMOVE_FROM_WALL = "api/secured/removeFromWallById";
URL_SHORTLIST_PROFILE = "api/secured/shortlistProfile";
URL_LOAD_SHORTLISTED_PROFILES = "api/secured/loadShortlistedProfiles";
URL_LOAD_BUY_CONTACTS_PROFILES = "api/secured/loadBuyContactsProfiles";
URL_LOAD_REMOVED_PROFILES = "api/secured/loadRemovedProfiles";
URL_UNDO_REMOVE_FROM_WALL = "api/secured/undoRemoveFromWall";


 //Profile Connection Controller
URL_SEND_CONNECTION_REQUEST = "api/secured/sendConnectionRequest";
URL_SEARCH_PROFILES = "api/secured/searchProfiles";
URL_WITHDRAW_REQUEST = "api/secured/withdrawRequest";
URL_ACTION_REQUEST = "api/secured/actionRequest";
URL_GET_SECURE_DETAILS = "api/secured/getSecureDetails";
URL_BUY_SECURE_DETAILS = "api/secured/buySecureDetails";


 //ExpectationsController
URL_LOAD_EXPECTATIONS = "api/secured/loadExpectations";
URL_SET_EXPECTATIONS = "api/secured/setExpectations";

 //Notifications Controller
URL_LOAD_NOTIFICATIONS = "api/secured/loadNotifications";
URL_LOAD_UNREAD_NOTIFICATIONS = "api/unsecured/loadUnreadNotifications";
URL_MARK_NOTIFICATIONS_READ = "api/secured/markNotificationAsRead";

 //Profile Controller

URL_UPLOAD_IMAGE = "api/secured/uploadImage";
URL_GET_IMAGE = "api/secured/getImage/{fileType}/{entityId}/{fileName}";
URL_GET_THUMBNAIL = "api/secured/thumbnail/{entityId}";
URL_GET_LOGGED_IN_PROFILE = "api/unsecured/profile/getLoggedInProfile";
URL_UPDATE_MY_PROFILE = "api/secured/profile/updateMyProfile";
URL_DELETE_MY_PROFILE = "api/secured/profile/deleteMyProfile";
URL_SEARCH_BY_ID = "api/unsecured/searchById";


 //Wallet Controller
URL_LOAD_WALLET_DETAILS = "api/secured/loadWalletDetails";
URL_LOAD_WALLET_TRANSACTIONS = "api/secured/loadWalletTransactions";
URL_RECHARGE_WALLET = "api/secured/rechargeWallet";
URL_WALLET_SUCCESS = "api/unsecured/walletSuccess";
URL_WALLET_FAILURE = "api/unsecured/walletFailure";
URL_WALLET_SUCCESS_WEBHOOK = "api/unsecured/57297c487bbe5b0b15211670cd3986e0ebf09cbe7db571ff5f82360b560b1";
URL_WALLET_FAILURE_WEBHOOK = "api/unsecured/57297c487bbe5b0b15211670cd3986e0ebf09cbe7db571ff5f82360b560b9";


 //Admin Controller
URL_ADMIN_WALLET_SUCCESS = "server/admin/Success";
URL_ADMIN_WALLET_FAILURE = "server/admin/Failure";
URL_ADMIN_REGISTERED = "server/admin/registered";
URL_ADMIN_REQUESTED = "server/admin/requested";
URL_ADMIN_SEND_RANDOM_SMS = "server/admin/sendRandomSms";
URL_ADMIN_GET_PAYMENT_STATUS = "server/admin/getAdminPaymentStatus";
URL_ADMIN_DELETE_ACCOUNT = "server/admin/deleteAccount";
URL_ADMIN_GET_PROFILE_ID = "server/admin/getProfileId";
URL_ADMIN_DELETE_PIC = "server/admin/deletePic";;
// Define App Specific Directives Here

edgeApp.directive('viewPersonal', function() {
  return {
    scope: {
    	profile: '=profile',
    	es: '=es'
    },
    templateUrl: 'modules/templatesView/view_personal.html'
  };
});

edgeApp.directive('editPersonal', function() {
  return {
    scope: {
    	profile: '=profile',
    	es: '=es'
    },
    templateUrl: 'modules/templates/edit_personal.html'
  };
});


edgeApp.directive('editExpectations', function() {
  return {
    scope: {
    	profile: '=profile',
    	es: '=es'
    },
    templateUrl: 'modules/templates/edit_expectations.html'
  };
});


edgeApp.directive('editLogin', function() {
  return {
    scope: {
    	profile: '=profile',
    	es: '=es'
    },
    templateUrl: 'modules/templates/edit_login.html'
  };
});

edgeApp.directive('viewDetails', function() {
  return {
    scope: {
    	profile: '=profile',
    	es: '=es'
    },
    templateUrl: 'modules/templatesView/view_details.html'
  };
});

edgeApp.directive('editPhysical', function() {
  return {
    scope: {
    	profile: '=profile',
    	es: '=es'
    },
    templateUrl: 'modules/templates/edit_physical.html'
  };
});

edgeApp.directive('editEthnicity', function() {
  return {
    scope: {
    	profile: '=profile',
    	es: '=es'
    },
    templateUrl: 'modules/templates/edit_ethnicity.html'
  };
});

edgeApp.directive('editProfessional', function() {
  return {
    scope: {
    	profile: '=profile',
    	es: '=es'
    },
    templateUrl: 'modules/templates/edit_professional.html'
  };
});

edgeApp.directive('viewKundali', function() {
  return {
    scope: {
    	profile: '=profile',
    	es: '=es'
    },
    templateUrl: 'modules/templatesView/view_kundali.html'
  };
});

edgeApp.directive('editKundali', function() {
  return {
    scope: {
    	profile: '=profile',
    	es: '=es'
    },
    templateUrl: 'modules/templates/edit_kundali.html'
  };
});

edgeApp.directive('viewFamily', function() {
  return {
    scope: {
    	profile: '=profile',
    	es: '=es'
    },
    templateUrl: 'modules/templatesView/view_family.html'
  };
});

edgeApp.directive('editFamily', function() {
  return {
    scope: {
    	profile: '=profile',
    	es: '=es'
    },
    templateUrl: 'modules/templates/edit_family.html'
  };
});


edgeApp.directive('editSecure', function() {
  return {
    scope: {
    	profile: '=profile',
    	es: '=es'
    },
    templateUrl: 'modules/templates/edit_secure.html'
  };
});


edgeApp.directive('buttonPanel', function() {
  return {
    scope: {
    	profile: '=profile',
    	es: '=es'
    },
    templateUrl: 'modules/profile/button_panel.html'
  };
});


edgeApp.directive('profileSecure', function() {
  return {
    scope: {
    	profile: '=profile',
        es: '=es'
    },
    templateUrl: 'modules/profile/profile_secure.html'
  };
});

edgeApp.directive('profileNonSecure', function() {
  return {
    scope: {
    	profile: '=profile',
    	es: '=es',
    	buttonMode: '=buttonMode'
    },
    templateUrl: 'modules/profile/profile_non_secure.html'
  };
});


edgeApp.directive('file', function () {
    return {
        scope: {
            file: '='
        },
        link: function (scope, el, attrs) {
            el.bind('change', function (event) {
                var file = event.target.files[0];
                scope.file = file ? file : undefined;
                scope.$apply();
            });
        }
    };
});

// Other Functions

var appInitFun = function ($scope, $http, $modal, $log, $sce, $cookies, $window){

	$scope.es.createUser = function (createUserForm) {
        if(createUserForm.$invalid){
                var msg = "Please fill up all the fields to proceed";
                basicPopup($scope, $http, "Information", msg,"Ok");
                return;
        }
        $scope.es.signUpForm.completeNumber = "+91" + $scope.es.signUpForm.phoneNumber;
        $scope.es.sendVerificationCode($scope.es.signUpForm.completeNumber);
        $scope.es.openPopup("SIGN_UP_POP_UP");
    };

    $scope.es.setProfileCookie = function (profile) {
        $cookies.es_profile = JSON.stringify(profile);
    };

    $scope.es.readProfileCookie = function () {
        try{
            $scope.es.editProfile = JSON.parse($cookies.es_profile);
            if(!$scope.es.editProfile){
                $scope.es.editProfile = {};
                $scope.es.editProfile.regPage = 1;
            }
        }
        catch(error){
            console.error("Not a JSON response");
            $scope.es.editProfile = {};
            $scope.es.editProfile.regPage = 1;
        }

    };

	$scope.es.openMyProfile = function () {
		openMyProfile($scope, $http);
	};

	$scope.es.getLoggedInProfile = function () {
    		getLoggedInProfile($scope, $http);
    	};
	
	$scope.es.updateMyProfile = function (updateForm) {
		updateMyProfile($scope, $http, updateForm);
	};


	$scope.es.deleteMyProfile = function () {
		deleteMyProfile($scope, $http);
	};


	$scope.es.searchById = function () {
		searchById($scope, $http);
	};
	
	$scope.es.viewFullProfile = function (showProfile) {
		viewFullProfile($scope, $http, showProfile);
	};

	$scope.es.setBirthDate = function (editProfile) {
        if(editProfile.profileDetails.birthDay &&
           editProfile.profileDetails.birthMonth &&
           editProfile.profileDetails.birthYear
        ){
            editProfile.profileDetails.birthDate =
              editProfile.profileDetails.birthYear + "-"
              + editProfile.profileDetails.birthMonth + "-"
              + editProfile.profileDetails.birthDay

        }
    };


	$scope.es.openFullProfileById = function (showInternalId) {
		openFullProfileById($scope, $http, showInternalId);
	};
	
	// COMMON


	$scope.es.showQuickLinks = function (profile) {
		showQuickLinks($scope, $http, profile);
	};
	
	$scope.es.showAlbum = function (profile) {
		showAlbum($scope, $http, profile);
	};

	$scope.es.showImageInPopup = function (title, imgPath) {
		showImageInPopup($scope, $http, title, imgPath);
	};

	$scope.es.uploadImage = function (imageType) {
		uploadImage($scope, $http, imageType);
	};

    $scope.es.getVerificationCode = function () {
        getVerificationCode($scope, $http);
    };

    $scope.es.getPaymentStatus = function () {
        getPaymentStatus($scope, $http);
    };

    // ADMIN

    $scope.es.deletePic = function () {
        deletePic($scope, $http);
    };

    $scope.es.createAdmin = function () {
        createAdmin($scope, $http);
    };

    $scope.es.deleteAccount = function () {
        deleteAccount($scope, $http);
    };

    $scope.es.getProfileId = function () {
        getProfileId($scope, $http);
    };

    $scope.es.sendRandomSms = function () {
        sendRandomSms($scope, $http);
    };

    $scope.es.getAdminPaymentStatus = function () {
        getAdminPaymentStatus($scope, $http);
    };

	// WALLET
	
	$scope.es.initializeWallet = function () {
		initializeWallet($scope, $http);
	};

	$scope.es.loadWalletDetails = function () {
        loadWalletDetails($scope, $http);
    };


	$scope.es.loadWalletTransactions = function () {
        loadWalletTransactions($scope, $http);
    };

    // SUPPORT

	$scope.es.initiateRefundRequest = function () {
		initiateRefundRequest($scope, $http);
	};

	// ADMIN

	$scope.es.simulateWebHook = function () {
		simulateWebHook($scope, $http);
	};
	
	$scope.es.simulateUIHook = function () {
		simulateUIHook($scope, $http);
	};

	$scope.es.simulateTest = function () {
		simulateTest($scope, $http);
	};
	
	// EXPECTATIONS FUNCTIONS

	$scope.es.initializeExpectations = function () {
		initializeExpectations($scope, $http);
	};

	$scope.es.setExpectations = function (expectationsForm) {
	    confirmationMsg = $scope.es.lg.UpdateExpectations;
        confirmationPopup(
                $scope, $http, confirmationMsg,
                function(){
                    setExpectations($scope, $http, expectationsForm);
                }
        );

	};

	$scope.es.oneTimeSearch = function (expectationsForm) {
    	oneTimeSearch($scope, $http, expectationsForm);
    };


	$scope.es.guestSearch = function (expectationsForm) {
    	guestSearch($scope, $http, expectationsForm);
    };

	// WALL FUNCTIONS
	
	$scope.es.loadWallProfiles = function () {
		loadWallProfiles($scope, $http);
	};
	
	$scope.es.initializeWall = function () {
		initializeWall($scope, $http);
	};

	$scope.es.setQuickSearchMenu = function (searchType) {
		setQuickSearchMenu($scope, $http, searchType);
	};
	
	$scope.es.removeFromWall = function (profileDto, mode) {
	        confirmationMsg = profileDto.profileDetails.firstName + " ("  + profileDto.profileDetails.profileId + ") " + " : " + $scope.es.lg.removeFromWall;
    		confirmationPopup(
    		        $scope, $http, confirmationMsg,
    		        function(){
    		            removeFromWall($scope, $http, profileDto, mode);
    		            $scope.es.closePopup('FULL_PROFILE_POPUP');
    		        }
    		);

	};

	$scope.es.shortlistProfile = function (profileDto) {
        confirmationMsg = profileDto.profileDetails.firstName + " ("  + profileDto.profileDetails.profileId + ") " + " : " + $scope.es.lg.shortlistProfile;

        confirmationPopup(
                $scope, $http, confirmationMsg,
                function(){
                    shortlistProfile($scope, $http, profileDto);
                    $scope.es.closePopup('FULL_PROFILE_POPUP');
                }
        );
    };

	$scope.es.sendConnectionRequest = function (profileDto, mode) {
        confirmationMsg = profileDto.profileDetails.firstName + " ("  + profileDto.profileDetails.profileId + ") " + " : " + $scope.es.lg.sendConnectionRequest;

		confirmationPopup(
		        $scope, $http, confirmationMsg,
		        function(){
		            sendConnectionRequest($scope, $http, profileDto, mode);
		            $scope.es.closePopup('FULL_PROFILE_POPUP');
		        }
		);
	};

	// SEARCH FUNCTIONS

	$scope.es.initializeSearch = function () {
		initializeSearch($scope, $http);
	};

	$scope.es.searchById = function () {
		searchById($scope, $http);
	};

	$scope.es.loadShortlistedProfiles = function () {
		loadShortlistedProfiles($scope, $http);
	};

	$scope.es.loadBuyContactsProfiles = function () {
		loadBuyContactsProfiles($scope, $http);
	};

	$scope.es.loadRemovedProfiles = function () {
		loadRemovedProfiles($scope, $http);
	};

	$scope.es.loadGrooms = function (searchType) {
		loadGrooms($scope, $http, searchType);
	};

	$scope.es.loadBrides = function (searchType) {
		loadBrides($scope, $http, searchType);
	};

	$scope.es.loadNewProfiles = function () {
		loadNewProfiles($scope, $http);
	};


	$scope.es.undoRemoveFromWall = function (profileDto) {

		confirmationMsg = profileDto.profileDetails.firstName + " ("  + profileDto.profileDetails.profileId + ") " + " : " + $scope.es.lg.undoRemoveFromWall;
        confirmationPopup(
                $scope, $http, confirmationMsg,
                function(){
                    undoRemoveFromWall($scope, $http, profileDto);
                    $scope.es.closePopup('FULL_PROFILE_POPUP');
                }
        );
	};

	$scope.es.searchProfiles = function () {
		searchProfiles($scope, $http);
	};

	$scope.es.getSecureDetails = function (profileDto) {
		getSecureDetails($scope, $http, profileDto);
	};

	$scope.es.buySecureDetails = function (profileDto) {
		buySecureDetails($scope, $http, profileDto);
	};

    $scope.es.withdrawRequest = function (profileDto, amount) {
        confirmationMsg = profileDto.profileDetails.firstName + " ("  + profileDto.profileDetails.profileId + ") " + " : " + $scope.es.lg.withdrawRequest;
        confirmationPopup(
                $scope, $http, confirmationMsg,
                function(){
                    withdrawRequest($scope, $http, profileDto);
                    $scope.es.closePopup('FULL_PROFILE_POPUP');
                }
        );

	};


	$scope.es.actionRequest = function (profileDto, connectionStatus) {
		var message="";
		if(connectionStatus == 'Accepted'){
			confirmationMsg = profileDto.profileDetails.firstName + " ("  + profileDto.profileDetails.profileId + ") " + " : " + $scope.es.lg.acceptedMsg;

            confirmationPopup(
                                    $scope, $http, confirmationMsg,
                                    function(){
                                        actionRequest($scope, $http, profileDto, connectionStatus);
                                        $scope.es.closePopup('FULL_PROFILE_POPUP');
                                    }
                            );

		}else if(connectionStatus == 'Rejected'){
			confirmationMsg= profileDto.profileDetails.firstName + " ("  + profileDto.profileDetails.profileId + ") " + " : " + $scope.es.lg.rejectedMsg;

			confirmationPopup(
                                    $scope, $http, confirmationMsg,
                                    function(){
                                        actionRequest($scope, $http, profileDto, connectionStatus);
                                        $scope.es.closePopup('FULL_PROFILE_POPUP');
                                    }
                            );
		};



	};
	
	// NOTIFICATIONS

	$scope.es.loadUnreadNotifications = function () {
		loadUnreadNotifications($scope, $http);
	};
	
	$scope.es.initializeNotifications = function () {
		initializeNotifications($scope, $http);
	};
	
	$scope.es.markNotificationAsRead = function (notificationId) {
		markNotificationAsRead($scope, $http, notificationId);
	};

	// GTAG
	$scope.es.gtag_signup = function () {
        gtag_signup($scope, $http);
    };

    $scope.es.gtag_checkout = function (value) {
        gtag_checkout($scope, $http, value);
    };
	
};	

var viewFullProfile = function ($scope, $http, showProfile){
    intervalMin=2000;
	$scope.es.showProfile = showProfile;
	$scope.es.showProfile.disabledInput = 'true';
	$scope.es.showProfile.interval2=intervalMin;
	$scope.es.imgCount = showProfile.profileDetails.profileImages.length;
	$scope.es.showProfile.interval1=intervalMin * $scope.es.imgCount;
	$scope.es.openPopup('FULL_PROFILE_POPUP');
};


var gtag_signup = function ($scope, $http) {
    var url = $scope.es.gtag_url;
    var callback = function () {
//        if (typeof(url) != 'undefined') {
//          window.location = url;
//        }
      };

    gtag(
        'event',
        'conversion',
        { 'send_to': 'AW-721751908/aS5HCPvJ1eUBEOSelNgC', 'event_callback': callback }
    );
    return false;
}

var gtag_checkout = function ($scope, $http, value) {
    var url = $scope.es.gtag_url;
      var callback = function () {
//        if (typeof(url) != 'undefined') {
//          window.location = url;
//        }
      };
  gtag('event', 'conversion', {
      'send_to': 'AW-721751908/OQNRCIDk1eUBEOSelNgC',
      'value': value,
      'currency': 'INR',
      'event_callback': callback
  });
  return false;
}
;
var appInitVar = function ($scope, $http, $modal, $log, $sce, $cookies, $window){

	if (isMobile()) {
		// Mobile - On Launch Do not show Menu 
		$scope.es.showMenu=false;
	}else{
		$scope.es.showMenu=true;
	}
	
	//$scope.es.setSelectedView("homeView");
	$scope.es.showSearch = 'C';
	$scope.es.searchedProfiles = {};
	/*$scope.es.editProfile = {};
	$scope.es.secureProfile = {};*/
	
	$scope.es.search = {};
	$scope.es.search.type="None";
	
	//setTimeout(function() {$scope.es.setSelectedView("homeView"); }, 3000);
}


var appOnLoad = function($scope, $http, $modal, $log, $sce, $routeParams, $cookies){

	$scope.es.loadUnreadNotifications();

	$scope.es.getLoggedInProfile();

	// https://localhost:8443/#/p/FETWP1WO
	// https://localhost:8443/#/p/FETWP1WO

	if($scope.es.searchId){
	    $scope.es.wallHeader = $scope.es.lg.SearchById + " - " + $scope.es.searchId;
		$scope.es.openFullProfileById($scope.es.searchId);
		$scope.es.setSelectedInternal("homeView");
	}

	// Handle Referral Code

    if( $routeParams.refCode){
        //if(!$cookies.refCodeCookie){
            $cookies.refCodeCookie = $routeParams.refCode;
        //}
        $scope.es.setSelectedInternal("registerView");
    }

	$scope.es.refCodeCookie = $cookies.refCodeCookie;

	$scope.es.homeSlides = {};
	$scope.es.homeSlides.title = "Home Page";
	$scope.es.homeSlides.interval = 4000;
	$scope.es.homeSlides.slides = [
    		{
    			htmlTemplate: "/modules/home/partial_accepted.html"
    	    },
    	    {
    	    	htmlTemplate: "/modules/home/partial_lifetime.html"
    	    },
    	    {
    	    	htmlTemplate: "/modules/home/partial_contacts.html"
    		},
    	    {
    	    	htmlTemplate: "/modules/home/partial_easy.html"
    	    }
    	  ];

	$scope.es.homeSlides.slidesOld = [
		{
			image: "modules/home/Email1.png"
	    },
	    {
	    	image: "modules/home/300.png"
	    },
	    {
	    	image: "modules/home/Contact.png"
		},
	    {
	    	image: "modules/home/Lifetime.png"
	    }
	  ];
	
};
;
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
;var initializeExpectations = function ($scope, $http){
	//if($scope.es.loggedInUserId){
		loadExpectations($scope, $http);
	//}
};


var loadExpectations = function ($scope, $http){	
	startAjax('LOAD_EXPECTATIONS', $scope);
	$http.post(URL_LOAD_EXPECTATIONS, $scope.es.editExpectations ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('LOAD_EXPECTATIONS', $scope, data, status, headers, config);
    	$scope.es.editExpectations = data.responseData;
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('LOAD_EXPECTATIONS', $scope, data, status, headers, config);
    });
};


var setExpectations = function ($scope, $http, expectationsForm){	

	startAjax('EXPECTATIONS', $scope);
	$http.post(URL_SET_EXPECTATIONS, $scope.es.editExpectations ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('EXPECTATIONS', $scope, data, status, headers, config);
    	$scope.es.setSelectedView("homeView");
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('EXPECTATIONS', $scope, data, status, headers, config);
    });
};


;var initDropDowns = function($scope, $http, $modal, $log, $sce, $cookies, $window){

initDropDownsHuman($scope, $http, $modal, $log, $sce, $cookies, $window);
initDropDownsGeo($scope, $http, $modal, $log, $sce, $cookies, $window);
initDropDownsTime($scope, $http, $modal, $log, $sce, $cookies, $window);
initDropDownsCast($scope, $http, $modal, $log, $sce, $cookies, $window);


};


;var initDropDownsCast = function($scope, $http, $modal, $log, $sce, $cookies, $window){

$scope.es.religionList = {
 "Hindu":"Hindu"
,"Jain":"Jain"
,"Buddhist":"Buddhist"
,"Christian":"Christian"

,"Bahai":"Bahai"
,"Jewish":"Jewish"
,"Muslim":"Muslim"
,"Parsi":"Parsi"
,"Sikh":"Sikh"
,"Other":"Other"

};

$scope.es.casteMap = {

"Bahai" : {
"Bahai":"Bahai"
},

"Buddhist" : {
"Buddhist":"Buddhist"
},

"Christian" : {
"Anglo_Indian":"Anglo Indian"
,"Born_Again":"Born Again"
,"Brethren":"Brethren"
,"Catholic":"Catholic"
,"Catholic_Knanaya":"Catholic - Knanaya"
,"Catholic_Latin":"Catholic - Latin"
,"Catholic_Malankara":"Catholic - Malankara"
,"Catholic_Roman":"Catholic - Roman"
,"Catholic_Syrian":"Catholic - Syrian"
,"Chaldean":"Chaldean"
,"CMS":"CMS"
,"CSI":"CSI"
,"Evangelical":"Evangelical"
,"Indian_Orthodox":"Indian Orthodox"
,"Jacobite":"Jacobite"
,"Jacobite_Knanaya":"Jacobite - Knanaya"
,"Jacobite_Syrian":"Jacobite - Syrian"
,"Kharvi":"Kharvi"
,"Knanaya":"Knanaya"
,"Mangalorean":"Mangalorean"
,"Manglorean":"Manglorean"
,"Marthomite":"Marthomite"
,"Nadar":"Nadar"
,"Pentecost":"Pentecost"
,"Protestant":"Protestant"
,"Syrian":"Syrian"
,"Syrian_Malabar":"Syrian - Malabar"
,"Syrian_Orthodox":"Syrian - Orthodox"
,"Syro_Malabar":"Syro - Malabar"
,"Other":"Other"
},

"Hindu" : {


 "96_Kuli_Maratha":"96 Kuli Maratha"
,"Agri":"Agri"
,"Banjara":"Banjara"
,"Bhandari":"Bhandari"
,"Bhavsar":"Bhavsar"
,"Brahmin":"Brahmin"
,"Brahmin_Deshastha":"Brahmin Deshastha"
,"Brahmin_Gaud_Saraswat_GSB":"Brahmin Gaud Saraswat GSB"
,"Brahmin_Koknastha":"Brahmin Koknastha"
,"Chambhar":"Chambhar"
,"Deshastha_Maratha":"Deshastha Maratha"
,"Devang_Koshthi":"Devang Koshthi"
,"Dhangar":"Dhangar"
,"Kokanastha_Maratha":"Kokanastha Maratha"
,"Koli":"Koli"
,"Koli_Mahadev":"Koli Mahadev"
,"Kshatriya":"Kshatriya"
,"Kumhar_Kumbhar":"Kumhar Kumbhar"
,"Kunbi":"Kunbi"
,"Lingayat":"Lingayat"
,"Lohar":"Lohar"
,"Mahar":"Mahar"
,"Mali":"Mali"
,"Maratha":"Maratha"
,"Matang":"Matang"
,"Nhavi":"Nhavi"
,"Other":"Other"
,"Others":"Others"
,"Shimpi":"Shimpi"
,"Sonar":"Sonar"
,"Sutar":"Sutar"
,"Teli":"Teli"
,"Vaishya_Vani":"Vaishya Vani"
,"Vanjari":"Vanjari"



,"Ad_Dharmi":"Ad Dharmi"
,"Adi_Andhra":"Adi Andhra"
,"Adi_Dravida":"Adi Dravida"
,"Adi_Karnataka":"Adi Karnataka"
,"Agamudayar":"Agamudayar"
,"Aggarwal":"Aggarwal"
,"Agri":"Agri"
,"Ahir":"Ahir"
,"Ahom":"Ahom"
,"Ambalavasi":"Ambalavasi"
,"Arora":"Arora"
,"Arunthathiyar":"Arunthathiyar"
,"Arya_Vysya":"Arya Vysya"
,"Badhai":"Badhai"
,"Baghel_Gaderiya":"Baghel-Gaderiya"
,"Baidya":"Baidya"
,"Baishnab":"Baishnab"
,"Baishya":"Baishya"
,"Balija_Naidu":"Balija Naidu"
,"Balija":"Balija"
,"Bania":"Bania"
,"Banik":"Banik"
,"Banjara":"Banjara"
,"Bari":"Bari"
,"Barujibi":"Barujibi"
,"Besta":"Besta"
,"Bhandari":"Bhandari"
,"Bhatia":"Bhatia"
,"Bhatraju":"Bhatraju"
,"Bhavsar":"Bhavsar"
,"Bhovi_Bhoi":"Bhovi-Bhoi"
,"Billava":"Billava"
,"Bisa_Agarwal":"Bisa Agarwal"
,"Bishnoi_Vishnoi":"Bishnoi-Vishnoi"
,"Boyer":"Boyer"
,"Brahmbatt":"Brahmbatt"
,"Brahmin_6000_Niyogi":"Brahmin 6000 Niyogi"
,"Brahmin_Anavil":"Brahmin Anavil"
,"Brahmin_Audichya":"Brahmin Audichya"
,"Brahmin_Bajkhedwal":"Brahmin Bajkhedwal"
,"Brahmin_Bardai":"Brahmin Bardai"
,"Brahmin_Barendra":"Brahmin Barendra"
,"Brahmin_Bhargava":"Brahmin Bhargava"
,"Brahmin_Bhatt":"Brahmin Bhatt"
,"Brahmin_Bhumihar":"Brahmin Bhumihar"
,"Brahmin_Brahacharanam":"Brahmin Brahacharanam"
,"Brahmin_BrahmBhatt":"Brahmin BrahmBhatt"
,"Brahmin_Brajastha_Mathil":"Brahmin Brajastha Mathil"
,"Brahmin_Dadhich":"Brahmin Dadhich"
,"Brahmin_Daivadnya":"Brahmin Daivadnya"

,"Brahmin_Deshastha":"Brahmin Deshastha"
,"Brahmin_Deshastha":"Brahmin Deshastha"
,"Brahmin_Devrukhe":"Brahmin Devrukhe"
,"Brahmin_Dhiman":"Brahmin Dhiman"
,"Brahmin_Dravida":"Brahmin Dravida"
,"Brahmin_Dunua":"Brahmin Dunua"
,"Brahmin_Embrandiri":"Brahmin Embrandiri"
,"Brahmin_Garhwali":"Brahmin Garhwali"
,"Brahmin_Gaud_Saraswat_GSB":"Brahmin Gaud Saraswat (GSB)"
,"Brahmin_Gaur":"Brahmin Gaur"
,"Brahmin_Goswami":"Brahmin Goswami"
,"Brahmin_Gujar_Gaur":"Brahmin Gujar Gaur"
,"Brahmin_Gurukkal":"Brahmin Gurukkal"
,"Brahmin_Halua":"Brahmin Halua"
,"Brahmin_Havyaka":"Brahmin Havyaka"
,"Brahmin_Hoysala":"Brahmin Hoysala"
,"Brahmin_Iyengar":"Brahmin Iyengar"
,"Brahmin_Iyer":"Brahmin Iyer"
,"Brahmin_Jangid":"Brahmin Jangid"
,"Brahmin_Jangra":"Brahmin Jangra"
,"Brahmin_Jhadua":"Brahmin Jhadua"
,"Brahmin_Jhijhotiya":"Brahmin Jhijhotiya"
,"Brahmin_Jogi":"Brahmin Jogi"
,"Brahmin_Jyotish":"Brahmin Jyotish"
,"Brahmin_Kanyakubj":"Brahmin Kanyakubj"
,"Brahmin_Karhade":"Brahmin Karhade"
,"Brahmin_Kashmiri_Pandit":"Brahmin Kashmiri Pandit"
,"Brahmin_Khadayat":"Brahmin Khadayat"
,"Brahmin_Khandelwal":"Brahmin Khandelwal"
,"Brahmin_Khedaval":"Brahmin Khedaval"
,"Brahmin_Koknastha":"Brahmin Koknastha"
,"Brahmin_Kota":"Brahmin Kota"
,"Brahmin_Kulin":"Brahmin Kulin"
,"Brahmin_Kumaoni":"Brahmin Kumaoni"
,"Brahmin_Madhwa":"Brahmin Madhwa"
,"Brahmin_Maithil":"Brahmin Maithil"
,"Brahmin_Malviya":"Brahmin Malviya"
,"Brahmin_Marwari":"Brahmin Marwari"
,"Brahmin_Mevada":"Brahmin Mevada"
,"Brahmin_Modh":"Brahmin Modh"
,"Brahmin_Mohyal":"Brahmin Mohyal"
,"Brahmin_Nagar":"Brahmin Nagar"
,"Brahmin_Namboodiri":"Brahmin Namboodiri"
,"Brahmin_Narmadiya":"Brahmin Narmadiya"
,"Brahmin_Paliwal":"Brahmin Paliwal"
,"Brahmin_Panda":"Brahmin Panda"
,"Brahmin_Pandit":"Brahmin Pandit"
,"Brahmin_Panicker":"Brahmin Panicker"
,"Brahmin_Pareek":"Brahmin Pareek"
,"Brahmin_Pushkarna":"Brahmin Pushkarna"

,"Brahmin_Rajgor":"Brahmin Rajgor"
,"Brahmin_Rarhi":"Brahmin Rarhi"
,"Brahmin_Rigvedi":"Brahmin Rigvedi"
,"Brahmin_Rudraj":"Brahmin Rudraj"
,"Brahmin_Sakaldwipi":"Brahmin Sakaldwipi"
,"Brahmin_Sanadya":"Brahmin Sanadya"
,"Brahmin_Sanketi":"Brahmin Sanketi"
,"Brahmin_Saraswat":"Brahmin Saraswat"
,"Brahmin_Sarotri":"Brahmin Sarotri"
,"Brahmin_Sarua":"Brahmin Sarua"
,"Brahmin_Saryuparin":"Brahmin Saryuparin"
,"Brahmin_Shivalli":"Brahmin Shivalli"
,"Brahmin_Shrimali":"Brahmin Shrimali"
,"Brahmin_Sikhwal":"Brahmin Sikhwal"
,"Brahmin_Smartha":"Brahmin Smartha"
,"Brahmin_Sri_Vishnava":"Brahmin Sri Vishnava"
,"Brahmin_Stanika":"Brahmin Stanika"
,"Brahmin_Tapodhan":"Brahmin Tapodhan"
,"Brahmin_Tyagi":"Brahmin Tyagi"
,"Brahmin_Vaidiki":"Brahmin Vaidiki"
,"Brahmin_Vaikhawas":"Brahmin Vaikhawas"
,"Brahmin_Vaishnav":"Brahmin Vaishnav"
,"Brahmin_Valam":"Brahmin Valam"
,"Brahmin_Velanadu":"Brahmin Velanadu"
,"Brahmin_Viswa":"Brahmin Viswa"
,"Brahmin_Vyas":"Brahmin Vyas"
,"Brahmin_Yajurvedi":"Brahmin Yajurvedi"
,"Brahmin_Zalora":"Brahmin Zalora"
,"Brahmin":"Brahmin"
,"Brahmo":"Brahmo"
,"Bunt_Shetty":"Bunt-Shetty"
,"Chamar":"Chamar"
,"Chambhar":"Chambhar"
,"Chandravanshi_Kahar":"Chandravanshi Kahar"
,"Chasa":"Chasa"
,"Chattada_Sri_Vaishnava":"Chattada Sri Vaishnava"
,"Chaudary":"Chaudary"
,"Chaurasia":"Chaurasia"
,"Chettiar":"Chettiar"
,"Chhetri":"Chhetri"
,"CKP":"CKP"
,"Coorgi":"Coorgi"
,"Deshastha_Maratha":"Deshastha Maratha"
,"Devadigas":"Devadigas"
,"Devang_Koshthi":"Devang Koshthi"
,"Devanga":"Devanga"
,"Devendra_Kula_Vellalar":"Devendra Kula Vellalar"
,"Dhangar":"Dhangar"
,"Dheevara":"Dheevara"
,"Dhoba":"Dhoba"

,"Dhobi":"Dhobi"
,"Dhor_Dhoar":"Dhor-Dhoar"
,"Dusadh":"Dusadh"
,"Edigas":"Edigas"
,"Ezhava":"Ezhava"
,"Ezhuthachan":"Ezhuthachan"
,"Gabit":"Gabit"
,"Gangai_Ganesh":"Gangai-Ganesh"
,"Ganiga":"Ganiga"
,"Garhwali":"Garhwali"
,"Gavali":"Gavali"
,"Gavara":"Gavara"
,"Ghumar":"Ghumar"
,"Goala":"Goala"
,"Goan":"Goan"
,"Gomantak_Maratha":"Gomantak Maratha"
,"Gond_Gondi_Raj_Gond":"Gond-Gondi-Raj Gond"
,"Gondhali":"Gondhali"
,"Goud":"Goud"
,"Gounder":"Gounder"
,"Gowda":"Gowda"
,"Gramani":"Gramani"
,"Gudia":"Gudia"
,"Gujjar":"Gujjar"
,"Gupta":"Gupta"
,"Gurav":"Gurav"
,"Halba_Koshti":"Halba Koshti"
,"Hegde":"Hegde"
,"Jaiswal":"Jaiswal"
,"Jangam":"Jangam"
,"Jat":"Jat"
,"Jatav":"Jatav"
,"Kadava_patel":"Kadava patel"
,"Kahar":"Kahar"
,"Kaibarta":"Kaibarta"
,"Kalal":"Kalal"
,"Kalanji":"Kalanji"
,"Kalar":"Kalar"
,"Kalinga_Vysya":"Kalinga Vysya"
,"Kalwar":"Kalwar"
,"Kamboj":"Kamboj"
,"Kamma":"Kamma"
,"Kansari":"Kansari"
,"Kapol":"Kapol"
,"Kapu_Munnuru":"Kapu Munnuru"
,"Kapu":"Kapu"
,"Karana":"Karana"
,"Karmakar":"Karmakar"
,"Karuneegar":"Karuneegar"
,"Kasar":"Kasar"


,"Kashyap":"Kashyap"
,"Kayastha":"Kayastha"
,"Khandayat":"Khandayat"
,"Khandelwal":"Khandelwal"
,"Kharvi":"Kharvi"
,"Kharwar":"Kharwar"
,"Khatik":"Khatik"
,"Khatri":"Khatri"
,"Koeri_Koiri":"Koeri-Koiri"
,"Kokanastha_Maratha":"Kokanastha Maratha"
,"Koli_Mahadev":"Koli Mahadev"
,"Koli":"Koli"
,"Kongu_Vellala_Gounder":"Kongu Vellala Gounder"
,"Konkani":"Konkani"
,"Kori":"Kori"
,"Koshti":"Koshti"
,"Kshatriya_Agnikula":"Kshatriya Agnikula"
,"Kshatriya":"Kshatriya"
,"Kudumbi":"Kudumbi"
,"Kulalar":"Kulalar"
,"Kulita":"Kulita"
,"Kumawat":"Kumawat"
,"Kumbhakar":"Kumbhakar"
,"Kumhar_Kumbhar":"Kumhar-Kumbhar"
,"Kummari":"Kummari"
,"Kunbi":"Kunbi"
,"Kurmi_kshatriya":"Kurmi kshatriya"
,"Kurmi":"Kurmi"
,"Kuruba":"Kuruba"
,"Kuruhina_shetty":"Kuruhina shetty"
,"Kurumbar":"Kurumbar"
,"Kushwaha":"Kushwaha"
,"Kutchi_Gurjar":"Kutchi Gurjar"
,"Kutchi":"Kutchi"
,"Lambadi":"Lambadi"
,"Leva_Patidar":"Leva Patidar"
,"Leva_Patil":"Leva Patil"
,"Lingayat":"Lingayat"
,"Lodhi_Rajput":"Lodhi Rajput"
,"Lohana":"Lohana"
,"Lohar":"Lohar"
,"Lubana":"Lubana"
,"Madiga":"Madiga"
,"Mahajan":"Mahajan"
,"Mahar":"Mahar"
,"Maheshwari":"Maheshwari"
,"Mahindra":"Mahindra"
,"Mahisya":"Mahisya"
,"Majabi_Mazhbi":"Majabi-Mazhbi"
,"Mala":"Mala"

,"Mali":"Mali"
,"Mallah":"Mallah"
,"Manipuri":"Manipuri"
,"Manjhi":"Manjhi"
,"Mapila":"Mapila"
,"Maratha":"Maratha"
,"96_Kuli_Maratha":"96 Kuli Maratha"
,"Maravar":"Maravar"
,"Maruthuvar":"Maruthuvar"
,"Marwari":"Marwari"
,"Matang":"Matang"
,"Mathur":"Mathur"
,"Maurya":"Maurya"
,"Meena":"Meena"
,"Meenavar":"Meenavar"
,"Mehra":"Mehra"
,"Menon":"Menon"
,"Meru_darji":"Meru darji"
,"Meru":"Meru"
,"Modak":"Modak"
,"Mogaveera":"Mogaveera"
,"Monchi":"Monchi"
,"Motati_Reddy":"Motati Reddy"
,"Mudaliar_Arcot":"Mudaliar Arcot"
,"Mudaliar":"Mudaliar"
,"Mudiraj":"Mudiraj"
,"Muthuraja":"Muthuraja"
,"Nadar":"Nadar"
,"Naicker":"Naicker"
,"Naidu":"Naidu"
,"Naik_Nayak_Nayaka":"Naik-Nayak-Nayaka"
,"Nair_Veluthedathu":"Nair Veluthedathu"
,"Nair_Vilakkithala":"Nair Vilakkithala"
,"Nair":"Nair"
,"Namasudra_Namosudra":"Namasudra-Namosudra"
,"Nambiar":"Nambiar"
,"Namboodiri":"Namboodiri"
,"Napit":"Napit"
,"Nath_Jogi":"Nath Jogi"
,"Nayee_Barber":"Nayee (Barber)"
,"Nepali":"Nepali"
,"Nhavi":"Nhavi"
,"Nonia":"Nonia"
,"OBC":"OBC"
,"Oswal":"Oswal"
,"Others":"Others"
,"Padmashali":"Padmashali"
,"Pal":"Pal"
,"Panchal":"Panchal"
,"Panchamsali":"Panchamsali"
,"Pandaram":"Pandaram"

,"Panicker":"Panicker"
,"Parkava_Kulam":"Parkava Kulam"
,"Pasi":"Pasi"
,"Patel_Dodia":"Patel Dodia"
,"Patel_Kadva":"Patel Kadva"
,"Patel_Leva":"Patel Leva"
,"Patel_Lodhi":"Patel Lodhi"
,"Patel":"Patel"
,"Patil":"Patil"
,"Patnaick":"Patnaick"
,"Patra":"Patra"
,"Perika":"Perika"
,"Pillai":"Pillai"
,"Prajapati":"Prajapati"
,"Raigar":"Raigar"
,"Raikwar":"Raikwar"
,"Rajaka":"Rajaka"
,"Rajbhar":"Rajbhar"
,"Rajbonshi":"Rajbonshi"
,"Rajpurohit":"Rajpurohit"
,"Rajput_Garhwali":"Rajput Garhwali"
,"Rajput_Kumaoni":"Rajput Kumaoni"
,"Rajput_Negi":"Rajput Negi"
,"Rajput_Rohella_Tank":"Rajput Rohella-Tank"
,"Rajput":"Rajput"
,"Ramdasia":"Ramdasia"
,"Ramgarhia":"Ramgarhia"
,"Ramoshi_Berad_Bedar":"Ramoshi-Berad-Bedar"
,"Ravidasia":"Ravidasia"
,"Rawat":"Rawat"
,"Reddy":"Reddy"
,"Sadgope":"Sadgope"
,"Saha":"Saha"
,"Sahu":"Sahu"
,"Saini":"Saini"
,"Saliya":"Saliya"
,"Scheduled_Caste":"Scheduled Caste"
,"Scheduled_Tribe":"Scheduled Tribe"
,"Senai_Thalaivar":"Senai Thalaivar"
,"Senguntha_Mudaliyar":"Senguntha Mudaliyar"
,"Settibalija":"Settibalija"
,"Shah":"Shah"
,"Shimpi":"Shimpi"
,"Sindhi_Amil":"Sindhi Amil"
,"Sindhi_Baibhand":"Sindhi Baibhand"
,"Sindhi_Bhanusali":"Sindhi Bhanusali"
,"Sindhi_Bhatia":"Sindhi Bhatia"
,"Sindhi_Chhapru":"Sindhi Chhapru"
,"Sindhi_Hydrabadi":"Sindhi Hydrabadi"
,"Sindhi_Larai":"Sindhi Larai"

,"Sindhi_Larkan":"Sindhi Larkan"
,"Sindhi_Larkana":"Sindhi Larkana"
,"Sindhi_Lohana":"Sindhi Lohana"
,"Sindhi_Rohiri":"Sindhi Rohiri"
,"Sindhi_Sahiti":"Sindhi Sahiti"
,"Sindhi_Sakkhar":"Sindhi Sakkhar"
,"Sindhi_Sehwani":"Sindhi Sehwani"
,"Sindhi_Shikarpuri":"Sindhi Shikarpuri"
,"Sindhi_Thatai":"Sindhi Thatai"
,"Sindhi":"Sindhi"
,"SKP":"SKP"
,"Somvanshi_Kayastha_Prabhu":"Somvanshi Kayastha Prabhu"
,"Somvanshi":"Somvanshi"
,"Sonar":"Sonar"
,"Soni":"Soni"
,"Sood":"Sood"
,"Sourashtra":"Sourashtra"
,"Sozhiya_Vellalar":"Sozhiya Vellalar"
,"Srisayani":"Srisayani"
,"SSK":"SSK"
,"Subarna_Banik":"Subarna Banik"
,"Sundhi":"Sundhi"
,"Sutar":"Sutar"
,"Swakula_sali":"Swakula sali"
,"Swarnkar":"Swarnkar"
,"Tamboli":"Tamboli"
,"Tanti":"Tanti"
,"Tantuway":"Tantuway"
,"Telaga":"Telaga"
,"Teli":"Teli"
,"Thakkar":"Thakkar"
,"Thakur":"Thakur"
,"Thevar_Mukkulathor":"Thevar-Mukkulathor"
,"Thigala":"Thigala"
,"Thiyya":"Thiyya"
,"Tili":"Tili"
,"Togata":"Togata"
,"Tonk_Kshatriya":"Tonk Kshatriya"
,"Tribe":"Tribe"
,"Turupu_Kapu":"Turupu Kapu"
,"Uppara":"Uppara"
,"Vaddera":"Vaddera"
,"Vaidiki_Velanadu":"Vaidiki Velanadu"
,"Vaish":"Vaish"
,"Vaishnav_Vanik":"Vaishnav Vanik"
,"Vaishnav":"Vaishnav"
,"Vaishnava":"Vaishnava"
,"Vaishya_Vani":"Vaishya Vani"
,"Vaishya":"Vaishya"
,"Valluvar":"Valluvar"


,"Valmiki":"Valmiki"
,"Vania":"Vania"
,"Vaniya":"Vaniya"
,"Vanjari":"Vanjari"
,"Vankar":"Vankar"
,"Vannar":"Vannar"
,"Vannia_Kula_Kshatriyar":"Vannia Kula Kshatriyar"
,"Vanniyar":"Vanniyar"
,"Varshney":"Varshney"
,"Veershaiva_Veera_Saivam":"Veershaiva-Veera Saivam"
,"Velama":"Velama"
,"Velan":"Velan"
,"Vellalar":"Vellalar"
,"Vettuva_Gounder":"Vettuva Gounder"
,"Vishwakarma":"Vishwakarma"
,"Vokkaliga":"Vokkaliga"
,"Vysya":"Vysya"
,"Yadav_Yadava":"Yadav-Yadava"

,"Other":"Other"

},

"Jain" :   {
"Digambar":"Digambar"
,"Shwetambar":"Shwetambar"
,"Other":"Other"
},

"Jewish" : {
"Jewish":"Jewish"
},

"Muslim" : {
"Shia":"Shia"
,"Sunni":"Sunni"
,"Other":"Other"
},

"Parsi" : {
"Parsi":"Parsi"
},

"Sikh" : {
"Arora":"Arora"
,"Bhatia":"Bhatia"
,"Gursikh":"Gursikh"
,"Jat":"Jat"
,"Kamboj":"Kamboj"
,"Kesadhari":"Kesadhari"
,"Khashap-Rajpoot":"Khashap-Rajpoot"
,"Khatri":"Khatri"
,"Labana":"Labana"
,"Mazhbi":"Mazhbi"
,"Rajput":"Rajput"
,"Ramdasia":"Ramdasia"
,"Ramgarhia":"Ramgarhia"
,"Saini":"Saini"
,"Tonk-Kshatriya":"Tonk-Kshatriya"
,"Other":"Other"
},

"Other" : {
"Other":"Other"
}

};



$scope.es.casteList = {

"Ad_Dharmi":"Ad Dharmi"
,"Adi_Andhra":"Adi Andhra"
,"Adi_Dravida":"Adi Dravida"
,"Adi_Karnataka":"Adi Karnataka"
,"Agamudayar":"Agamudayar"
,"Aggarwal":"Aggarwal"
,"Agri":"Agri"
,"Ahir":"Ahir"
,"Ahom":"Ahom"
,"Ambalavasi":"Ambalavasi"
,"Arora":"Arora"
,"Arunthathiyar":"Arunthathiyar"
,"Arya_Vysya":"Arya Vysya"
,"Badhai":"Badhai"
,"Baghel_Gaderiya":"Baghel-Gaderiya"
,"Baidya":"Baidya"
,"Baishnab":"Baishnab"
,"Baishya":"Baishya"
,"Balija_Naidu":"Balija Naidu"
,"Balija":"Balija"
,"Bania":"Bania"
,"Banik":"Banik"
,"Banjara":"Banjara"
,"Bari":"Bari"
,"Barujibi":"Barujibi"
,"Besta":"Besta"
,"Bhandari":"Bhandari"
,"Bhatia":"Bhatia"
,"Bhatraju":"Bhatraju"
,"Bhavsar":"Bhavsar"
,"Bhovi_Bhoi":"Bhovi-Bhoi"
,"Billava":"Billava"
,"Bisa_Agarwal":"Bisa Agarwal"
,"Bishnoi_Vishnoi":"Bishnoi-Vishnoi"
,"Boyer":"Boyer"
,"Brahmbatt":"Brahmbatt"
,"Brahmin_6000_Niyogi":"Brahmin 6000 Niyogi"
,"Brahmin_Anavil":"Brahmin Anavil"
,"Brahmin_Audichya":"Brahmin Audichya"
,"Brahmin_Bajkhedwal":"Brahmin Bajkhedwal"
,"Brahmin_Bardai":"Brahmin Bardai"
,"Brahmin_Barendra":"Brahmin Barendra"
,"Brahmin_Bhargava":"Brahmin Bhargava"
,"Brahmin_Bhatt":"Brahmin Bhatt"
,"Brahmin_Bhumihar":"Brahmin Bhumihar"
,"Brahmin_Brahacharanam":"Brahmin Brahacharanam"
,"Brahmin_BrahmBhatt":"Brahmin BrahmBhatt"
,"Brahmin_Brajastha_Mathil":"Brahmin Brajastha Mathil"
,"Brahmin_Dadhich":"Brahmin Dadhich"
,"Brahmin_Daivadnya":"Brahmin Daivadnya"

,"Brahmin_Deshastha":"Brahmin Deshastha"
,"Brahmin_Deshastha":"Brahmin Deshastha"
,"Brahmin_Devrukhe":"Brahmin Devrukhe"
,"Brahmin_Dhiman":"Brahmin Dhiman"
,"Brahmin_Dravida":"Brahmin Dravida"
,"Brahmin_Dunua":"Brahmin Dunua"
,"Brahmin_Embrandiri":"Brahmin Embrandiri"
,"Brahmin_Garhwali":"Brahmin Garhwali"
,"Brahmin_Gaud_Saraswat_GSB":"Brahmin Gaud Saraswat (GSB)"
,"Brahmin_Gaur":"Brahmin Gaur"
,"Brahmin_Goswami":"Brahmin Goswami"
,"Brahmin_Gujar_Gaur":"Brahmin Gujar Gaur"
,"Brahmin_Gurukkal":"Brahmin Gurukkal"
,"Brahmin_Halua":"Brahmin Halua"
,"Brahmin_Havyaka":"Brahmin Havyaka"
,"Brahmin_Hoysala":"Brahmin Hoysala"
,"Brahmin_Iyengar":"Brahmin Iyengar"
,"Brahmin_Iyer":"Brahmin Iyer"
,"Brahmin_Jangid":"Brahmin Jangid"
,"Brahmin_Jangra":"Brahmin Jangra"
,"Brahmin_Jhadua":"Brahmin Jhadua"
,"Brahmin_Jhijhotiya":"Brahmin Jhijhotiya"
,"Brahmin_Jogi":"Brahmin Jogi"
,"Brahmin_Jyotish":"Brahmin Jyotish"
,"Brahmin_Kanyakubj":"Brahmin Kanyakubj"
,"Brahmin_Karhade":"Brahmin Karhade"
,"Brahmin_Kashmiri_Pandit":"Brahmin Kashmiri Pandit"
,"Brahmin_Khadayat":"Brahmin Khadayat"
,"Brahmin_Khandelwal":"Brahmin Khandelwal"
,"Brahmin_Khedaval":"Brahmin Khedaval"
,"Brahmin_Koknastha":"Brahmin Koknastha"
,"Brahmin_Kota":"Brahmin Kota"
,"Brahmin_Kulin":"Brahmin Kulin"
,"Brahmin_Kumaoni":"Brahmin Kumaoni"
,"Brahmin_Madhwa":"Brahmin Madhwa"
,"Brahmin_Maithil":"Brahmin Maithil"
,"Brahmin_Malviya":"Brahmin Malviya"
,"Brahmin_Marwari":"Brahmin Marwari"
,"Brahmin_Mevada":"Brahmin Mevada"
,"Brahmin_Modh":"Brahmin Modh"
,"Brahmin_Mohyal":"Brahmin Mohyal"
,"Brahmin_Nagar":"Brahmin Nagar"
,"Brahmin_Namboodiri":"Brahmin Namboodiri"
,"Brahmin_Narmadiya":"Brahmin Narmadiya"
,"Brahmin_Paliwal":"Brahmin Paliwal"
,"Brahmin_Panda":"Brahmin Panda"
,"Brahmin_Pandit":"Brahmin Pandit"
,"Brahmin_Panicker":"Brahmin Panicker"
,"Brahmin_Pareek":"Brahmin Pareek"
,"Brahmin_Pushkarna":"Brahmin Pushkarna"

,"Brahmin_Rajgor":"Brahmin Rajgor"
,"Brahmin_Rarhi":"Brahmin Rarhi"
,"Brahmin_Rigvedi":"Brahmin Rigvedi"
,"Brahmin_Rudraj":"Brahmin Rudraj"
,"Brahmin_Sakaldwipi":"Brahmin Sakaldwipi"
,"Brahmin_Sanadya":"Brahmin Sanadya"
,"Brahmin_Sanketi":"Brahmin Sanketi"
,"Brahmin_Saraswat":"Brahmin Saraswat"
,"Brahmin_Sarotri":"Brahmin Sarotri"
,"Brahmin_Sarua":"Brahmin Sarua"
,"Brahmin_Saryuparin":"Brahmin Saryuparin"
,"Brahmin_Shivalli":"Brahmin Shivalli"
,"Brahmin_Shrimali":"Brahmin Shrimali"
,"Brahmin_Sikhwal":"Brahmin Sikhwal"
,"Brahmin_Smartha":"Brahmin Smartha"
,"Brahmin_Sri_Vishnava":"Brahmin Sri Vishnava"
,"Brahmin_Stanika":"Brahmin Stanika"
,"Brahmin_Tapodhan":"Brahmin Tapodhan"
,"Brahmin_Tyagi":"Brahmin Tyagi"
,"Brahmin_Vaidiki":"Brahmin Vaidiki"
,"Brahmin_Vaikhawas":"Brahmin Vaikhawas"
,"Brahmin_Vaishnav":"Brahmin Vaishnav"
,"Brahmin_Valam":"Brahmin Valam"
,"Brahmin_Velanadu":"Brahmin Velanadu"
,"Brahmin_Viswa":"Brahmin Viswa"
,"Brahmin_Vyas":"Brahmin Vyas"
,"Brahmin_Yajurvedi":"Brahmin Yajurvedi"
,"Brahmin_Zalora":"Brahmin Zalora"
,"Brahmin":"Brahmin"
,"Brahmo":"Brahmo"
,"Bunt_Shetty":"Bunt-Shetty"
,"Chamar":"Chamar"
,"Chambhar":"Chambhar"
,"Chandravanshi_Kahar":"Chandravanshi Kahar"
,"Chasa":"Chasa"
,"Chattada_Sri_Vaishnava":"Chattada Sri Vaishnava"
,"Chaudary":"Chaudary"
,"Chaurasia":"Chaurasia"
,"Chettiar":"Chettiar"
,"Chhetri":"Chhetri"
,"CKP":"CKP"
,"Coorgi":"Coorgi"
,"Deshastha_Maratha":"Deshastha Maratha"
,"Devadigas":"Devadigas"
,"Devang_Koshthi":"Devang Koshthi"
,"Devanga":"Devanga"
,"Devendra_Kula_Vellalar":"Devendra Kula Vellalar"
,"Dhangar":"Dhangar"
,"Dheevara":"Dheevara"
,"Dhoba":"Dhoba"

,"Dhobi":"Dhobi"
,"Dhor_Dhoar":"Dhor-Dhoar"
,"Dusadh":"Dusadh"
,"Edigas":"Edigas"
,"Ezhava":"Ezhava"
,"Ezhuthachan":"Ezhuthachan"
,"Gabit":"Gabit"
,"Gangai_Ganesh":"Gangai-Ganesh"
,"Ganiga":"Ganiga"
,"Garhwali":"Garhwali"
,"Gavali":"Gavali"
,"Gavara":"Gavara"
,"Ghumar":"Ghumar"
,"Goala":"Goala"
,"Goan":"Goan"
,"Gomantak_Maratha":"Gomantak Maratha"
,"Gond_Gondi_Raj_Gond":"Gond-Gondi-Raj Gond"
,"Gondhali":"Gondhali"
,"Goud":"Goud"
,"Gounder":"Gounder"
,"Gowda":"Gowda"
,"Gramani":"Gramani"
,"Gudia":"Gudia"
,"Gujjar":"Gujjar"
,"Gupta":"Gupta"
,"Gurav":"Gurav"
,"Halba_Koshti":"Halba Koshti"
,"Hegde":"Hegde"
,"Jaiswal":"Jaiswal"
,"Jangam":"Jangam"
,"Jat":"Jat"
,"Jatav":"Jatav"
,"Kadava_patel":"Kadava patel"
,"Kahar":"Kahar"
,"Kaibarta":"Kaibarta"
,"Kalal":"Kalal"
,"Kalanji":"Kalanji"
,"Kalar":"Kalar"
,"Kalinga_Vysya":"Kalinga Vysya"
,"Kalwar":"Kalwar"
,"Kamboj":"Kamboj"
,"Kamma":"Kamma"
,"Kansari":"Kansari"
,"Kapol":"Kapol"
,"Kapu_Munnuru":"Kapu Munnuru"
,"Kapu":"Kapu"
,"Karana":"Karana"
,"Karmakar":"Karmakar"
,"Karuneegar":"Karuneegar"
,"Kasar":"Kasar"


,"Kashyap":"Kashyap"
,"Kayastha":"Kayastha"
,"Khandayat":"Khandayat"
,"Khandelwal":"Khandelwal"
,"Kharvi":"Kharvi"
,"Kharwar":"Kharwar"
,"Khatik":"Khatik"
,"Khatri":"Khatri"
,"Koeri_Koiri":"Koeri-Koiri"
,"Kokanastha_Maratha":"Kokanastha Maratha"
,"Koli_Mahadev":"Koli Mahadev"
,"Koli":"Koli"
,"Kongu_Vellala_Gounder":"Kongu Vellala Gounder"
,"Konkani":"Konkani"
,"Kori":"Kori"
,"Koshti":"Koshti"
,"Kshatriya_Agnikula":"Kshatriya Agnikula"
,"Kshatriya":"Kshatriya"
,"Kudumbi":"Kudumbi"
,"Kulalar":"Kulalar"
,"Kulita":"Kulita"
,"Kumawat":"Kumawat"
,"Kumbhakar":"Kumbhakar"
,"Kumhar_Kumbhar":"Kumhar-Kumbhar"
,"Kummari":"Kummari"
,"Kunbi":"Kunbi"
,"Kurmi_kshatriya":"Kurmi kshatriya"
,"Kurmi":"Kurmi"
,"Kuruba":"Kuruba"
,"Kuruhina_shetty":"Kuruhina shetty"
,"Kurumbar":"Kurumbar"
,"Kushwaha":"Kushwaha"
,"Kutchi_Gurjar":"Kutchi Gurjar"
,"Kutchi":"Kutchi"
,"Lambadi":"Lambadi"
,"Leva_Patidar":"Leva Patidar"
,"Leva_Patil":"Leva Patil"
,"Lingayat":"Lingayat"
,"Lodhi_Rajput":"Lodhi Rajput"
,"Lohana":"Lohana"
,"Lohar":"Lohar"
,"Lubana":"Lubana"
,"Madiga":"Madiga"
,"Mahajan":"Mahajan"
,"Mahar":"Mahar"
,"Maheshwari":"Maheshwari"
,"Mahindra":"Mahindra"
,"Mahisya":"Mahisya"
,"Majabi_Mazhbi":"Majabi-Mazhbi"
,"Mala":"Mala"

,"Mali":"Mali"
,"Mallah":"Mallah"
,"Manipuri":"Manipuri"
,"Manjhi":"Manjhi"
,"Mapila":"Mapila"
,"Maratha":"Maratha"
,"96_Kuli_Maratha":"96 Kuli Maratha"
,"Maravar":"Maravar"
,"Maruthuvar":"Maruthuvar"
,"Marwari":"Marwari"
,"Matang":"Matang"
,"Mathur":"Mathur"
,"Maurya":"Maurya"
,"Meena":"Meena"
,"Meenavar":"Meenavar"
,"Mehra":"Mehra"
,"Menon":"Menon"
,"Meru_darji":"Meru darji"
,"Meru":"Meru"
,"Modak":"Modak"
,"Mogaveera":"Mogaveera"
,"Monchi":"Monchi"
,"Motati_Reddy":"Motati Reddy"
,"Mudaliar_Arcot":"Mudaliar Arcot"
,"Mudaliar":"Mudaliar"
,"Mudiraj":"Mudiraj"
,"Muthuraja":"Muthuraja"
,"Nadar":"Nadar"
,"Naicker":"Naicker"
,"Naidu":"Naidu"
,"Naik_Nayak_Nayaka":"Naik-Nayak-Nayaka"
,"Nair_Veluthedathu":"Nair Veluthedathu"
,"Nair_Vilakkithala":"Nair Vilakkithala"
,"Nair":"Nair"
,"Namasudra_Namosudra":"Namasudra-Namosudra"
,"Nambiar":"Nambiar"
,"Namboodiri":"Namboodiri"
,"Napit":"Napit"
,"Nath_Jogi":"Nath Jogi"
,"Nayee_Barber":"Nayee (Barber)"
,"Nepali":"Nepali"
,"Nhavi":"Nhavi"
,"Nonia":"Nonia"
,"OBC":"OBC"
,"Oswal":"Oswal"
,"Others":"Others"
,"Padmashali":"Padmashali"
,"Pal":"Pal"
,"Panchal":"Panchal"
,"Panchamsali":"Panchamsali"
,"Pandaram":"Pandaram"

,"Panicker":"Panicker"
,"Parkava_Kulam":"Parkava Kulam"
,"Pasi":"Pasi"
,"Patel_Dodia":"Patel Dodia"
,"Patel_Kadva":"Patel Kadva"
,"Patel_Leva":"Patel Leva"
,"Patel_Lodhi":"Patel Lodhi"
,"Patel":"Patel"
,"Patil":"Patil"
,"Patnaick":"Patnaick"
,"Patra":"Patra"
,"Perika":"Perika"
,"Pillai":"Pillai"
,"Prajapati":"Prajapati"
,"Raigar":"Raigar"
,"Raikwar":"Raikwar"
,"Rajaka":"Rajaka"
,"Rajbhar":"Rajbhar"
,"Rajbonshi":"Rajbonshi"
,"Rajpurohit":"Rajpurohit"
,"Rajput_Garhwali":"Rajput Garhwali"
,"Rajput_Kumaoni":"Rajput Kumaoni"
,"Rajput_Negi":"Rajput Negi"
,"Rajput_Rohella_Tank":"Rajput Rohella-Tank"
,"Rajput":"Rajput"
,"Ramdasia":"Ramdasia"
,"Ramgarhia":"Ramgarhia"
,"Ramoshi_Berad_Bedar":"Ramoshi-Berad-Bedar"
,"Ravidasia":"Ravidasia"
,"Rawat":"Rawat"
,"Reddy":"Reddy"
,"Sadgope":"Sadgope"
,"Saha":"Saha"
,"Sahu":"Sahu"
,"Saini":"Saini"
,"Saliya":"Saliya"
,"Scheduled_Caste":"Scheduled Caste"
,"Scheduled_Tribe":"Scheduled Tribe"
,"Senai_Thalaivar":"Senai Thalaivar"
,"Senguntha_Mudaliyar":"Senguntha Mudaliyar"
,"Settibalija":"Settibalija"
,"Shah":"Shah"
,"Shimpi":"Shimpi"
,"Sindhi_Amil":"Sindhi Amil"
,"Sindhi_Baibhand":"Sindhi Baibhand"
,"Sindhi_Bhanusali":"Sindhi Bhanusali"
,"Sindhi_Bhatia":"Sindhi Bhatia"
,"Sindhi_Chhapru":"Sindhi Chhapru"
,"Sindhi_Hydrabadi":"Sindhi Hydrabadi"
,"Sindhi_Larai":"Sindhi Larai"

,"Sindhi_Larkan":"Sindhi Larkan"
,"Sindhi_Larkana":"Sindhi Larkana"
,"Sindhi_Lohana":"Sindhi Lohana"
,"Sindhi_Rohiri":"Sindhi Rohiri"
,"Sindhi_Sahiti":"Sindhi Sahiti"
,"Sindhi_Sakkhar":"Sindhi Sakkhar"
,"Sindhi_Sehwani":"Sindhi Sehwani"
,"Sindhi_Shikarpuri":"Sindhi Shikarpuri"
,"Sindhi_Thatai":"Sindhi Thatai"
,"Sindhi":"Sindhi"
,"SKP":"SKP"
,"Somvanshi_Kayastha_Prabhu":"Somvanshi Kayastha Prabhu"
,"Somvanshi":"Somvanshi"
,"Sonar":"Sonar"
,"Soni":"Soni"
,"Sood":"Sood"
,"Sourashtra":"Sourashtra"
,"Sozhiya_Vellalar":"Sozhiya Vellalar"
,"Srisayani":"Srisayani"
,"SSK":"SSK"
,"Subarna_Banik":"Subarna Banik"
,"Sundhi":"Sundhi"
,"Sutar":"Sutar"
,"Swakula_sali":"Swakula sali"
,"Swarnkar":"Swarnkar"
,"Tamboli":"Tamboli"
,"Tanti":"Tanti"
,"Tantuway":"Tantuway"
,"Telaga":"Telaga"
,"Teli":"Teli"
,"Thakkar":"Thakkar"
,"Thakur":"Thakur"
,"Thevar_Mukkulathor":"Thevar-Mukkulathor"
,"Thigala":"Thigala"
,"Thiyya":"Thiyya"
,"Tili":"Tili"
,"Togata":"Togata"
,"Tonk_Kshatriya":"Tonk Kshatriya"
,"Tribe":"Tribe"
,"Turupu_Kapu":"Turupu Kapu"
,"Uppara":"Uppara"
,"Vaddera":"Vaddera"
,"Vaidiki_Velanadu":"Vaidiki Velanadu"
,"Vaish":"Vaish"
,"Vaishnav_Vanik":"Vaishnav Vanik"
,"Vaishnav":"Vaishnav"
,"Vaishnava":"Vaishnava"
,"Vaishya_Vani":"Vaishya Vani"
,"Vaishya":"Vaishya"
,"Valluvar":"Valluvar"


,"Valmiki":"Valmiki"
,"Vania":"Vania"
,"Vaniya":"Vaniya"
,"Vanjari":"Vanjari"
,"Vankar":"Vankar"
,"Vannar":"Vannar"
,"Vannia_Kula_Kshatriyar":"Vannia Kula Kshatriyar"
,"Vanniyar":"Vanniyar"
,"Varshney":"Varshney"
,"Veershaiva_Veera_Saivam":"Veershaiva-Veera Saivam"
,"Velama":"Velama"
,"Velan":"Velan"
,"Vellalar":"Vellalar"
,"Vettuva_Gounder":"Vettuva Gounder"
,"Vishwakarma":"Vishwakarma"
,"Vokkaliga":"Vokkaliga"
,"Vysya":"Vysya"
,"Yadav_Yadava":"Yadav-Yadava"

, "Digambar":"Jain-Digambar"
,"Shwetambar":"Jain-Shwetambar"


,"Bahai":"Bahai"


,"Buddhist":"Buddhist"

,"Anglo_Indian":"Anglo Indian"
,"Born_Again":"Born Again"
,"Brethren":"Brethren"
,"Catholic":"Catholic"
,"Catholic_Knanaya":"Catholic - Knanaya"
,"Catholic_Latin":"Catholic - Latin"
,"Catholic_Malankara":"Catholic - Malankara"
,"Catholic_Roman":"Catholic - Roman"
,"Catholic_Syrian":"Catholic - Syrian"
,"Chaldean":"Chaldean"
,"CMS":"CMS"
,"CSI":"CSI"
,"Evangelical":"Evangelical"
,"Indian_Orthodox":"Indian Orthodox"
,"Jacobite":"Jacobite"
,"Jacobite_Knanaya":"Jacobite - Knanaya"
,"Jacobite_Syrian":"Jacobite - Syrian"
,"Kharvi":"Kharvi"
,"Knanaya":"Knanaya"
,"Mangalorean":"Mangalorean"
,"Manglorean":"Manglorean"
,"Marthomite":"Marthomite"
,"Nadar":"Nadar"
,"Pentecost":"Pentecost"
,"Protestant":"Protestant"
,"Syrian":"Syrian"
,"Syrian_Malabar":"Syrian - Malabar"
,"Syrian_Orthodox":"Syrian - Orthodox"
,"Syro_Malabar":"Syro - Malabar"

,"Jewish":"Jewish"

,"Shia":"Shia"
,"Sunni":"Sunni"

,"Parsi":"Parsi"

,"Arora":"Arora"
,"Bhatia":"Bhatia"
,"Gursikh":"Gursikh"
,"Jat":"Jat"
,"Kamboj":"Kamboj"
,"Kesadhari":"Kesadhari"
,"Khashap-Rajpoot":"Khashap-Rajpoot"
,"Khatri":"Khatri"
,"Labana":"Labana"
,"Mazhbi":"Mazhbi"
,"Rajput":"Rajput"
,"Ramdasia":"Ramdasia"
,"Ramgarhia":"Ramgarhia"
,"Saini":"Saini"
,"Tonk-Kshatriya":"Tonk-Kshatriya"
,"Other":"Other"

}

};
;
var initDropDownsGeo = function($scope, $http, $modal, $log, $sce, $cookies, $window){

$scope.es.defaultCountry = "India";

$scope.es.countryList = {
"India":"India"
,"USA":"United States of America"
,"UAE":"United Arab Emirates"
,"United_Kingdom":"United Kingdom"
,"Afghanistan":"Afghanistan"
,"Aland_Islands":"Aland Islands"
,"Albania":"Albania"
,"Algeria":"Algeria"
,"American_Samoa":"American Samoa"
,"Andorra":"Andorra"
,"Angola":"Angola"
,"Anguilla":"Anguilla"
,"Antigua_and_Barbuda":"Antigua and Barbuda"
,"Argentina":"Argentina"
,"Armenia":"Armenia"
,"Aruba":"Aruba"
,"Ascension":"Ascension"
,"Australia":"Australia"
,"Australian_Antarctic_Territory":"Australian Antarctic Territory"
,"Australian_External_Territories":"Australian External Territories"
,"Austria":"Austria"
,"Azerbaijan":"Azerbaijan"
,"Bahamas":"Bahamas"
,"Bahrain":"Bahrain"
,"Bangladesh":"Bangladesh"
,"Barbados":"Barbados"
,"Barbuda":"Barbuda"
,"Belarus":"Belarus"
,"Belgium":"Belgium"
,"Belize":"Belize"
,"Benin":"Benin"
,"Bermuda":"Bermuda"
,"Bhutan":"Bhutan"
,"Bolivia":"Bolivia"
,"Bonaire":"Bonaire"
,"Bosnia_and_Herzegovina":"Bosnia and Herzegovina"
,"Botswana":"Botswana"
,"Brazil":"Brazil"
,"British_Indian_Ocean_Territory":"British Indian Ocean Territory"
,"British_Virgin_Islands":"British Virgin Islands"
,"Brunei_Darussalam":"Brunei Darussalam"
,"Bulgaria":"Bulgaria"
,"Burkina_Faso":"Burkina Faso"
,"Burundi":"Burundi"
,"Cambodia":"Cambodia"
,"Cameroon":"Cameroon"
,"Canada":"Canada"
,"Cape_Verde":"Cape Verde"
,"Caribbean_Netherlands":"Caribbean Netherlands"
,"Cayman_Islands":"Cayman Islands"
,"Central_African_Republic":"Central African Republic"
,"Chad":"Chad"
,"Chatham_Island_New_Zealand":"Chatham Island, New Zealand"
,"Chile":"Chile"
,"China":"China"
,"Christmas_Island":"Christmas Island"
,"Cocos_Keeling_Islands":"Cocos (Keeling) Islands"
,"Colombia":"Colombia"
,"Comoros":"Comoros"
,"Congo":"Congo"
,"Congo_Democratic_Republic_of_the_Zaire":"Congo, Democratic Republic of the (Zaire)"
,"Cook_Islands":"Cook Islands"
,"Costa_Rica":"Costa Rica"
,"Croatia":"Croatia"
,"Cuba":"Cuba"
,"Curaçao":"Curaçao"
,"Cyprus":"Cyprus"
,"Czech_Republic":"Czech Republic"
,"Denmark":"Denmark"
,"Diego_Garcia":"Diego Garcia"
,"Djibouti":"Djibouti"
,"Dominica":"Dominica"
,"Dominican_Republic":"Dominican Republic"
,"East_Timor":"East Timor"
,"Easter_Island":"Easter Island"
,"Ecuador":"Ecuador"
,"Egypt":"Egypt"
,"El_Salvador":"El Salvador"
,"Ellipso_Mobile_Satellite_service":"Ellipso (Mobile Satellite service)"
,"EMSAT_Mobile_Satellite_service":"EMSAT (Mobile Satellite service)"
,"Equatorial_Guinea":"Equatorial Guinea"
,"Eritrea":"Eritrea"
,"Estonia":"Estonia"
,"eSwatini":"eSwatini"
,"Ethiopia":"Ethiopia"
,"Falkland_Islands":"Falkland Islands"
,"Faroe_Islands":"Faroe Islands"
,"Fiji":"Fiji"
,"Finland":"Finland"
,"France":"France"
,"French_Antilles":"French Antilles"
,"French_Guiana":"French Guiana"
,"French_Polynesia":"French Polynesia"
,"Gabon":"Gabon"
,"Gambia":"Gambia"
,"Georgia":"Georgia"
,"Germany":"Germany"
,"Ghana":"Ghana"
,"Gibraltar":"Gibraltar"
,"Global_Mobile_Satellite_System_GMSS":"Global Mobile Satellite System (GMSS)"
,"Globalstar_Mobile_Satellite_Service":"Globalstar (Mobile Satellite Service)"
,"Greece":"Greece"
,"Greenland":"Greenland"
,"Grenada":"Grenada"
,"Guadeloupe":"Guadeloupe"
,"Guam":"Guam"
,"Guatemala":"Guatemala"
,"Guernsey":"Guernsey"
,"Guinea":"Guinea"
,"Guinea-Bissau":"Guinea-Bissau"
,"Guyana":"Guyana"
,"Haiti":"Haiti"
,"Honduras":"Honduras"
,"Hong_Kong":"Hong Kong"
,"Hungary":"Hungary"
,"Iceland":"Iceland"
,"ICO_Global_Mobile_Satellite_Service":"ICO Global (Mobile Satellite Service)"
,"Indonesia":"Indonesia"
,"Inmarsat_SNAC":"Inmarsat SNAC"
,"International_Freephone_Service_UIFN":"International Freephone Service (UIFN)"
,"International_Networks":"International Networks"
,"International_Premium_Rate_Service":"International Premium Rate Service"
,"International_Shared_Cost_Service_ISCS":"International Shared Cost Service (ISCS)"
,"Iran":"Iran"
,"Iraq":"Iraq"
,"Ireland":"Ireland"
,"Iridium_Mobile_Satellite_service":"Iridium (Mobile Satellite service)"
,"Isle_of_Man":"Isle of Man"
,"Israel":"Israel"
,"Italy":"Italy"
,"Ivory_Coast":"Ivory Coast"
,"Jamaica":"Jamaica"
,"Jan_Mayen":"Jan Mayen"
,"Japan":"Japan"
,"Jersey":"Jersey"
,"Jordan":"Jordan"
,"Kazakhstan":"Kazakhstan"
,"Kenya":"Kenya"
,"Kiribati":"Kiribati"
,"Korea_North":"Korea, North"
,"Korea_South":"Korea, South"
,"Kosovo":"Kosovo"
,"Kuwait":"Kuwait"
,"Kyrgyzstan":"Kyrgyzstan"
,"Laos":"Laos"
,"Latvia":"Latvia"
,"Lebanon":"Lebanon"
,"Lesotho":"Lesotho"
,"Liberia":"Liberia"
,"Libya":"Libya"
,"Liechtenstein":"Liechtenstein"
,"Lithuania":"Lithuania"
,"Luxembourg":"Luxembourg"
,"Macau":"Macau"
,"Madagascar":"Madagascar"
,"Malawi":"Malawi"
,"Malaysia":"Malaysia"
,"Maldives":"Maldives"
,"Mali":"Mali"
,"Malta":"Malta"
,"Marshall_Islands":"Marshall Islands"
,"Martinique":"Martinique"
,"Mauritania":"Mauritania"
,"Mauritius":"Mauritius"
,"Mayotte":"Mayotte"
,"Mexico":"Mexico"
,"Micronesia_Federated_States_of":"Micronesia, Federated States of"
,"Midway_Island_USA":"Midway Island, USA"
,"Moldova":"Moldova"
,"Monaco":"Monaco"
,"Mongolia":"Mongolia"
,"Montenegro":"Montenegro"
,"Montserrat":"Montserrat"
,"Morocco":"Morocco"
,"Mozambique":"Mozambique"
,"Myanmar":"Myanmar"
,"Nagorno-Karabakh":"Nagorno-Karabakh"
,"Namibia":"Namibia"
,"Nauru":"Nauru"
,"Nepal":"Nepal"
,"Netherlands":"Netherlands"
,"Nevis":"Nevis"
,"New_Caledonia":"New Caledonia"
,"New_Zealand":"New Zealand"
,"Nicaragua":"Nicaragua"
,"Niger":"Niger"
,"Nigeria":"Nigeria"
,"Niue":"Niue"
,"Norfolk_Island":"Norfolk Island"
,"North_Macedonia":"North Macedonia"
,"Northern_Cyprus":"Northern Cyprus"
,"Northern_Ireland":"Northern Ireland"
,"Northern_Mariana_Islands":"Northern Mariana Islands"
,"Norway":"Norway"
,"Oman":"Oman"
,"Pakistan":"Pakistan"
,"Palau":"Palau"
,"Palestine_State_of":"Palestine, State of"
,"Panama":"Panama"
,"Papua_New_Guinea":"Papua New Guinea"
,"Paraguay":"Paraguay"
,"Peru":"Peru"
,"Philippines":"Philippines"
,"Pitcairn_Islands":"Pitcairn Islands"
,"Poland":"Poland"
,"Portugal":"Portugal"
,"Puerto_Rico":"Puerto Rico"
,"Qatar":"Qatar"
,"Romania":"Romania"
,"Russia":"Russia"
,"Rwanda":"Rwanda"
,"Réunion":"Réunion"
,"Saba":"Saba"
,"Saint_Barthélemy":"Saint Barthélemy"
,"Saint_Helena":"Saint Helena"
,"Saint_Kitts_and_Nevis":"Saint Kitts and Nevis"
,"Saint_Lucia":"Saint Lucia"
,"Saint_Martin_France":"Saint Martin (France)"
,"Saint_Pierre_and_Miquelon":"Saint Pierre and Miquelon"
,"Saint_Vincent_and_the_Grenadines":"Saint Vincent and the Grenadines"
,"Samoa":"Samoa"
,"San_Marino":"San Marino"
,"Saudi_Arabia":"Saudi Arabia"
,"Senegal":"Senegal"
,"Serbia":"Serbia"
,"Seychelles":"Seychelles"
,"Sierra_Leone":"Sierra Leone"
,"Singapore":"Singapore"
,"Sint_Eustatius":"Sint Eustatius"
,"Sint_Maarten_Netherlands":"Sint Maarten (Netherlands)"
,"Slovakia":"Slovakia"
,"Slovenia":"Slovenia"
,"Solomon_Islands":"Solomon Islands"
,"Somalia":"Somalia"
,"South_Africa":"South Africa"
,"South_Georgia_and_the_South_Sandwich_Islands":"South Georgia and the South Sandwich Islands"
,"South_Ossetia":"South Ossetia"
,"South_Sudan":"South Sudan"
,"Spain":"Spain"
,"Sri_Lanka":"Sri Lanka"
,"Sudan":"Sudan"
,"Suriname":"Suriname"
,"Svalbard":"Svalbard"
,"Sweden":"Sweden"
,"Switzerland":"Switzerland"
,"Syria":"Syria"
,"São_Tomé_and_Príncipe":"São Tomé and Príncipe"
,"Taiwan":"Taiwan"
,"Tajikistan":"Tajikistan"
,"Tanzania":"Tanzania"
,"Telecommunications_for_Disaster_Relief_by_OCHA":"Telecommunications for Disaster Relief by OCHA"
,"Thailand":"Thailand"
,"Thuraya_Mobile_Satellite_service":"Thuraya (Mobile Satellite service)"
,"Togo":"Togo"
,"Tokelau":"Tokelau"
,"Tonga":"Tonga"
,"Transnistria":"Transnistria"
,"Trinidad_and_Tobago":"Trinidad and Tobago"
,"Tristan_da_Cunha":"Tristan da Cunha"
,"Tunisia":"Tunisia"
,"Turkey":"Turkey"
,"Turkmenistan":"Turkmenistan"
,"Turks_and_Caicos_Islands":"Turks and Caicos Islands"
,"Tuvalu":"Tuvalu"
,"Uganda":"Uganda"
,"Ukraine":"Ukraine"
,"Universal_Personal_Telecommunications_UPT":"Universal Personal Telecommunications (UPT)"
,"Uruguay":"Uruguay"
,"US_Virgin_Islands":"US Virgin Islands"
,"Uzbekistan":"Uzbekistan"
,"Vanuatu":"Vanuatu"
,"Vatican_City_State_Holy_See":"Vatican City State (Holy See)"
,"Venezuela":"Venezuela"
,"Vietnam":"Vietnam"
,"Wake_Island_USA":"Wake Island, USA"
,"Wallis_and_Futuna":"Wallis and Futuna"
,"Yemen":"Yemen"
,"Zambia":"Zambia"
,"Zanzibar":"Zanzibar"
,"Zimbabwe":"Zimbabwe"
,"Other":"Other"

};

$scope.es.stateList = {
 "Maharashtra":"Maharashtra"
,"Andaman_and_Nicobar_Islands":"Andaman and Nicobar Islands"
,"Andhra_Pradesh":"Andhra Pradesh"
,"Arunachal_Pradesh":"Arunachal Pradesh"
,"Assam":"Assam"
,"Bihar":"Bihar"
,"Chandigarh":"Chandigarh"
,"Chhattisgarh":"Chhattisgarh"
,"Dadra_and_Nagar_Haveli":"Dadra and Nagar Haveli"
,"Daman_and_Diu":"Daman and Diu"
,"Delhi":"Delhi"
,"Goa":"Goa"
,"Gujarat":"Gujarat"
,"Haryana":"Haryana"
,"Himachal_Pradesh":"Himachal Pradesh"
,"Jammu_and_Kashmir":"Jammu and Kashmir"
,"Jharkhand":"Jharkhand"
,"Karnataka":"Karnataka"
,"Kerala":"Kerala"
,"Lakshadweep":"Lakshadweep"
,"Madhya_Pradesh":"Madhya Pradesh"
,"Manipur":"Manipur"
,"Meghalaya":"Meghalaya"
,"Mizoram":"Mizoram"
,"Nagaland":"Nagaland"
,"Odisha":"Odisha"
,"Puducherry":"Puducherry"
,"Punjab":"Punjab"
,"Rajasthan":"Rajasthan"
,"Sikkim":"Sikkim"
,"Tamil_Nadu":"Tamil Nadu"
,"Telangana":"Telangana"
,"Tripura":"Tripura"
,"Uttar_Pradesh":"Uttar Pradesh"
,"Uttarakhand":"Uttarakhand"
,"West_Bengal":"West Bengal"
,"Outside_India":"Outside India"
,"Other":"Other"
};


};
;
var initDropDownsHuman = function($scope, $http, $modal, $log, $sce, $cookies, $window){

$scope.es.genderList =  {
"Female":"Bride / Female"
,"Male":"Groom / Male"
};

$scope.es.shareContactsWithList =  {
 "ANYONE":"ANYONE - Any registered can view my contact details"
,"EXPECTED":"EXPECTED - Only members which satisfy my expectations can view my contact details"
,"ACCEPTED":"ACCEPTED - Only members whose request I accept can view my contact details"
};

$scope.es.allowRequestFromList =  {
 "ANYONE":"ANYONE - Any registered member can send me a request"
,"EXPECTED":"EXPECTED - Only members which satisfy my expectations can send me a request"
};

$scope.es.bodyTypeList = {
 "Slim":"Slim"
,"Athletic":"Athletic"
,"Average":"Average"
,"Healthy":"Healthy"
,"Heavy":"Heavy"
};

$scope.es.skinColorList = {
 "Very_Fair":"Very Fair"
,"Fair":"Fair"
,"Brown":"Brown"
,"Dark_Brown":"Dark Brown"
,"Black":"Black"
};

$scope.es.maritalStatusList = {
"Never_Married":"Never Married"
,"Awaiting_Divorce":"Awaiting Divorce"
,"Divorced":"Divorced"
,"Widow":"Widow"
,"Other":"Other"
};

$scope.es.physicalStatusList = {
"Not_Challenged":"Not Challenged"
,"Physically_Challenged":"Physically Challenged"
,"Mentally_Challenged":"Mentally Challenged"
,"Physically_Mentally Challenged":"Physically Mentally Challenged"
};

$scope.es.manglikStatusList = {
"No":"No"
,"Souwmya":"Souwmya"
,"Yes":"Yes"
};

$scope.es.kundaliNadiList = {
"Adya":"Adya"
,"Madhya":"Madhya"
,"Antya":"Antya"
};

$scope.es.kundaliGanList =  {
"Dev":"Dev"
,"Manushya":"Manushya"
,"Rakshas":"Rakshas"
};


$scope.es.kundaliCharanList = {
"01":"01","02":"02","03":"03","04":"04"
};

$scope.es.siblingsList = {
"00":"00",
"01":"01","02":"02","03":"03","04":"04","05":"05","06":"06"
};


$scope.es.bloodGroupList = {
"1": "Don't Know"
,"A+":"A+"
,"A-":"A-"
,"AB+":"AB+"
,"AB-":"AB-"
,"B+":"B+"
,"B-":"B-"
,"OP":"O+"
,"O-":"O-"
};

$scope.es.heightCompleteList = {

"301":"03'01''" ,
"302":"03'02''" ,
"303":"03'03''" ,
"304":"03'04''" ,
"305":"03'05''" ,
"306":"03'06''" ,
"307":"03'07''" ,
"308":"03'08''" ,
"309":"03'09''" ,
"310":"03'10''" ,
"311":"03'11''" ,

"400":"04'00''" ,
"401":"04'01''" ,
"402":"04'02''" ,
"403":"04'03''" ,
"404":"04'04''" ,
"405":"04'05''" ,
"406":"04'06''" ,
"407":"04'07''" ,
"408":"04'08''" ,
"409":"04'09''" ,
"410":"04'10''" ,
"411":"04'11''" ,

"500":"05'00''" ,
"501":"05'01''" ,
"502":"05'02''" ,
"503":"05'03''" ,
"504":"05'04''" ,
"505":"05'05''" ,
"506":"05'06''" ,
"507":"05'07''" ,
"508":"05'08''" ,
"509":"05'09''" ,
"510":"05'10''" ,
"511":"05'11''" ,

"600":"06'00''" ,
"601":"06'01''" ,
"602":"06'02''" ,
"603":"06'03''" ,
"604":"06'04''" ,
"605":"06'05''" ,
"606":"06'06''" ,
"607":"06'07''" ,
"608":"06'08''" ,
"609":"06'09''" ,
"610":"06'10''" ,
"611":"06'11''" ,

"700":"07'00''" ,
"701":"07'01''" ,
"702":"07'02''" ,
"703":"07'03''" ,
"704":"07'04''" ,
"705":"07'05''" ,
"706":"07'06''" ,
"707":"07'07''" ,
"708":"07'08''" ,
"709":"07'09''" ,
"710":"07'10''" ,
"711":"07'11''" ,


};

$scope.es.professionalTypeList = {
"1" : "No Business and No Job"
,"2" : "Farmer"
,"Student":"Student"
,"Accountant":"Accountant"
,"AirHostess":"AirHostess"
,"Businessman":"Businessman"
,"Defence":"Defence"
,"Doctor":"Doctor"
,"Engineer":"Engineer"
,"Finance":"Finance"
,"Government":"Government"
,"Hospitality":"Hospitality"
,"IAS":"IAS"
,"Lawyer":"Lawyer"
,"Navy":"Navy"
,"NetworkSecurity":"NetworkSecurity"
,"Nurse":"Nurse"
,"Pilot":"Pilot"
,"Police":"Police"
,"Psychologist":"Psychologist"
,"SocialServices":"SocialServices"
,"Teacher":"Teacher"
,"Other":"Other"
};

$scope.es.motherTongueList = {
 "Marathi":"Marathi"
,"Assamese":"Assamese"
,"Bengali":"Bengali"
,"Bihari":"Bihari"
,"Gujarati":"Gujarati"
,"Haryanvi":"Haryanvi"
,"Himachali":"Himachali"
,"Hindi":"Hindi"
,"Kannada":"Kannada"
,"Kashmiri":"Kashmiri"
,"Konkani":"Konkani"
,"Malayalam":"Malayalam"
,"Nepali":"Nepali"
,"Oriya":"Oriya"
,"Punjabi":"Punjabi"
,"Rajasthani":"Rajasthani"
,"Sikkim":"Sikkim"
,"Tamil":"Tamil"
,"Telugu":"Telugu"
,"Tulu":"Tulu"
,"Other":"Other"
};

$scope.es.degreeTypeList = {
"Diploma":"Diploma"
,"Graduate":"Graduate - eg. BA/BCom/BSc"
,"Engineer":"Engineer - eg. BE/BTech"
,"Engineer-PG":"Engineer-PG - eg. MTech/MCA/MS"
,"Doctor":"Doctor - eg. MBBS/BDS/BAMS/BHMS"
,"Doctor-PG":"Doctor-PG - eg. MDS/MS/MD"
,"Finance":"Finance - eg. BBA/CA"
,"Finance-PG":"Finance-PG - eg. MBA in Finance"
,"PostGraduate":"PostGraduate - eg. MBA in any field"
,"Phd":"PHD"
,"Other":"Other - eg. SSC,HSC or any other Degree"
};

$scope.es.degreeTypeMap = {
"Diploma":"Diploma"
,"Graduate":"Graduate"
,"Engineer":"Engineer"
,"Engineer-PG":"Engineer-PG"
,"Doctor":"Doctor"
,"Doctor-PG":"Doctor-PG"
,"Finance":"Finance"
,"Finance-PG":"Finance-PG"
,"PostGraduate":"PostGraduate"
,"Phd":"PHD"
,"Other":"Other"
};

$scope.es.dietList = {
"Vegetarian":"Vegetarian"
,"Non-Vegetarian":"Non-Vegetarian"
,"Jain":"Jain"
,"Eggetarian":"Eggetarian"
,"Vegan":"Vegan"
,"Other":"Other"
};


$scope.es.smokingList = {
"Yes":"Yes"
,"No":"No"
,"Occasionally":"Occasionally"
};

$scope.es.drinkingList = {
"Yes":"Yes"
,"No":"No"
,"Occasionally":"Occasionally"
};

$scope.es.marketingRefList = {
"Google":"Google"
,"Facebook":"Facebook"
,"Newspaper":"Newspaper"
,"Relative":"Relative"
,"Other":"Other"
};


};
;
var initDropDownsTime = function($scope, $http, $modal, $log, $sce, $cookies, $window){

$scope.es.dayList = {
 "01":"01","02":"02","03":"03","04":"04","05":"05","06":"06","07":"07","08":"08","09":"09","10":"10"
,"11":"11","12":"12","13":"13","14":"14","15":"15","16":"16","17":"17","18":"18","19":"19","20":"20"
,"21":"21","22":"22","23":"23","24":"24","25":"25","26":"26","27":"27","28":"28","29":"29","30":"30"
,"31":"31"
};

$scope.es.monthList = {
 "01":"January","02":"February","03":"March","04":"April","05":"May","06":"June","07":"July","08":"August","09":"September","10":"October"
,"11":"November","12":"December"
};

$scope.es.yearList = {
 "2020" : "2020", "2019" : "2019", "2018" : "2018", "2017" : "2017", "2016" : "2016", "2015" : "2015", "2014" : "2014", "2013" : "2013", "2012" : "2012", "2011" : "2011"
,"2010" : "2010", "2009" : "2009", "2008" : "2008", "2007" : "2007", "2006" : "2006", "2005" : "2005", "2004" : "2004", "2003" : "2003", "2002" : "2002", "2001" : "2001"
,"2000" : "2000", "1999" : "1999", "1998" : "1998", "1997" : "1997", "1996" : "1996", "1995" : "1995", "1994" : "1994", "1993" : "1993", "1992" : "1992", "1991" : "1991"
,"1990" : "1990", "1989" : "1989", "1988" : "1988", "1987" : "1987", "1986" : "1986", "1985" : "1985", "1984" : "1984", "1983" : "1983", "1982" : "1982", "1981" : "1981"
,"1980" : "1980", "1979" : "1979", "1978" : "1978", "1977" : "1977", "1976" : "1976", "1975" : "1975", "1974" : "1974", "1973" : "1973", "1972" : "1972", "1971" : "1971"
,"1970" : "1970", "1969" : "1969", "1968" : "1968", "1967" : "1967", "1966" : "1966", "1965" : "1965", "1964" : "1964", "1963" : "1963", "1962" : "1962", "1961" : "1961"
,"1960" : "1960", "1959" : "1959", "1958" : "1958", "1957" : "1957", "1956" : "1956", "1955" : "1955", "1954" : "1954", "1953" : "1953", "1952" : "1952", "1951" : "1951"
,"1950" : "1950", "1949" : "1949", "1948" : "1948", "1947" : "1947", "1946" : "1946", "1945" : "1945", "1944" : "1944", "1943" : "1943", "1942" : "1942", "1941" : "1941"

};

$scope.es.hrList = {
"00":"00",
"01":"01","02":"02","03":"03","04":"04","05":"05","06":"06","07":"07","08":"08","09":"09","10":"10",
"11":"11","12":"12","13":"13","14":"14","15":"15","16":"16","17":"17","18":"18","19":"19","20":"20",
"21":"21","22":"22","23":"23"
};

$scope.es.minList = {
 "00":"00"
,"01":"01","02":"02","03":"03","04":"04","05":"05","06":"06","07":"07","08":"08","09":"09","10":"10"
,"11":"11","12":"12","13":"13","14":"14","15":"15","16":"16","17":"17","18":"18","19":"19","20":"20"
,"21":"21","22":"22","23":"23","24":"24","25":"25","26":"26","27":"27","28":"28","29":"29","30":"30"
,"31":"31","32":"32","33":"33","34":"34","35":"35","36":"36","37":"37","38":"38","39":"39","40":"40"
,"41":"41","42":"42","43":"43","44":"44","45":"45","46":"46","47":"47","48":"48","49":"49","50":"50"
,"51":"51","52":"52","53":"53","54":"54","55":"55","56":"56","57":"57","58":"58","59":"59"

};

};
;
var initLangOverrides = function initLangOverrides($scope, $http, $modal, $log, $sce, $cookies, $window){
    
    $scope.es.appContextRoot="https://lagnsthal.com";
    $scope.es.appEAddress="care@lagnsthal.com";
    $scope.es.appEscEmailAddress="care@lagnsthal.com";
    $scope.es.appWhatsapp="+91-88880-84629";
    $scope.es.appName="Lagnsthal";
    $scope.es.appVersion=appVersion;
    $scope.es.appTagline="India's #1 Affordable and Secure Matrimony";
    $scope.es.appDomain="lagnsthal.com";

    $scope.es.msg_lowBalance="Your balance is less than minimum balance required to send / accept request. Click here to recharge with at least Rs. ";
    
    initLangMessages($scope, $http, $modal, $log, $sce, $cookies, $window);
    initDropDowns($scope, $http, $modal, $log, $sce, $cookies, $window);
};


;
function initLangMessages($scope, $http, $modal, $log, $sce, $cookies, $window){

$scope.es.lg={};

$scope.es.lg.CareNumber = "+91-7506004585";
$scope.es.lg.ShortestForm="Shortest Form to start with";
$scope.es.lg.Personal="Personal Details";

$scope.es.lg.New="New";
$scope.es.lg.PhoneNumber="Phone Number";
$scope.es.lg.Gender="Gender";
$scope.es.lg.BirthDate="Birth Date";
$scope.es.lg.Income="Income";
$scope.es.lg.Lakhs="Lakhs";

$scope.es.lg.FirstName="First Name";
$scope.es.lg.LastName="Last Name";

$scope.es.lg.Ft="Ft";
$scope.es.lg.Inches="Inches";
$scope.es.lg.Kg="Kg";

$scope.es.lg.MaritalStatus="Marital Status";
$scope.es.lg.About="About";
$scope.es.lg.BriefIntroduction="Brief Introduction";
$scope.es.lg.Physical="Physical Attributes";
$scope.es.lg.Height="Height";
$scope.es.lg.Weight="Weight";
$scope.es.lg.BloodGroup="Blood Group";
$scope.es.lg.BodyType="Body Type";
$scope.es.lg.SkinColor="Skin Color";
$scope.es.lg.Challenged="Challenged";
$scope.es.lg.Diet="Diet";
$scope.es.lg.Lifestyle="Lifestyle";

$scope.es.lg.Smoking="Smoking";
$scope.es.lg.Drinking="Drinking";
$scope.es.lg.Ethnicity="Ethnicity";
$scope.es.lg.Religion="Religion";
$scope.es.lg.Caste="Caste";
$scope.es.lg.MotherTongue="Mother Tongue";
$scope.es.lg.Birth="Birth Details";
$scope.es.lg.Date="Date";
$scope.es.lg.Day="Day";
$scope.es.lg.Month="Month";
$scope.es.lg.Year="Year";
$scope.es.lg.Years="Years";
$scope.es.lg.Time="Time";
$scope.es.lg.Hour="Hour";
$scope.es.lg.Minutes="Minutes";
$scope.es.lg.Country="Country";
$scope.es.lg.State="State";
$scope.es.lg.City="City";

$scope.es.lg.Job="Job";
$scope.es.lg.Education="Education";
$scope.es.lg.Details="Details";
$scope.es.lg.Optional="Optional";
$scope.es.lg.Profession="Profession";
$scope.es.lg.Stream="Stream";
$scope.es.lg.Designation="Designation";

$scope.es.lg.Family="Family Details";
$scope.es.lg.FatherOccupation="Father's Occupation";
$scope.es.lg.MotherOccupation="Mother's Occupation";
$scope.es.lg.Total="Total";
$scope.es.lg.Brothers="Brothers";
$scope.es.lg.Sisters="Sisters";
$scope.es.lg.Married="Married";
$scope.es.lg.Wealth="Wealth";

$scope.es.lg.Kundali="Kundali";
$scope.es.lg.Ras="Ras";
$scope.es.lg.Nakshatra="Nakshatra";
$scope.es.lg.Nadi="Nadi";
$scope.es.lg.Charan="Charan";
$scope.es.lg.Gan="Gan";
$scope.es.lg.Gotra="Gotra";
$scope.es.lg.Devak="Devak";

$scope.es.lg.CheckAll="Check All";
$scope.es.lg.UncheckAll="Uncheck All";
$scope.es.lg.Done="Done";

$scope.es.lg.PleaseConfirm="Please confirm if details mentioned above are correct";
$scope.es.lg.MandatoryInputs="Mandatory inputs are marked with Red ribbon. Please fill up those";
$scope.es.lg.OnlyAccepted="Only accepted connections can see each other's contact details";
$scope.es.lg.ProfileToOthers="This is how your profile would look like to others";

$scope.es.lg.Select="Please Select";
$scope.es.lg.PleaseSelect="Please Select";

$scope.es.lg.MaximumDegree="Maximum Degree";

$scope.es.lg.Contact="Contact";
$scope.es.lg.ContactDetails="Contact Details";
$scope.es.lg.Secure="Secure";
$scope.es.lg.OtherCell="Alternate";
$scope.es.lg.LoginCredentials="Login Credentials";
$scope.es.lg.UserName="User Name";
$scope.es.lg.Password="Password";


$scope.es.lg.CountryCode="Country Code";

$scope.es.lg.Register="Register";
$scope.es.lg.SignIn="Sign in";
$scope.es.lg.Search="Search Profiles";

$scope.es.lg.MembershipValidTill="Membership End Date";
$scope.es.lg.Membership="Membership";

$scope.es.lg.MembershipText="You can extend your membership by one year by paying just 1000 Rs./. This is entirely refundable amount. If you do not get any acceptance, your entire amount will be refunded in 30 working days.";

$scope.es.lg.Amount="Amount";
$scope.es.lg.PaymentDetails="Payment Details";
$scope.es.lg.ReflectPayment="It may take few hours to reflect your latest payment";

$scope.es.lg.Proceed="Proceed";
$scope.es.lg.Feedback="Feedback";
$scope.es.lg.FeedbackText="What you feel is important to us. If you have any <B>suggestions, queries, feedback, complaints</B> please drop us an email at below mentioned address"

$scope.es.lg.RefundRequest="Refund Request";
$scope.es.lg.RefundRequestText1="If none of your connection requests are accepted and your account is at least 3 months old, you can initiate refund request.";
$scope.es.lg.RefundRequestText2="On initiation of Refund Request <B>all the amount you paid so far will be refunded to the same payment source / bank account within maximum of 30 calendar days</B>, and your account will be deactivated. To initiate refund, please email us your profile id and bank account details.";


$scope.es.lg.Refer="Refer and Earn 200/- Rs. for each referral";
$scope.es.lg.ReferText="If you know someone eligible for marriage, you can send them your 'Referral Code'. When they register and mention your referral code and make payment, you earn 200/- Rs. in your bank account. So start earning now. Feel up below form to get your referral code.";
$scope.es.lg.ReferButton="Get Referral Code";
$scope.es.lg.BankAccount="Bank Account";
$scope.es.lg.IFSCCode="IFSC Code";
$scope.es.lg.EnterDetails="Please Enter Details To Proceed";

$scope.es.lg.ReferStatus="Payment Status Check";
$scope.es.lg.ReferStatusText="You can check status of your payments here";
$scope.es.lg.VerificationCodeButton="Get Verification Code";
$scope.es.lg.VerificationCode="Verification Code";
$scope.es.lg.Verification="Verification";

$scope.es.lg.QuickLinks="Search by Type of Request";
$scope.es.lg.From="From";
$scope.es.lg.To="To";

$scope.es.lg.RangeFrom="Minimum";
$scope.es.lg.RangeTo="Maximum";

$scope.es.lg.Manglik="Manglik";

$scope.es.lg.OneTime="One Time";
$scope.es.lg.SetAsExpectation="Set as Expectations";
$scope.es.lg.ProfileId="Profile Id";
$scope.es.lg.ProfileIdAndProfilePic="Your Profile Id and Profile Pic would appear Here...!";
$scope.es.lg.UpdateProfile="Update Profile";

$scope.es.AlbumPics="Album Pics";
$scope.es.lg.Residence="Residence";
$scope.es.lg.NativePlace="Native";

$scope.es.lg.UpdateExpectations="This will update your expectations, Continue?";
$scope.es.lg.removeFromWall="This Profile will be permanently removed from your Wall, Continue?"
$scope.es.lg.shortlistProfile="This Profile will be added to your Shortlisted Profile, Continue?";
$scope.es.lg.sendConnectionRequest="This Profile will be sent connection request, Continue?";
$scope.es.lg.undoRemoveFromWall="This Profile will be added back to your Wall, Continue?";
$scope.es.lg.withdrawRequest="This will withdraw the request; Continue?";
$scope.es.lg.acceptedMsg= "Both you and this profile would be able to see each others Contact Details, Continue?";
$scope.es.lg.rejectedMsg="None of you would be able to connect with each other hence on, Are you sure to continue?";


$scope.es.lg.PwdResetForm="Password Reset Form";
$scope.es.lg.ConfirmPwd="Confirm Password";
$scope.es.lg.Submit="Submit";
$scope.es.lg.Cancel="Cancel";
$scope.es.lg.Clear="Clear";
$scope.es.lg.Forgot="Forgot";
$scope.es.lg.LoginForm="Login Form";
$scope.es.lg.InvalidCredentials="Please click 'Register' if you have not registered with us before, or else please click 'Forgot' to reset your password.";
$scope.es.lg.ErrorWhileProcessing="Error while processing";
$scope.es.lg.ShowHideDetails="Show / Hide Details";
$scope.es.lg.EDetails="Please mail us below details, so that we can resolve this issue at earliest. Thank You!";
$scope.es.lg.Close="Close";
$scope.es.lg.Information="Information";
$scope.es.lg.Confirmation="Confirmation";
$scope.es.lg.AdvancedSearch="Advanced Search / Manage Expectations";
$scope.es.lg.SearchById="Search By Id";
$scope.es.lg.MarkRead ="Mark Read";
$scope.es.lg.ViewAction="Action";
$scope.es.lg.View="View";
$scope.es.lg.ReadOn="Read On";
$scope.es.lg.ReceivedOn="Received On";
$scope.es.lg.ThisInactiveProfile="This profile is currently inactive.";
$scope.es.lg.Album="Album";
$scope.es.lg.ManageAlbum="Manage Album";
$scope.es.lg.BrowseImage="Select Image";
$scope.es.lg.UploadAs="Upload As";
$scope.es.lg.ProfilePic="Profile Pic";
$scope.es.lg.Image="Image";
$scope.es.lg.ViewUploads="View Uploads";
$scope.es.lg.ViewAlbum="View Album";

$scope.es.lg.Registration="Congratulations! Your account is created successfully!";
$scope.es.lg.ThanksRegards="Thanks and Regards";
$scope.es.lg.SendingVerificationCode="Sending SMS...";
$scope.es.lg.Documents="Documents";
$scope.es.lg.MoreOnProfessionDetails="Designation etc.";
$scope.es.lg.DesignationEg="Student / Owner / Senior Manager";

$scope.es.lg.Donate="Donate Us?";
$scope.es.lg.Connect="Connect";
$scope.es.lg.Accept="Accept";
$scope.es.lg.Reject="Reject";
$scope.es.lg.Withdraw="Withdraw";
$scope.es.lg.Shortlist="Shortlist";
$scope.es.lg.Recharge="Recharge";
$scope.es.lg.ViewContact="View Contact";
$scope.es.lg.Remove="Remove";
$scope.es.lg.UndoRemove="Undo Remove";
$scope.es.lg.WithdrawTitle="This will withdraw the request and refund the money back to your wallet.";
$scope.es.lg.ShowContactDetail="Show the Contact Details!";
$scope.es.lg.AcceptTitle="Click here to Accept the request, Once Accepted, You both can see each others Contact Details!";
$scope.es.lg.ConnectTitle="Send Connection Request, if accepted, you can see each other's contact details!";
$scope.es.lg.UndoRemoveTitle="Undo-Remove from My Wall, I accidently removed this profile!";
$scope.es.lg.RemoveTitle="Remove from My Wall, I do not want to see this profile again!";
$scope.es.lg.ShortlistTitle="Click here to Shortlist the request!";
$scope.es.lg.URL="URL";
$scope.es.lg.Update="Update";
$scope.es.lg.More="More";

$scope.es.lg.ProfilesList="Profiles List";

$scope.es.lg.WallFeed="Profiles List";
$scope.es.lg.Received="Requests Received";
$scope.es.lg.Expected="Top Recommendations";
$scope.es.lg.Sent="Requests Sent";
$scope.es.lg.Withdrawn="Requests Withdrawn";
$scope.es.lg.Shortlisted="Shortlisted Profiles";
$scope.es.lg.BuyContacts="Contacts Book";
$scope.es.lg.Removed="Removed Profiles";
$scope.es.lg.Accepted="Accepted Profiles";
$scope.es.lg.Rejected="Rejected Profiles";

$scope.es.lg.Me="Me";
$scope.es.lg.Them="Them";
$scope.es.lg.Either="Either";

$scope.es.lg.ByMe="By You";
$scope.es.lg.ByThem="By Them";
$scope.es.lg.ByEither="By Either";
$scope.es.lg.Requests="Requested Profiles";


$scope.es.lg.AcceptedMe="Profiles accepted by you";
$scope.es.lg.AcceptedThem="Profiles accepted by them";
$scope.es.lg.AcceptedEither="All accepted profiles";

$scope.es.lg.RejectedMe="Profiles rejected by you";
$scope.es.lg.RejectedThem="Profiles rejected by them";
$scope.es.lg.RejectedEither="All rejected profiles";

$scope.es.lg.ExpectedHeader="Top Profiles As Per Your Expectation";
$scope.es.lg.ShortlistedHeader="Shortlisted Profiles";
$scope.es.lg.BuyContactsHeader="Contact Book";
$scope.es.lg.ReceivedHeader="Requests Received";
$scope.es.lg.SentHeader="Requests Sent";
$scope.es.lg.WithdrawnHeader="Requests Withdrawn";
$scope.es.lg.RemovedHeader="Requests Removed";
$scope.es.lg.AcceptedHeaderByEither="Accepted Profiles";
$scope.es.lg.RejectedHeaderByEither="Rejected Profiles";
$scope.es.lg.AcceptedHeaderByMe="Accepted Profiles (By You)";
$scope.es.lg.RejectedHeaderByMe="Rejected Profiles (By You)";
$scope.es.lg.AcceptedHeaderByThem="Accepted Profiles (By Them)";
$scope.es.lg.RejectedHeaderByThem="Rejected Profiles (By Them)";
$scope.es.lg.NoMoreProfiles="Oops! No Profiles Found..";
$scope.es.lg.Donation="Donation";
$scope.es.lg.DonationText1="We do not sell your data to anyone, your donation is the only source of income we do have. So if you like the service, please think of donating to us.";
$scope.es.lg.DonationText2="We will be obliged. We assure you the best service in return. Thank You!";

$scope.es.lg.ActionedOn="Actioned On";
$scope.es.lg.NotActioned="Not Actioned Yet";
$scope.es.lg.Logout="Logout";
$scope.es.lg.Notifications="Notifications";
$scope.es.lg.IdentityDocument="Identity Document";

$scope.es.lg.CurrentResidence="Current Residence";
$scope.es.lg.FamilyResidence="Family Residence";
$scope.es.lg.LoginToSearch="Register for free to search";
$scope.es.lg.MultiselectFilter="Type to filter";


$scope.es.lg.RememberMe="Remember Me";
$scope.es.lg.RefundButton="Refund money and delete the profile";
$scope.es.lg.OneTimeSearchText="Click here for one time search";
$scope.es.lg.ExpectationSearchText="Click here if these are your expectations";
$scope.es.lg.OneTimeSearchWallHeader="Profiles as per one time search";
$scope.es.lg.aboutPlaceholder="Optional - About You";
$scope.es.lg.EverythingOptional="These are all optional expectations. Keep fields empty if you are okay with any value"
$scope.es.lg.UpdateLong="Update Details";
$scope.es.lg.AlbumLong="Upload Images";
$scope.es.lg.ManageAccount="Manage Account";
$scope.es.lg.DocumentsLong="Upload Document";
$scope.es.lg.EducationJob="Education / Job";
$scope.es.lg.BrowseImageAndUpload="You can upload image in 2 simple steps";
$scope.es.lg.ImageUploadStep1="Please select the image to upload.";
$scope.es.lg.ImageUploadStep2="Please verify the image and click on upload.";

$scope.es.lg.FillCodeHere="Fill code here";
$scope.es.lg.RegisterInstructions="Please fill up below information and request for code";
$scope.es.lg.ClickHereTypingSupport="To help with typing in reginal languages, click here";
$scope.es.lg.Expectations="Expectations";
$scope.es.lg.expectationsPlaceholder="Expectations in brief";
$scope.es.lg.digitPassword="Password should have at least 4 characters";
$scope.es.lg.pwdConfPwdNoMatch="Password and Password (confirm) do not match";
$scope.es.lg.Yes="Yes";
$scope.es.lg.No="No";
$scope.es.lg.NextProfiles="Click here to view next profiles";
$scope.es.lg.MarketingHeader="How did you get to know about us?";
$scope.es.lg.MarketingLabel="Reference";
$scope.es.lg.MarketingOtherLabel="Details";

// Verified on 21st July

$scope.es.lg.UploadPicToViewPic1="You need to upload";
$scope.es.lg.UploadPicToViewPic2="your profile image";
$scope.es.lg.UploadPicToViewPic3="to view other's image";
$scope.es.lg.ClickHere="Please click here to upload";

$scope.es.lg.UploadImage="Upload Image";
$scope.es.lg.InWords="In words";
$scope.es.lg.ClickHereToSetExpectations="Profiles not as per your expectations? Click here to change the expectations.";
$scope.es.lg.Minimum="Minimum";
$scope.es.lg.RegisterToSeeMoreProfiles="Register free today to view all profiles";
$scope.es.lg.ProfileWithPicOnly="Profiles with photo only";

$scope.es.lg.Previous="Previous";
$scope.es.lg.Next="Next";

$scope.es.lg.AndroidApp="Android App is here";
$scope.es.lg.AndoridAppDownload="Please rate us on Google Play and support us! Thank You!";

$scope.es.lg.RegisterNow="Register Now For Free!";

$scope.es.lg.SureToDeleteMyProfile="Are you sure to delete your profile ?";
$scope.es.lg.DeleteMyProfile="Delete My Profile";

$scope.es.lg.lastLogin="Last Login On";
$scope.es.lg.ActWithin45Days="Please accept or reject the request within 45 days. Thank You!";

$scope.es.lg.ExpectationsLine="Please let us know what kind of profiles you are interested in.";
$scope.es.lg.ExpectationsLineButton="Click here to set the expectations";

$scope.es.lg.PENDING="Pending";
$scope.es.lg.REGISTER="Register";
$scope.es.lg.MEMBERSHIP="Membership";
$scope.es.lg.FEATURES="Features";
$scope.es.lg.CONTACTS="Contact Us";

$scope.es.lg.SaveAsDraft="Save as draft";
$scope.es.lg.Welcome="Welcome";
$scope.es.lg.DownloadApp="Download App";
$scope.es.lg.RateUs="Please Rate Us";
$scope.es.lg.Support="Support";

$scope.es.lg.AnyIssues="Facing Issues? Click here."
$scope.es.lg.RegisterToday="Complete your registration today and get premium membership absolutely free for first month. Offer valid for limited profiles only. Hurry!";

$scope.es.lg.searchPartnerWithUs = "Find your soulmate with lagnsthal.com";
$scope.es.lg.tagLine1 = "India's #1 affordable and Secure Matrimony!";
$scope.es.lg.tagLine2 = "Get free month of premium";
$scope.es.lg.tagLine3 = "Click here to Register";


$scope.es.lg.wishes1="Happy New Year 2021!";

};



;
var initializeNotifications = function ($scope, $http){
	//if($scope.es.loggedInUserId){
		loadNotifications($scope, $http);
	//}
};

var loadNotifications = function ($scope, $http){	
	startAjax('LOAD_NOTIFICATIONS', $scope);
	$http.post(URL_LOAD_NOTIFICATIONS, $scope.es.editProfile ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('LOAD_NOTIFICATIONS', $scope, data, status, headers, config);
    	$scope.es.notifications = data.responseData;
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('LOAD_NOTIFICATIONS', $scope, data, status, headers, config);
    });
};

var markNotificationAsRead = function ($scope, $http, notificationId){	
	startAjax('MARK_NOTIFICATION_AS_READ', $scope);
	$http.post(URL_MARK_NOTIFICATIONS_READ, notificationId ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('MARK_NOTIFICATION_AS_READ', $scope, data, status, headers, config);
    	loadUnreadNotifications($scope, $http);
    	loadNotifications($scope, $http);
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('MARK_NOTIFICATION_AS_READ', $scope, data, status, headers, config);
    });
};

var loadUnreadNotifications = function ($scope, $http){

	startAjax('LOAD_UNREAD_NOTIFICATIONS', $scope);
	$http.post(URL_LOAD_UNREAD_NOTIFICATIONS, $scope.es.editProfile ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('LOAD_UNREAD_NOTIFICATIONS', $scope, data, status, headers, config);
    	$scope.es.unreadNotificationsCount=data.responseData;

    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('LOAD_UNREAD_NOTIFICATIONS', $scope, data, status, headers, config);
    });
};

;

var updateMyProfile = function ($scope, $http,updateForm){
	
	if(updateForm.$invalid){
	    var msg = $scope.es.lg.MandatoryInputs;
    	basicPopup($scope, $http, "Information", msg,"Ok");
		return;
	}

	if($scope.es.editProfile.updateCell){
	    if(!$scope.es.editProfile.newCell){
	        var msg = "Please enter valid phone number";
            basicPopup($scope, $http, "Information", msg,"Ok");
        	return;
	    }
	    if(!$scope.es.editProfile.verificationCode){
            var msg = "Please enter valid verification code";
            basicPopup($scope, $http, "Information", msg,"Ok");
            return;
        }
	}

	confirmationMsg =  $scope.es.lg.PleaseConfirm;
        confirmationPopup(
                $scope, $http, confirmationMsg,
                function(){

                        var currProfileDetails = $scope.es.editProfile.profileDetails;

                        startAjax('UPDATE_PROFILE', $scope);
                        $scope.es.inProgress.updateProfile="In Progress...";

                        $http.post(URL_UPDATE_MY_PROFILE, $scope.es.editProfile ).
                        success(function(data, status, headers, config) {
                            $scope.es.inProgress.updateProfile=null;
                            handleAjaxSuccess('UPDATE_PROFILE', $scope, data, status, headers, config);
                            $scope.es.profileNotCreated = false;
                            $scope.es.loadMenuPage('/#/page/homeView');
                        }).
                        error(function(data, status, headers, config) {
                            $scope.es.inProgress.updateProfile=null;
                            handleAjaxError('UPDATE_PROFILE', $scope, data, status, headers, config);
                        });
                }
    );

};


var deleteMyProfile = function ($scope, $http){

	confirmationMsg =  $scope.es.lg.SureToDeleteMyProfile;
        confirmationPopup(
                $scope, $http, confirmationMsg,
                function(){

                        startAjax('DELETE_PROFILE', $scope);

                        $http.post(URL_DELETE_MY_PROFILE).
                        success(function(data, status, headers, config) {
                            handleAjaxSuccess('DELETE_PROFILE', $scope, data, status, headers, config);
                            window.open("logout","_self", "", false);
                        }).
                        error(function(data, status, headers, config) {
                            handleAjaxError('DELETE_PROFILE', $scope, data, status, headers, config);
                        });
                }
    );

};

var uploadImage = function ($scope, $http, imageType){
	startAjax('UPLOAD_IMAGE', $scope);
	$scope.es.inProgress.uploadImage="In Progress...";

	var formData = new FormData();
	formData.append("file",$scope.es.uploadFile);
	formData.append("imageType",imageType);
	
	$http({
        method: 'POST',
        url: URL_UPLOAD_IMAGE,
        headers: {
            'Content-Type': undefined
        },
        data: formData,
        transformRequest: function(data, headersGetterFunction) {
            return data; // do nothing! FormData is very good!
        }
        /*transformRequest: function (data, headersGetter) {
            var formData = new FormData();
            angular.forEach(data, function (value, key) {
                formData.append(key, value);
            });

            var headers = headersGetter();
            delete headers['Content-Type'];

            return formData;
        }*/
    })
    .success(function(data, status, headers, config) {
        $scope.es.inProgress.uploadImage=null;
    	handleAjaxSuccess('UPLOAD_IMAGE', $scope, data, status, headers, config);
    	openMyProfile($scope, $http);
    })
    .error(function(data, status, headers, config) {
        $scope.es.inProgress.uploadImage=null;
    	handleAjaxError('UPLOAD_IMAGE', $scope, data, status, headers, config);
    });
	
};

var getLoggedInProfile = function($scope, $http){

    $scope.es.profileNotCreated = false;
    startAjax('LOGGED_PROFILE', $scope);
	$http.post(URL_GET_LOGGED_IN_PROFILE,"").
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('LOGGED_PROFILE', $scope, data, status, headers, config);
    	$scope.es.loggedInProfile = data.responseData;
    	$scope.es.editProfile = data.responseData;
    }).
    error(function(data, status, headers, config) {
        handleAjaxError('LOGGED_PROFILE', $scope, data, status, headers, config);
        $scope.es.profileNotCreated = true;
    	$scope.es.readProfileCookie();
        $scope.es.editProfile.profileDetails.profileId = null;
        $scope.es.editProfile.updateCell = false;
    });
};

var openMyProfile = function($scope, $http){
    $scope.es.search = {};
	$scope.es.search.type="preview";
};

var searchById = function($scope, $http){

	if($scope.es.searchId){
        alert("Please enter valid Profile Id.");
    }

	if($scope.es.searchId){
		startAjax('SEARCH_BY_ID', $scope);
		$http.post(URL_SEARCH_BY_ID,$scope.es.searchId).
	    success(function(data, status, headers, config) {

	    	handleAjaxSuccess('SEARCH_BY_ID', $scope, data, status, headers, config);
	    	$scope.es.searchedProfiles = data.responseData;
            if($scope.es.searchedProfiles){
                $scope.es.filteredProfiles = $scope.es.searchedProfiles.slice(0, $scope.es.numPerPage);
            }else{
                $scope.es.filteredProfiles = [];
            }

	    }).
	    error(function(data, status, headers, config) {
	    	handleAjaxError('SEARCH_BY_ID', $scope, data, status, headers, config);
	    });
	}

};


var openFullProfileById = function ($scope, $http, showInternalId){

    startAjax('FULL_PROFILE_BY_ID', $scope);
    $http.post(URL_SEARCH_BY_ID,showInternalId).
    success(function(data, status, headers, config) {
        handleAjaxSuccess('FULL_PROFILE_BY_ID', $scope, data, status, headers, config);
        $scope.es.searchedProfiles = data.responseData;
    	if($scope.es.searchedProfiles){
    	    $scope.es.filteredProfiles = $scope.es.searchedProfiles.slice(0, $scope.es.numPerPage);
    	}else{
            $scope.es.filteredProfiles = [];
        }
        $scope.es.viewFullProfile($scope.es.searchedProfiles[0]);
    }).
    error(function(data, status, headers, config) {
        handleAjaxError('FULL_PROFILE_BY_ID', $scope, data, status, headers, config);
    });

};


;
var initializeSearch = function ($scope, $http){
	$scope.es.search = {};
	$scope.es.search.type = "";
	$scope.es.searchedProfiles = {};

};

;
var initiateRefundRequest = function ($scope, $http){

    var msg = "Your account must be at least 3 months old in order to initiate refund request.";
    basicPopup($scope, $http, "Information", msg,"Ok");
    return;

};

;
var shortlistProfile = function ($scope, $http, profileDto){
	startAjax('SHORTLIST_PROFILE', $scope);

	var shortlistInfo = {};
	shortlistInfo.profileId = profileDto.profileDetails.profileId;
	shortlistInfo.internalId = profileDto.profileDetails.internalId;

	$http.post(URL_SHORTLIST_PROFILE, shortlistInfo ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('SHORTLIST_PROFILE', $scope, data, status, headers, config);
    	loadWalletDetails($scope, $http);
    	//loadWallProfiles($scope, $http);
    	removeElementFromArray($scope.es.searchedProfiles, profileDto);
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('SHORTLIST_PROFILE', $scope, data, status, headers, config);
    });
};


var searchProfiles = function ($scope, $http){
	startAjax('SEARCH_PROFILES', $scope);
	$http.post(URL_SEARCH_PROFILES, $scope.es.search.type ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('SEARCH_PROFILES', $scope, data, status, headers, config);
    	$scope.es.searchedProfiles = data.responseData;
    	if($scope.es.searchedProfiles){
    	    $scope.es.filteredProfiles = $scope.es.searchedProfiles.slice(0, $scope.es.numPerPage);
    	}else{
            $scope.es.filteredProfiles = [];
        }
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('SEARCH_PROFILES', $scope, data, status, headers, config);
    });
};

var withdrawRequest = function ($scope, $http, profileDto){
	startAjax('WITHDRAW_REQUEST', $scope);

	var withdrawInfo = {};
	withdrawInfo.profileId = profileDto.profileDetails.profileId;
	withdrawInfo.internalId = profileDto.profileDetails.internalId;

	$http.post(URL_WITHDRAW_REQUEST, withdrawInfo).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('WITHDRAW_REQUEST', $scope, data, status, headers, config);
    	loadWalletDetails($scope, $http);
    	searchProfiles($scope, $http);
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('WITHDRAW_REQUEST', $scope, data, status, headers, config);
    });
};

var actionRequest = function ($scope, $http, profileDto, connectionStatus){
	startAjax('ACTION_REQUEST', $scope);

	var actionInfo ={};
	actionInfo.internalId=profileDto.profileDetails.internalId;
	actionInfo.connectionStatus=connectionStatus;

	$scope.es.closePopup('FULL_PROFILE_POPUP');

	$http.post(URL_ACTION_REQUEST, actionInfo ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('ACTION_REQUEST', $scope, data, status, headers, config);
    	if(connectionStatus == 'Accepted'){
            $scope.es.getSecureDetails(profileDto);
    	}
    	searchProfiles($scope, $http);
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('ACTION_REQUEST', $scope, data, status, headers, config);
    });
};

var getSecureDetails = function ($scope, $http, profileDto){

    $scope.es.secureProfile = {};
	startAjax('GET_SECURE_DETAILS', $scope);
	$http.post(URL_GET_SECURE_DETAILS, profileDto.profileDetails.internalId ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('GET_SECURE_DETAILS', $scope, data, status, headers, config);
    	$scope.es.secureProfile=data.responseData;
    	if(data.success){
    	    $scope.es.openPopup('SECURE_PROFILE_POPUP');
    	}

    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('GET_SECURE_DETAILS', $scope, data, status, headers, config);
    });
};


var buySecureDetails = function ($scope, $http, profileDto){

    $scope.es.secureProfile = {};
	startAjax('BUY_SECURE_DETAILS', $scope);
	$http.post(URL_BUY_SECURE_DETAILS, profileDto.profileDetails.internalId ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('BUY_SECURE_DETAILS', $scope, data, status, headers, config);
    	$scope.es.secureProfile=data.responseData;
    	if(data.success){
    	    $scope.es.openPopup('SECURE_PROFILE_POPUP');
    	}

    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('BUY_SECURE_DETAILS', $scope, data, status, headers, config);
    });
};
;
var removeFromWall = function ($scope, $http, profileDto, mode){
	startAjax('REMOVE_FROM_WALL', $scope);

	var removeInfo = {};
	removeInfo.profileId = profileDto.profileDetails.profileId;
	removeInfo.internalId = profileDto.profileDetails.internalId;

	$http.post(URL_REMOVE_FROM_WALL, removeInfo ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('REMOVE_FROM_WALL', $scope, data, status, headers, config);
    	if(mode == 'wall'){
            //loadWallProfiles($scope, $http);
            removeElementFromArray($scope.es.searchedProfiles, profileDto);
        }else if(mode == 'shortlist'){
            loadShortlistedProfiles($scope, $http);
        }else if(mode == 'withdraw'){
            searchProfiles($scope, $http);
        }
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('REMOVE_FROM_WALL', $scope, data, status, headers, config);
    });
};

var shortlistProfile = function ($scope, $http, profileDto){
	startAjax('SHORTLIST_PROFILE', $scope);

	var shortlistInfo = {};
	shortlistInfo.profileId = profileDto.profileDetails.profileId;
	shortlistInfo.internalId = profileDto.profileDetails.internalId;

	$http.post(URL_SHORTLIST_PROFILE, shortlistInfo ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('SHORTLIST_PROFILE', $scope, data, status, headers, config);
    	loadWalletDetails($scope, $http);
    	//loadWallProfiles($scope, $http);
    	removeElementFromArray($scope.es.searchedProfiles, profileDto);
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('SHORTLIST_PROFILE', $scope, data, status, headers, config);
    });
};

var loadShortlistedProfiles = function ($scope, $http){
	$scope.es.search.type = "IShortlisted";
	startAjax('LOAD_SHORTLISTED_PROFILES', $scope);
	$http.post(URL_LOAD_SHORTLISTED_PROFILES, $scope.es.editProfile ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('LOAD_SHORTLISTED_PROFILES', $scope, data, status, headers, config);
    	$scope.es.searchedProfiles = data.responseData;
    	if($scope.es.searchedProfiles){
    	    $scope.es.filteredProfiles = $scope.es.searchedProfiles.slice(0, $scope.es.numPerPage);
    	}else{
            $scope.es.filteredProfiles = [];
        }
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('LOAD_SHORTLISTED_PROFILES', $scope, data, status, headers, config);
    });
};

var loadBuyContactsProfiles = function ($scope, $http){
	$scope.es.search.type = "IBuyContacts";
	startAjax('LOAD_BUY_CONTACTS_PROFILES', $scope);
	$http.post(URL_LOAD_BUY_CONTACTS_PROFILES, $scope.es.editProfile ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('LOAD_BUY_CONTACTS_PROFILES', $scope, data, status, headers, config);
    	$scope.es.searchedProfiles = data.responseData;
    	if($scope.es.searchedProfiles){
    	    $scope.es.filteredProfiles = $scope.es.searchedProfiles.slice(0, $scope.es.numPerPage);
    	}else{
            $scope.es.filteredProfiles = [];
        }
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('LOAD_BUY_CONTACTS_PROFILES', $scope, data, status, headers, config);
    });
};

var loadRemovedProfiles = function ($scope, $http){
	$scope.es.search.type = "IRemoved";
	startAjax('LOAD_REMOVED_PROFILES', $scope);
	$http.post(URL_LOAD_REMOVED_PROFILES, $scope.es.editProfile ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('LOAD_REMOVED_PROFILES', $scope, data, status, headers, config);
    	$scope.es.searchedProfiles = data.responseData;
    	if($scope.es.searchedProfiles){
    	    $scope.es.filteredProfiles = $scope.es.searchedProfiles.slice(0, $scope.es.numPerPage);
    	}else{
            $scope.es.filteredProfiles = [];
        }
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('LOAD_REMOVED_PROFILES', $scope, data, status, headers, config);
    });
};

var undoRemoveFromWall = function ($scope, $http, profileDto){
	startAjax('UNREMOVE_FROM_WALL', $scope);

	var undoRemoveInfo = {};
	undoRemoveInfo.profileId = profileDto.profileDetails.profileId;
	undoRemoveInfo.internalId = profileDto.profileDetails.internalId;

	$http.post(URL_UNDO_REMOVE_FROM_WALL, undoRemoveInfo ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('UNREMOVE_FROM_WALL', $scope, data, status, headers, config);
    	loadRemovedProfiles($scope, $http);
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('UNREMOVE_FROM_WALL', $scope, data, status, headers, config);
    });
};

;

var showQuickLinks = function  ($scope, $http){
	$scope.es.homeQuickLinks = true;
	$scope.es.wallHeader='NA';
	$scope.es.setSelectedInternal("homeView");

};

var initializeWall = function  ($scope, $http){
	if(!$scope.es.wallHeader){
		loadWallProfiles($scope, $http);
		$scope.es.search={};
		$scope.es.search.type='wall';
	}
};

var setQuickSearchMenu = function ($scope, $http,searchType){

    $scope.es.search.type=searchType;

    if(searchType == 'wall'){
        $scope.es.wallHeader=$scope.es.lg.ExpectedHeader ; $scope.es.loadWallProfiles();
    }
    else if(searchType == 'IShortlisted'){
        $scope.es.wallHeader=$scope.es.lg.ShortlistedHeader ; $scope.es.loadShortlistedProfiles();
    }
    else if(searchType == 'IBuyContacts'){
        $scope.es.wallHeader=$scope.es.lg.BuyContactsHeader ; $scope.es.loadBuyContactsProfiles();
    }
    else if(searchType == 'IRemoved'){
        $scope.es.wallHeader=$scope.es.lg.RemovedHeader ; $scope.es.loadRemovedProfiles();
    }
    else if(searchType == 'TheyRequested'){
        $scope.es.wallHeader=$scope.es.lg.ReceivedHeader;
    }
    else if(searchType == 'IRequested'){
        $scope.es.wallHeader=$scope.es.lg.SentHeader;
    }
    else if(searchType == 'Withdrawn'){
        $scope.es.wallHeader=$scope.es.lg.WithdrawnHeader;
    }
    
    
    else if(searchType == 'IAccepted'){
        $scope.es.wallHeader=$scope.es.lg.AcceptedHeaderByMe ;
    }
    else if(searchType == 'TheyAccepted'){
        $scope.es.wallHeader=$scope.es.lg.AcceptedHeaderByThem ;
    }
    else if(searchType == 'Accepted'){
        $scope.es.wallHeader=$scope.es.lg.AcceptedHeaderByEither ;
    }


    else if(searchType == 'IRejected'){
        $scope.es.wallHeader=$scope.es.lg.RejectedHeaderByMe ;
    }
    else if(searchType == 'TheyRejected'){
        $scope.es.wallHeader=$scope.es.lg.RejectedHeaderByThem ;
    }
    else if(searchType == 'Rejected'){
        $scope.es.wallHeader=$scope.es.lg.RejectedHeaderByEither ;
    }

	$scope.es.setSelectedInternal('homeView');
	$scope.es.search = {};
	$scope.es.search.type = searchType;
	$scope.es.searchProfiles();
}


var loadWallProfiles = function ($scope, $http){

	$scope.es.search.type='wall';
	$scope.es.wallHeader=$scope.es.lg.ExpectedHeader;

	startAjax('LOAD_WALL_PROFILES', $scope);
	$http.post(URL_LOAD_WALL_PROFILES, $scope.es.editProfile ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('LOAD_WALL_PROFILES', $scope, data, status, headers, config);
    	$scope.es.searchedProfiles = data.responseData;
    	if($scope.es.searchedProfiles){
    	    $scope.es.filteredProfiles = $scope.es.searchedProfiles.slice(0, $scope.es.numPerPage);
    	}else{
            $scope.es.filteredProfiles = [];
        }

    	var len=0;
    	if($scope.es.searchedProfiles){
    	    len = $scope.es.searchedProfiles.length;
    	}

    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('LOAD_WALL_PROFILES', $scope, data, status, headers, config);
    });
};

var sendConnectionRequest = function ($scope, $http, profileDto, mode){
	startAjax('SEND_CONNECTION_REQUEST', $scope);
	$scope.es.closePopup('FULL_PROFILE_POPUP');
	$http.post(URL_SEND_CONNECTION_REQUEST, profileDto.profileDetails.internalId ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('SEND_CONNECTION_REQUEST', $scope, data, status, headers, config);
    	loadWalletDetails($scope, $http);
        if(mode == 'wall'){
            //loadWallProfiles($scope, $http);
    	    removeElementFromArray($scope.es.searchedProfiles, profileDto);
        }else if(mode == 'shortlist'){
            loadShortlistedProfiles($scope, $http);
        }else if(mode == 'withdraw'){
            searchProfiles($scope, $http);
        }
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('SEND_CONNECTION_REQUEST', $scope, data, status, headers, config);
    });
};


var loadNewProfiles = function ($scope, $http){

	$scope.es.search.type='search';

	startAjax('LOAD_NEW_PROFILES', $scope);
	$http.post(URL_LOAD_NEW_PROFILES, $scope.es.editProfile ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('LOAD_NEW_PROFILES', $scope, data, status, headers, config);
    	$scope.es.searchedProfiles = data.responseData;
    	if($scope.es.searchedProfiles){
    	    $scope.es.filteredProfiles = $scope.es.searchedProfiles.slice(0, $scope.es.numPerPage);
    	}else{
            $scope.es.filteredProfiles = [];
        }
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('LOAD_NEW_PROFILES', $scope, data, status, headers, config);
    });

};


var oneTimeSearch = function ($scope, $http, expectationsForm){

	startAjax('ONE_TIME_SEARCH', $scope);
	$http.post(URL_ONE_TIME_SEARCH, $scope.es.editExpectations ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('ONE_TIME_SEARCH', $scope, data, status, headers, config);
    	$scope.es.wallHeader=$scope.es.lg.OneTimeSearchWallHeader;
    	$scope.es.searchedProfiles = data.responseData;
    	if($scope.es.searchedProfiles){
    	    $scope.es.filteredProfiles = $scope.es.searchedProfiles.slice(0, $scope.es.numPerPage);
    	}else{
            $scope.es.filteredProfiles = [];
        }
    	$scope.es.setSelectedInternal("homeView");
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('ONE_TIME_SEARCH', $scope, data, status, headers, config);
    });
};


var guestSearch = function ($scope, $http, expectationsForm){

	startAjax('GUEST_SEARCH', $scope);
	$http.post(URL_GUEST_SEARCH, $scope.es.editExpectations ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('GUEST_SEARCH', $scope, data, status, headers, config);
    	$scope.es.wallHeader=$scope.es.lg.ProfilesList;
    	$scope.es.searchedProfiles = data.responseData;
    	if($scope.es.searchedProfiles){
    	    $scope.es.filteredProfiles = $scope.es.searchedProfiles.slice(0, $scope.es.numPerPage);
    	}else{
            $scope.es.filteredProfiles = [];
        }
    	$scope.es.setSelectedInternal("userView");
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('GUEST_SEARCH', $scope, data, status, headers, config);
    });
};


var loadGrooms = function ($scope, $http, searchType){

	$scope.es.search.type=searchType;
	$scope.es.wallHeader="";

	startAjax('LOAD_GROOMS', $scope);
	var request = { 'searchType' : searchType};

	$http.post(URL_LOAD_GROOMS, request).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('LOAD_GROOMS', $scope, data, status, headers, config);
    	$scope.es.searchedProfiles = data.responseData;
    	if($scope.es.searchedProfiles){
    	    $scope.es.filteredProfiles = $scope.es.searchedProfiles.slice(0, $scope.es.numPerPage);
    	}else{
            $scope.es.filteredProfiles = [];
        }
    	var len=0;
    	if($scope.es.searchedProfiles){
    	    len = $scope.es.searchedProfiles.length;
    	}

    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('LOAD_GROOMS', $scope, data, status, headers, config);
    });
};


var loadBrides = function ($scope, $http, searchType){

	$scope.es.search.type=searchType;
	$scope.es.wallHeader="";

	startAjax('LOAD_BRIDES', $scope);
	var request = { 'searchType' : searchType};

	$http.post(URL_LOAD_BRIDES, request).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('LOAD_BRIDES', $scope, data, status, headers, config);
    	$scope.es.searchedProfiles = data.responseData;
    	if($scope.es.searchedProfiles){
    	    $scope.es.filteredProfiles = $scope.es.searchedProfiles.slice(0, $scope.es.numPerPage);
    	}else{
            $scope.es.filteredProfiles = [];
        }
    	var len=0;
    	if($scope.es.searchedProfiles){
    	    len = $scope.es.searchedProfiles.length;
    	}

    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('LOAD_BRIDES', $scope, data, status, headers, config);
    });
};


;
var initializeWallet = function ($scope, $http){
	//if($scope.es.loggedInUserId){
		loadWalletDetails($scope, $http);
		loadWalletTransactions($scope, $http);
	//}
};


var loadWalletDetails = function ($scope, $http){

	startAjax('LOAD_WALLET_DTLS', $scope);
	$http.post(URL_LOAD_WALLET_DETAILS, $scope.es.editProfile ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('LOAD_WALLET_DTLS', $scope, data, status, headers, config);
    	$scope.es.wallet = data.responseData;
    	$scope.es.wallet.maxAmount = 10000;
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('LOAD_WALLET_DTLS', $scope, data, status, headers, config);
    });
};

var loadWalletTransactions = function ($scope, $http){
	startAjax('LOAD_WTRANSACTIONS', $scope);
	$http.post(URL_LOAD_WALLET_TRANSACTIONS, $scope.es.editProfile ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('LOAD_WTRANSACTIONS', $scope, data, status, headers, config);
    	$scope.es.walletTransactions = data.responseData;
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('LOAD_WTRANSACTIONS', $scope, data, status, headers, config);
    });
};

var rechargeWallet = function ($scope, $http, registerForm){	

	if(registerForm.$invalid){
		alert("Some input values are missing or invalid. Marked with red text or '**' sign. Please correct them and submit the form again.");
		return;
	}
	
	startAjax('RECHARGE_WALLET', $scope);
	$http.post(URL_RECHARGE_WALLET, $scope.es.editProfile ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('RECHARGE_WALLET', $scope, data, status, headers, config);
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('RECHARGE_WALLET', $scope, data, status, headers, config);
    });
};
