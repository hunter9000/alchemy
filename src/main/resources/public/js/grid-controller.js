
alchApp.controller('gridController', function(APIService, GridService, $scope, $location, $routeParams) {

    $scope.grid = {};
//    $scope.purchasableUnits = [];
//    $scope.selectedPlaceableUnit = null;

//  $scope.list1 = {title: 'AngularJS - Drag Me'};
//  $scope.list2 = {};

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

//    $scope.placeUnit = function(unit) {
//        console.log('placing unit ' + unit.id);
////        $scope.selectedPlaceableUnit = unit;
//    }

//    $scope.emptyCellClick = function(row, col) {
//        console.log('clicked cell: row ' + row + ' col ' + col);
//        // if a placable unit is selected, make call to place the unit in this row,col
//        if ($scope.selectedPlaceableUnit) {
////            APIService.placeUnit($routeParams.gridId,
////                                 $scope.selectedPlaceableUnit.id,
////                                 GridService.getUnitPlacementData($scope.selectedPlaceableUnit.id, row, col),
////                                 function(response) {
////                                     $scope.grid = response.data;
////                                     $scope.selectedPlaceableUnit = null;
////                                 });
//        }
//    }

//    $scope.unitClick = function(unit, row, col) {
//        console.log('grid controller unit click: ' + unit.id + ' row ' + row + ' col ' + col);
//        // make call to remove this unit from the cell
//        APIService.placeUnit($routeParams.gridId,
//                             unit.id,
//                             GridService.getUnitPlacementData(unit.id, null, null),
//                             function(response) {
//                                 $scope.grid = response.data;
//                             });
//    }
//    $scope.unitDropped = function(unit, row, col) {
//        console.log('grid controller unit dropped: ' + unit.id + ' row ' + row + ' col ' + col);
//
////        APIService.placeUnit($routeParams.gridId,
////                             unit.id,
////                             GridService.getUnitPlacementData(unit.id, null, null),
////                             function(response) {
////                                 $scope.grid = response.data;
////                             });
//    }

    $scope.delete = function() {
        APIService.deleteGrid($routeParams.gridId, function(response) {
            $location.path('/newgrid');
        });
    }

})