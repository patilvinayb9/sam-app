
var digits = /^[0-9]+$/;

edgeApp.
filter('checkmark', function() {
    return function(input) {
      return input ? '\u2713' : '\u2718';
    };
});

edgeApp.filter('ageFilter', function(){
    return function(birthday){
        birthday = new Date(birthday);
        var today = new Date();
        var age = ((today - birthday) / (31557600000));
        age = Math.floor( age );
        return age;
    };
});


edgeApp.
filter('min2digit', function() {
    return function(input) {
       return min2digitFun(input);
    };
});


edgeApp.directive('sButton', function() {
  return {
    scope: {
    	text: '@',
    	inprogress: '='
    },
    templateUrl: 'modules/templates/special_button.html'
  };
});

var min2digitFun = function(input) {
      if (input < 10){
           return '0' + input;
      }else{
           return '' + input;
      }
};

var coreOnLoad = function($scope, $http, $modal, $log, $sce, $cookies, $window){

	// CORE - ON LOAD TASKS
	if($scope.es.pageID == 'loginFailure'){
		// if($scope.es.location.search().auth == 'fail'){
		$scope.es.openPopup('SIGN_IN_POP_UP');
	}

	$scope.es.getLoggedInUser();

	$scope.es.pageRedirection = $scope.es.location.search().pageRedirection;

	if($scope.es.pageRedirection){
		$scope.es.setSelectedView($scope.es.pageRedirection);
	}else if($scope.es.searchTYPE){
	    $scope.es.setQuickSearchMenu($scope.es.searchTYPE);
	}else if($scope.es.pageID){
	    $scope.es.setSelectedInternal($scope.es.pageID);
	}else{
	    $scope.es.pageID = 'homeView';
	    $scope.es.setSelectedInternal($scope.es.pageID);
	}

	
	$scope.es.showMessage = $scope.es.location.search().showMessage;
	
	if($scope.es.showMessage){
		$scope.es.messageType = $scope.es.location.search().messageType;
		$scope.es.addAlertNative($scope.es.messageType, $scope.es.showMessage, null, null);
	}
	
};

