
function coreInitVar ($scope, $http, $modal, $log, $sce, $cookies, $window){
		
	$scope.es.ajaxCount = 0;
	$scope.es.name = "vinay";

	$scope.es.inProgress = {};
	$scope.es.alerts = [];
	
	$scope.es.openedPopups = {};
	$scope.es.confirmPopup = {};

	$scope.es.signUpForm = {};
	$scope.es.signInForm = {};
	$scope.es.signUpForm.emailId = 'a@a.com';

	initDropDowns($scope, $http, $modal, $log, $sce, $cookies, $window);
}
