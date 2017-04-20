
alchApp.directive('pipeConnector', function(APIService, GridService, $routeParams, $log) {
	return {
		templateUrl: 'pages/templates/pipe-connector-directive-template.html',
		restrict: 'E',
		scope: {
		    direction: '=',
			cell: '=',
			col: '@',
			row: '@',
		},
        controller:function($scope, $window, $http, $location) {

//            $scope.isCellEmpty = function() {
//                return !$scope.cell.unit && !$scope.cell.pipe1 && !$scope.cell.pipe2;
//            }

//            $scope.unitDrop = function() {
//                $log.debug('dropped unit id: ' + $scope.cell.unit.id);
//                $log.debug('isempty: ' + $scope.isCellEmpty());
//
//                GridService.placeUnit($routeParams.gridId, $scope.cell.unit.id, $scope.row, $scope.col);
//            }


//            $scope.startCallback = function(event, ui, dir) {
//                console.log('You started draggin: ' + dir);
//                $scope.draggedDir = dir;
//            };

            $scope.pipeDrop = function(event, ui) {
                var dragItem = $scope.dndDragItem;
                var dropItem = angular.element(ui.draggable).scope().dndDragItem; //$scope.$parent.dndDropItem;
                $log.debug('dropped pipe from ' + dragItem + ' to ' + dropItem);
                if (dragItem == dropItem) {
                    return;
                }
                GridService.placePipe($routeParams.gridId, $scope.row, $scope.col, dragItem, dropItem);
            }

            $scope.startDragging = function(event, ui, dir) {
                $scope.dragging = true;
                console.log('You started draggin: ' + dir);
                $scope.draggedDir = dir;
            };

            $scope.removePipe = function(pipeDir) {
                if ($scope.dragging) {
                    return;
                }

                if (!$scope.cell) {
                    return;
                }
                if ($scope.cell.pipe1 && ($scope.cell.pipe1.inDirection == pipeDir || $scope.cell.pipe1.outDirection == pipeDir) ) {
                    // delete pipe1
                    GridService.removePipe($routeParams.gridId, $scope.cell.pipe1.id);
                    return;
                }
                if ($scope.cell.pipe2 && ($scope.cell.pipe2.inDirection == pipeDir || $scope.cell.pipe2.outDirection == pipeDir) ) {
                    // delete pipe2
                    GridService.removePipe($routeParams.gridId, $scope.cell.pipe2.id);
                    return;
                }

                $scope.dragging = false;
            };

            $scope.css = function () {
                var dir;
                if ($scope.direction == 'WEST') {
                    dir = 'left';
                }
                if ($scope.direction == 'EAST') {
                    dir = 'right';
                }
                if ($scope.direction == 'NORTH') {
                    dir = 'top';
                }
                if ($scope.direction == 'SOUTH') {
                    dir = 'bottom';
                }

                return 'pipe-connector-' + dir;
            }
        },


    }
});