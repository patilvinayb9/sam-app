
var initiateRefundRequest = function ($scope, $http){

    var msg = "Your account must be at least 3 months old in order to initiate refund request.";
    basicPopup($scope, $http, "Information", msg,"Ok");
    return;

};