function coreInitFun($scope, $http, $modal, $log, $sce, $cookies, $window){

	$scope.es.addAlertBasic = function(message){
    		$scope.es.alertDetails = edgeResponse;
            $scope.es.openPopup('ALERT_POPUP');
    };

	$scope.es.addAlertNative = function(type, header, messages, errorHtml){
		$scope.es.alerts.push({ type : type, header : header, messages : messages, errorHtml: errorHtml });
	};
	
	$scope.es.addAlert = function(edgeResponse) {
		if(edgeResponse.header){
		$scope.es.closeable = false;
			/*$scope.es.alerts.push({
                    type : edgeResponse.type, header : edgeResponse.header, messages : edgeResponse.messages,
                    errors : edgeResponse.errors, footer : edgeResponse.footer
            });*/
            $scope.es.alertDetails = edgeResponse;
            $scope.es.openPopup('ALERT_POPUP');
		}else{
		    if(edgeResponse.error){
		        $scope.es.alerts.push({
                        type : 'danger', header : edgeResponse.message, messages : null,
                        errors : null, footer : null
                });
		    }
		}
	};

	$scope.closeAlert = function(index) {
		$scope.es.alerts.splice(index, 1);
	};

	$scope.es.loadMenuPage = function(newUrl) {
        window.open(newUrl,"_self", "", false);
    };

	$scope.es.setSelectedView = function(pageToView) {
	    var newUrl = "#/page/" + pageToView;
	    var currentUrl = window.location.href;

	    if(currentUrl.includes(newUrl)){
	        $scope.es.setSelectedInternal(pageToView);
	    }else{
	        window.open(newUrl,"_self", "", false);
	    }

	};
	
	$scope.es.setSelectedInternal = function(selectedMenuName) {
	    $scope.es.selectedPage = $scope.es.extendedMenuMap[selectedMenuName];
	};
	
	$scope.es.isSelectedMenu = function(selectedMenuObject) {	
		return $scope.es.selectedPage.id === selectedMenuObject.id ;
	};

	$scope.es.w3_open = function() {
        $scope.es.showNavBar = true;
    };

    $scope.es.w3_close = function() {
        $scope.es.showNavBar = false;
    };
		
	$scope.es.openPopup = function(popupId) {
		$scope.es.closePopup(popupId);
		var popUpDetails = $scope.es.popupMap[popupId];
		$modal.open({
		      templateUrl: popUpDetails.templateUrl,
		      size: popUpDetails.size,
		      controller: 'PopupInstanceCtrl',
		      resolve: {
		    	  es : function () {
		            return $scope.es;
		          },
		          popupId : function () {
		            return popupId;
		          }
		        }
		    });
	};
	
	$scope.es.closePopup = function (popupId) {
		var toClose = $scope.es.openedPopups[popupId];
		if(toClose){
			toClose.dismiss('cancel');
		}
	};
	
	$scope.es.printDiv = function (divName) {
		var printContents = document.getElementById(divName).innerHTML;
		  var popupWin = window.open('', '_blank', 'width=300,height=300');
		  popupWin.document.open();
		  popupWin.document.write('<html><head><link rel="stylesheet" type="text/css" href="style.css" /></head><body onload="window.print()">' + printContents + '</body></html>');
		  popupWin.document.close(); 
	};
	
	$scope.es.signUp = function () {
		signUp($scope, $http);
	};
	
	$scope.es.signIn = function () {
		signIn($scope, $http);
	};
	
	$scope.es.sendVerificationCode = function (completeNumber) {
		sendVerificationCode($scope, $http, completeNumber);
	};

	$scope.es.alert = function (msg) {
		confirmationPopup($scope, $http, msg, null);
	};

	$scope.es.resetPassword = function () {
		resetPassword($scope, $http);
	};
	
	$scope.es.getLoggedInUser = function () {		
		startAjax(null, $scope);
		$http.post(URL_LOGIN_USER).
	    success(function(data, status, headers, config) {
	    	handleAjaxSuccess(null, $scope, data, status, headers, config);
	    	if(data){	    		
	    		$scope.es.loggedInUser = data.responseData;
	    		if($scope.es.loggedInUser){
	    		    $scope.es.loggedInUserId=$scope.es.loggedInUser.internalId;
	    		    if($scope.es.loggedInUser.role === "ROLE_ADMIN"){
	    		        $scope.es.setSelectedView("adminIndexView");
	    		    }
	    		}
	    		$scope.es.tempCustName = $scope.es.loggedInUserId; // DEBUG //TODO
	    		$scope.es.webhook = "Success"; // DEBUG //TODO
	    		$scope.es.uihook = "walletSuccess";	// DEBUG //TODO
	    	}
	    }).
	    error(function(data, status, headers, config) {
	    	handleAjaxError(null, $scope, data, status, headers, config);
	    });
	};
	

	$scope.es.callIfLoggedInRole = function (role, fun) {		
		startAjax(null, $scope);
		$http.post(URL_LOGIN_ROLE).
	    success(function(data, status, headers, config) {
	    	handleAjaxSuccess(null, $scope, data, status, headers, config);
	    	if(data){	    		
	    		$scope.es.loggedInRole = data.responseData;
	    		if($scope.es.loggedInRole == role){
	    			fun();
	    		}
	    	}else{
	    		$scope.es.loggedInRole = "";
	    	}
	    }).
	    error(function(data, status, headers, config) {
	    	$scope.es.loggedInRole = "";
	    	handleAjaxError(null, $scope, data, status, headers, config);
	    });
	};

	$scope.es.reloadPage = function() {
       $window.location.reload();
    };

	$scope.es.manageMenuOnSelection = function () {
		if (isMobile()) {
			// Mobile
			$scope.es.showMenu=false;
		}else{
			$scope.es.showMenu=true;
		}
	};
	
}

