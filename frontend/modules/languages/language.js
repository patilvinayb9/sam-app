
var initLangOverrides = function initLangOverrides($scope, $http, $modal, $log, $sce, $cookies, $window){
    
    $scope.es.appContextRoot="https://lagnsthal.com";
    $scope.es.appEAddress="care@lagnsthal.com";
    $scope.es.appEscEmailAddress="care@lagnsthal.com";
    $scope.es.appWhatsapp="+91-88880-84629";
    $scope.es.appName="Lagnsthal";
    $scope.es.appVersion=appVersion;
    $scope.es.appTagline="India's #1 Affordable and Secure Matrimony";
    $scope.es.appDomain="lagnsthal.com";

    $scope.es.msg_lowBalance="Your balance is less than minimum balance required to send / accept request. Click here to recharge with at least Rs. ";
    
    initLangMessages($scope, $http, $modal, $log, $sce, $cookies, $window);
    initDropDowns($scope, $http, $modal, $log, $sce, $cookies, $window);
};


