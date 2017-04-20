
alchApp.directive('cell', function(APIService, GridService, $routeParams, $log) {
	return {
		templateUrl: 'pages/templates/cell-directive-template.html',
		restrict: 'E',
		scope: {
			cell: '@',
			col: '@',
			row: '@',
		},
        controller:function($scope, $window, $http, $location) {
            $scope.lPipeDir = 'WEST';
            $scope.rPipeDir = 'EAST';
            $scope.tPipeDir = 'NORTH';
            $scope.bPipeDir = 'SOUTH';

            $scope.isCellEmpty = function() {
                return !$scope.cell.unit && !$scope.cell.pipe1 && !$scope.cell.pipe2;
            }

            $scope.unitDrop = function() {
                $log.debug('dropped unit id: ' + $scope.cell.unit.id);
                $log.debug('isempty: ' + $scope.isCellEmpty());

                GridService.placeUnit($routeParams.gridId, $scope.cell.unit.id, $scope.row, $scope.col);
            }

//            $scope.pipeDrop = function() {
//                $log.debug('dropped pipe from ' + $scope.dndDragItem + ' to ' + $scope.dndDropItem);
//                if ($scope.dndDragItem == $scope.dndDropItem) {
//                    return;
//                }
//                GridService.placePipe($routeParams.gridId, $scope.row, $scope.col, $scope.dndDragItem, $scope.dndDropItem);
//            }
//
//            $scope.startDragging = function() {
//                $scope.dragging = true;
//            };
//
//            $scope.removePipe = function(pipeDir) {
//                if ($scope.dragging) {
//                    return;
//                }
//
//                if (!$scope.cell) {
//                    return;
//                }
//                if ($scope.cell.pipe1 && ($scope.cell.pipe1.inDirection == pipeDir || $scope.cell.pipe1.outDirection == pipeDir) ) {
//                    // delete pipe1
//                    GridService.removePipe($routeParams.gridId, $scope.cell.pipe1.id);
//                    return;
//                }
//                if ($scope.cell.pipe2 && ($scope.cell.pipe2.inDirection == pipeDir || $scope.cell.pipe2.outDirection == pipeDir) ) {
//                    // delete pipe2
//                    GridService.removePipe($routeParams.gridId, $scope.cell.pipe2.id);
//                    return;
//                }
//
//                $scope.dragging = false;
//            }
        },


    }
});