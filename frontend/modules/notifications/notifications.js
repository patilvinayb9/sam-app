
var initializeNotifications = function ($scope, $http){
	//if($scope.es.loggedInUserId){
		loadNotifications($scope, $http);
	//}
};

var loadNotifications = function ($scope, $http){	
	startAjax('LOAD_NOTIFICATIONS', $scope);
	$http.post(URL_LOAD_NOTIFICATIONS, $scope.es.editProfile ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('LOAD_NOTIFICATIONS', $scope, data, status, headers, config);
    	$scope.es.notifications = data.responseData;
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('LOAD_NOTIFICATIONS', $scope, data, status, headers, config);
    });
};

var markNotificationAsRead = function ($scope, $http, notificationId){	
	startAjax('MARK_NOTIFICATION_AS_READ', $scope);
	$http.post(URL_MARK_NOTIFICATIONS_READ, notificationId ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('MARK_NOTIFICATION_AS_READ', $scope, data, status, headers, config);
    	loadUnreadNotifications($scope, $http);
    	loadNotifications($scope, $http);
    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('MARK_NOTIFICATION_AS_READ', $scope, data, status, headers, config);
    });
};

var loadUnreadNotifications = function ($scope, $http){

	startAjax('LOAD_UNREAD_NOTIFICATIONS', $scope);
	$http.post(URL_LOAD_UNREAD_NOTIFICATIONS, $scope.es.editProfile ).
    success(function(data, status, headers, config) {
    	handleAjaxSuccess('LOAD_UNREAD_NOTIFICATIONS', $scope, data, status, headers, config);
    	$scope.es.unreadNotificationsCount=data.responseData;

    }).
    error(function(data, status, headers, config) {
    	handleAjaxError('LOAD_UNREAD_NOTIFICATIONS', $scope, data, status, headers, config);
    });
};

