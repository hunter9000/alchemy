
alchApp.controller('gridController', function(APIService, GridService, $scope, $location, $routeParams) {

    $scope.grid = {};

    $scope.loadGrid = function() {
    //https://github.com/rclayton/NG-Communicate-Ctrls/blob/master/service/index.html

        APIService.getGrid($routeParams.gridId, function(response) {
            $scope.grid = response.data;
        });
    }
    $scope.loadGrid();

//    $scope.$on() ;
    $scope.$on("grid.updated", function(e, kvp){
        $scope.grid = kvp.grid;
    });

//
//    $scope.purchaseUnit = function(unit) {
//        APIService.purchaseUnit($routeParams.gridId, unit.id, GridService.getUnitPurchaseData(unit.id), function(response) {
//            $scope.loadGrid();
//        })
//    }

    $scope.delete = function() {
        APIService.deleteGrid($routeParams.gridId, function(response) {
            $location.path('/newgrid');
        });
    }

})