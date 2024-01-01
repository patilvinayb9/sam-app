
var initLangOverrides = function initLangOverrides($scope, $http, $modal, $log, $sce, $cookies, $window){
    
    $scope.es.appContextRoot="https://marryeazy.com";
    $scope.es.appEAddress="care@marryeazy.com";
    $scope.es.appEscEmailAddress="care@marryeazy.com";
    $scope.es.appWhatsapp="+91-88880-84629";
    $scope.es.appName="MarryEazy";
    $scope.es.appVersion=appVersion;
    $scope.es.appTagline="India's #1 Affordable and Secure Matrimony";
    $scope.es.appDomain="marryeazy.com";

    $scope.es.msg_lowBalance="Your balance is less than minimum balance required to send / accept request. Click here to recharge with at least Rs. ";
    
    initLangMessages($scope, $http, $modal, $log, $sce, $cookies, $window);
    initDropDowns($scope, $http, $modal, $log, $sce, $cookies, $window);
};