function startAjax(popupId, $scope){	
	$scope.es.ajaxCount = $scope.es.ajaxCount + 1;	
}

function handleAjaxSilent (popupId, $scope, data, status, headers, config){
	$scope.es.ajaxCount = $scope.es.ajaxCount - 1;
}

function handleAjaxSuccess (popupId, $scope, data, status, headers, config){

	$scope.es.ajaxCount = $scope.es.ajaxCount - 1;
	if(data){
		$scope.es.addAlert(data);
	}else{
		$scope.es.alerts.push({ type : 'danger', heading : 'Some Error occured while processing. Apologies!', errorHtml : data });
	}	

}

function handleAjaxError (popupId, $scope, data, status, headers, config){

	$scope.es.ajaxCount = $scope.es.ajaxCount - 1;
	if(data){
		$scope.es.addAlert(data);
	}else{
		$scope.es.alerts.push({ type : 'danger', heading : 'Some Error occured while processing. Apologies!' });
	}	
}

function isMobile(){
	if (window.matchMedia('(max-device-width: 1200px)').matches) {
		// Mobile
		return true;
	}else{
		return false;
	}
}


var showImageInPopup = function ($scope, $http, title, imgPath){
	slides=[
	    { 'image':imgPath, 'caption': ''}
    ];
	showSlideshow($scope, $http, title, slides);
};


var showAlbum = function ($scope, $http, profile){
	title = $scope.es.lg.Album + " : "+ profile.profileDetails.profileId;
	slides = profile.profileDetails.profileImages;
	showSlideshow($scope, $http, title, slides);
};

var showSlideshow = function ($scope, $http, title, slides){
    $scope.es.slideshow = {};
    $scope.es.slideshow.interval=2000;
	$scope.es.slideshow.title = title;
	$scope.es.slideshow.slides = slides;
	$scope.es.openPopup('SLIDESHOW_POPUP');
};

var isEmpty = function(inputStr){
    return inputStr == null || inputStr == 'undefined' || inputStr.trim() == '';
};

var basicPopup = function ($scope, $http, title, msg, btnText){

    $scope.es.basicPopup = {};
	$scope.es.basicPopup.title = title;
	$scope.es.basicPopup.msg = msg;
	$scope.es.basicPopup.btnText = btnText;
	$scope.es.openPopup('BASIC_POPUP');
};

var confirmationPopup = function ($scope, $http, msg, okFunction){

    $scope.es.confirmPopup = {};
	$scope.es.confirmPopup.msg = msg;
	$scope.es.confirmPopup.okFunction = okFunction;
	$scope.es.openPopup('CONFIRM_POPUP');
};

var removeElementFromArray = function (srcArray, itemToRemove){
    srcArray.splice(srcArray.indexOf(itemToRemove), 1);
};


var loadFile = function(event) {
    var reader = new FileReader();
    reader.onload = function(){
      var output = document.getElementById('output');
      output.src = reader.result;
    };
    reader.readAsDataURL(event.target.files[0]);
};

var sendVerificationCode = function ($scope, $http, completeNumber){

    if(!completeNumber){
	    var msg = "Please enter valid phone number";
    	basicPopup($scope, $http, "Information", msg,"Ok");
		return;
    }

	$scope.es.verificationCodeStatus=$scope.es.lg.SendingVerificationCode;
	startAjax('VERIFICATION_CODE', $scope);

	$http.post(URL_VERIFICATION_CODE, completeNumber).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('VERIFICATION_CODE', $scope, data, status, headers, config);
    	$scope.es.verificationCodeStatus=data.responseData;
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('VERIFICATION_CODE', $scope, data, status, headers, config);
    	$scope.es.verificationCodeStatus=data.responseData;
    });
};

