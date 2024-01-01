

var resetPassword = function resetPassword($scope, $http){
	startAjax('FORGOT_PWD_POP_UP', $scope);
	$http.post(URL_RESET_PASSWORD, $scope.es.signInForm).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('FORGOT_PWD_POP_UP', $scope, data, status, headers, config);
    	if(data && data.success){
    	    $scope.es.otpFailure='';
    	    $scope.es.closePopup('FORGOT_PWD_POP_UP');
    	}else{
    	   $scope.es.otpFailure=data.responseData;
    	}
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('FORGOT_PWD_POP_UP', $scope, data, status, headers, config);
    	$scope.es.closePopup('FORGOT_PWD_POP_UP');
    });
	};