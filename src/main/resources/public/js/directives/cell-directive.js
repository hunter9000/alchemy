
alchApp.directive('cell', function(APIService, GridService, $routeParams) {
	return {
		templateUrl: 'pages/templates/cell-directive-template.html',
		restrict: 'E',
		scope: {
			cell: '=',
			x: '@',     // col
			y: '@',     // row
		},
        controller:function($scope, $window, $http, $location) {
            $scope.isCellEmpty = function() {
                return !$scope.cell.unit && !$scope.cell.pipe1 && !$scope.cell.pipe2;
            }

//            $scope.handleUnitClick = function() {
//
//            }
            $scope.pipeClick = function() {
                // nothing yet
            }
            $scope.clickEmpty = function() {
                console.log('cell directive clicked empty cell');
            }

            $scope.drop = function() {
                console.log('dropped unit id: ' + $scope.cell.unit.id);
                console.log('isempty: ' + $scope.isCellEmpty());

                GridService.placeUnit($routeParams.gridId, $scope.cell.unit.id, $scope.y, $scope.x;
            }

//            $scope.deleteUnit = function() {
//
//            }
        },


    }
});