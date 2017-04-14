
alchApp.controller('gridController', function(APIService, GridService, $scope, $location, $routeParams) {

    $scope.grid = {};
    $scope.droppedUnit = null;

    $scope.loadGrid = function() {
        //https://github.com/rclayton/NG-Communicate-Ctrls/blob/master/service/index.html

        APIService.getGrid($routeParams.gridId, function(response) {
            $scope.grid = response.data;
        });
    }
    $scope.loadGrid();

    $scope.$on("grid.update", function(e, kvp){
        $scope.grid = kvp.grid;
    });

    /** unit has been dragged from the grid to here, unplace it. */
    $scope.drop = function() {
        console.log($scope.droppedUnit);
        GridService.unplaceUnit($routeParams.gridId, $scope.droppedUnit.id);
    }

    $scope.delete = function() {
        APIService.deleteGrid($routeParams.gridId, function(response) {
            $location.path('/newgrid');
        });
    }

    $scope.start = function() {
        APIService.startGrid($routeParams.gridId, function(response) {
            $scope.grid = response.data;
        });
    }

    $scope.stop = function() {
        APIService.stopGrid($routeParams.gridId, function(response) {
            $scope.grid = response.data;
        });
    }

})