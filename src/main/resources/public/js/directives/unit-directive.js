
alchApp.directive('unit', function(APIService, GridService, $routeParams) {
	return {
		templateUrl: 'pages/templates/unit-directive-template.html',
		restrict: 'E',
		scope: {
			unit: '=',
			click: '&',
		},
        controller:function($scope, $window, $http, $location) {
            $scope.draggable = function() {
                return $scope.unit && $scope.unit.purchased;
            }

            $scope.handleClick = function() {
                if ($scope.unit && !$scope.unit.purchased) {
                    if ($scope.unit.canAfford) {
//                        $scope.click()($scope.unit);
                        // talk to the grid service, tell it to purchase
                        GridService.purchaseUnit($routeParams.gridId, $scope.unit.id);
                    }
                }
//                else {
//                    $scope.click()($scope.unit);
//                }
            }

            $scope.unplace = function() {
                if ($scope.unit) {
                    console.log('unplacing unit ' + $scope.unit.id);

                    GridService.unplaceUnit($routeParams.gridId, $scope.unit.id);
                }
            }
        },
    }
});