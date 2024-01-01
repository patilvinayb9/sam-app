
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



