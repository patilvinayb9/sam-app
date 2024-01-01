

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


