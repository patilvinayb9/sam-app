
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
