
alchApp.directive('cell', function(APIService, GridService, $routeParams) {
	return {
		templateUrl: 'pages/templates/cell-directive-template.html',
		restrict: 'E',
		scope: {
			cell: '=',
			x: '@',     // col
			y: '@',     // row
//			emptyCellClick: '&',
//			unitClick: '&',
//			unitDropped: '&',
		},
        controller:function($scope, $window, $http, $location) {
            $scope.isCellEmpty = function() {
                return !$scope.cell.unit && !$scope.cell.pipe1 && !$scope.cell.pipe2;
            }

            $scope.handleUnitClick = function() {
//                console.log('clicked unit inside cell ' + $scope.cell.unit.id);
//
//                $scope.unitClick()($scope.cell.unit, $scope.y, $scope.x);
            }
            $scope.pipeClick = function() {
                // nothing yet
            }
            $scope.clickEmpty = function() {
                console.log('cell directive clicked empty cell');
//                $scope.emptyCellClick()($scope.y, $scope.x);
            }

            $scope.drop = function() {
                console.log('dropped unit id: ' + $scope.cell.unit.id);
                console.log('isempty: ' + $scope.isCellEmpty());

//                $scope.unitDropped()($scope.cell.unit, $scope.y, $scope.x);

                APIService.placeUnit($routeParams.gridId,
                                     $scope.cell.unit.id,
                                     GridService.getUnitPlacementData($scope.cell.unit.id, $scope.y, $scope.x),
                                     function(response) {
//                                         $scope.grid = response.data;
//                                         $scope.selectedPlaceableUnit = null;
                                     });

            }

            $scope.deleteUnit = function() {
//                            APIService.placeUnit($routeParams.gridId,
//                                                 unit.id,
//                                                 GridService.getUnitPlacementData(unit.id, null, null),
//                                                 function(response) {
//                                                     $scope.grid = response.data;
//                                                 });
            }
        },


    }
});