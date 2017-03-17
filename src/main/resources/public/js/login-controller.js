
// create the controller and inject Angular's $scope
alchApp.controller('loginController', function($scope, $location, $http, $window) {
    // create a message to display in our view
    $scope.message = '';

    $scope.formData = {};

    // when submitting the add form, send the text to the node API
    $scope.login = function() {
        APIService.authenticate($scope.formData, function(response) {
            $window.localStorage['jwtToken'] = response.data.response;
            $location.path("/sheetselect");
        });
    };
});