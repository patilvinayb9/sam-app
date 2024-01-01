
var lang = "en";

var edgeApp = angular.module('edgeApp', ['ngRoute','appControllers']);

edgeApp.config(['$routeProvider', function($routeProvider) {
	  $routeProvider.
	  when('/list', {
	    templateUrl: 'partials/list.html',
	    controller: 'edgeController'
	  }).
      when('/profile/:profileID', {
        templateUrl: 'edgeApp.html',
        controller: 'edgeController'
      }).
      when('/p/:profileID', {
        templateUrl: 'edgeApp.html',
        controller: 'edgeController'
      }).
      when('/r/:refCode', {
        templateUrl: 'edgeApp.html',
        controller: 'edgeController'
      }).
      when('/search/:searchTYPE', {
        templateUrl: 'edgeApp.html',
        controller: 'edgeController'
      }).
      when('/page/:pageID', {
        templateUrl: 'edgeApp.html',
        controller: 'edgeController'
      }).
	  otherwise({
	    redirectTo: '/page/homeView'
	  });
	}]);

var appControllers = angular.module("appControllers", ['ui.bootstrap', 'ui.grid', 'ui.grid.resizeColumns', 'ui.grid.moveColumns', 'ui.grid.selection', 'ngSanitize','ui.multiselect','ngCookies']);

edgeApp.filter('unsafe', function($sce) {
	return function(val) {
		return $sce.trustAsHtml(val);
	};
});

appControllers.controller("edgeController",
		[
			'$scope', '$http', '$modal', '$log', '$sce', '$window', '$location', '$routeParams', '$cookies',
			function($scope, $http, $modal, $log, $sce, $window, $location, $routeParams, $cookies) {

				$scope.es = {};
				$scope.es.gtag_url = "https://marryeazy.com";

				$scope.es.location = $location;
				$scope.es.pageID = $routeParams.pageID;
				$scope.es.searchTYPE = $routeParams.searchTYPE;
                $scope.es.searchId = $routeParams.profileID;

               $scope.es.filteredProfiles = [];
               $scope.es.searchedProfiles = [];
               $scope.es.currentPage = 1;
               $scope.es.numPerPage = 9;
               $scope.es.maxSize = 5;
               $scope.es.appVersion=appVersion;

              $scope.$watch('es.currentPage + es.numPerPage + es.searchedProfiles', function() {

                if($scope.es.searchedProfiles){
                    var begin = (($scope.es.currentPage - 1) * $scope.es.numPerPage)
                                , end = begin + $scope.es.numPerPage;
                    $scope.es.filteredProfiles = $scope.es.searchedProfiles.slice(begin, end);
                    $window.scrollTo(0, 0);
                }else{
                    $scope.es.filteredProfiles = [];
                }

              });

                initLangOverrides($scope, $http, $modal, $log, $sce, $cookies, $window);

				coreInitFun($scope, $http, $modal, $log, $sce, $cookies, $window);
				coreInitVar($scope, $http, $modal, $log, $sce, $cookies, $window);

                appInitMenu($scope, $http, $modal, $log, $sce, $cookies, $window);
				appInitFun($scope, $http, $modal, $log, $sce, $cookies, $window);
				appInitVar($scope, $http, $modal, $log, $sce, $cookies, $window);

				coreOnLoad ($scope, $http, $modal, $log, $sce, $cookies, $window);

				appOnLoad ($scope, $http, $modal, $log, $sce, $routeParams, $cookies);

			}
		]
	);

appControllers.controller('PopupInstanceCtrl', function ($scope, $modalInstance, es, popupId) {

	  $scope.es = es;
	  $scope.es.openedPopups[popupId] = $modalInstance;
});



