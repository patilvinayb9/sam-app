
var appInitVar = function ($scope, $http, $modal, $log, $sce, $cookies, $window){

	if (isMobile()) {
		// Mobile - On Launch Do not show Menu 
		$scope.es.showMenu=false;
	}else{
		$scope.es.showMenu=true;
	}
	
	//$scope.es.setSelectedView("homeView");
	$scope.es.showSearch = 'C';
	$scope.es.searchedProfiles = {};
	/*$scope.es.editProfile = {};
	$scope.es.secureProfile = {};*/
	
	$scope.es.search = {};
	$scope.es.search.type="None";
	
	//setTimeout(function() {$scope.es.setSelectedView("homeView"); }, 3000);
}


var appOnLoad = function($scope, $http, $modal, $log, $sce, $routeParams, $cookies){

	$scope.es.loadUnreadNotifications();

	$scope.es.getLoggedInProfile();

	// https://localhost:8443/#/p/FETWP1WO
	// https://localhost:8443/#/p/FETWP1WO

	if($scope.es.searchId){
	    $scope.es.wallHeader = $scope.es.lg.SearchById + " - " + $scope.es.searchId;
		$scope.es.openFullProfileById($scope.es.searchId);
		$scope.es.setSelectedInternal("homeView");
	}

	// Handle Referral Code

    if( $routeParams.refCode){
        //if(!$cookies.refCodeCookie){
            $cookies.refCodeCookie = $routeParams.refCode;
        //}
        $scope.es.setSelectedInternal("registerView");
    }

	$scope.es.refCodeCookie = $cookies.refCodeCookie;

	$scope.es.homeSlides = {};
	$scope.es.homeSlides.title = "Home Page";
	$scope.es.homeSlides.interval = 4000;
	$scope.es.homeSlides.slides = [
    		{
    			htmlTemplate: "/modules/home/partial_accepted.html"
    	    },
    	    {
    	    	htmlTemplate: "/modules/home/partial_lifetime.html"
    	    },
    	    {
    	    	htmlTemplate: "/modules/home/partial_contacts.html"
    		},
    	    {
    	    	htmlTemplate: "/modules/home/partial_easy.html"
    	    }
    	  ];

	$scope.es.homeSlides.slidesOld = [
		{
			image: "modules/home/Email1.png"
	    },
	    {
	    	image: "modules/home/300.png"
	    },
	    {
	    	image: "modules/home/Contact.png"
		},
	    {
	    	image: "modules/home/Lifetime.png"
	    }
	  ];
	
};
