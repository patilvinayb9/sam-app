var initializeExpectations = function ($scope, $http){
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


