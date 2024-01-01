
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

