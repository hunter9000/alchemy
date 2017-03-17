
alchApp.directive('cell', function() {
	return {
		templateUrl: 'pages/templates/cell-directive-template.html',
		restrict: 'E',
		scope: {
			cell: '=',
			x: '@',     // col
			y: '@',     // row
			emptyCellClick: '&',
			unitClick: '&',
		},
        controller:function($scope, $window, $http, $location) {
            $scope.isCellEmpty = function() {
                return !$scope.cell.unit && !$scope.cell.pipe1 && !$scope.cell.pipe2;
            }

            $scope.handleUnitClick = function() {
                console.log('clicked unit inside cell ' + $scope.cell.unit.id);

                $scope.unitClick()($scope.cell.unit, $scope.y, $scope.x);
            }
            $scope.pipeClick = function() {
                // nothing yet
            }
            $scope.clickEmpty = function() {
                console.log('cell directive clicked empty cell');
                $scope.emptyCellClick()($scope.y, $scope.x);
            }
        },


    }
});