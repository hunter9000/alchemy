
alchApp.controller('newGridController', function(APIService, $scope, $location){

    $scope.startNewGrid = function() {
        APIService.createNewGrid(function(response) {
            $location.path('/grid/'+response.data.id);
        });
    }

})
