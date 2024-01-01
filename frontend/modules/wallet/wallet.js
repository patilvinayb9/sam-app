
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
