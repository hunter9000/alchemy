
alchApp.controller('gridController', function(APIService, GridService, $scope, $location, $routeParams) {

    $scope.grid = {};
//    $scope.purchasableUnits = [];

    $scope.loadGrid = function() {
        APIService.getGrid($routeParams.gridId, function(response) {
            $scope.grid = response.data;
        });
//        APIService.getPurchasableUnits($routeParams.gridId, function(response) {
//            $scope.purchasableUnits = response.data;
//        });
    }
    $scope.loadGrid();

    $scope.purchaseUnit = function(unit) {
        APIService.purchaseUnit($routeParams.gridId, unit.id, GridService.getUnitPurchaseData(unit.id), function(response) {
            $scope.loadGrid();
        })
    }

    $scope.delete = function() {
        APIService.deleteGrid($routeParams.gridId, function(response) {
            $location.path('/newgrid');
        });
    }

})