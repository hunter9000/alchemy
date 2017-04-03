
alchApp.directive('cell', function(APIService, GridService, $routeParams, $log) {
	return {
		templateUrl: 'pages/templates/cell-directive-template.html',
		restrict: 'E',
		scope: {
			cell: '=',
			col: '@',     // col (x)
			row: '@',     // row (y)


		},
        controller:function($scope, $window, $http, $location) {
            $scope.lPipeDir = 'WEST';
            $scope.rPipeDir = 'EAST';
            $scope.tPipeDir = 'NORTH';
            $scope.bPipeDir = 'SOUTH';

            $scope.isCellEmpty = function() {
                return !$scope.cell.unit && !$scope.cell.pipe1 && !$scope.cell.pipe2;
            }

//            $scope.pipeClick = function() {
//                // nothing yet
//            }
//            $scope.clickEmpty = function() {
//                console.log('cell directive clicked empty cell');
//            }

            $scope.unitDrop = function() {
                console.log('dropped unit id: ' + $scope.cell.unit.id);
                console.log('isempty: ' + $scope.isCellEmpty());

                GridService.placeUnit($routeParams.gridId, $scope.cell.unit.id, $scope.row, $scope.col);
            }

//            $scope.setupPipes = function(pipe) {
//                if (pipe) {
//                    if (pipe.inDirection == 'WEST') {
//                        $scope.cell.lPipe = pipe;
//                    }
//                    if (pipe.inDirection == 'EAST') {
//                        $scope.cell.rPipe = pipe;
//                    }
//                    if (pipe.inDirection == 'NORTH') {
//                        $scope.cell.tPipe = pipe;
//                    }
//                    if (pipe.inDirection == 'SOUTH') {
//                        $scope.cell.bPipe = pipe;
//                    }
//                }

//            }
//            $scope.setupPipes($scope.cell.pipe1);
//            $scope.setupPipes($scope.cell.pipe2);

            $scope.pipeDrop = function() {
                $log.debug('dropped pipe from ' + $scope.dndDragItem + ' to ' + $scope.dndDropItem);
                GridService.placePipe($routeParams.gridId, $scope.row, $scope.col, $scope.dndDragItem, $scope.dndDropItem);
            }

        },


    }
});