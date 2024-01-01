

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


