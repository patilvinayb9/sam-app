
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
