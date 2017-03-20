
alchApp.directive('unit', function(APIService, GridService, $routeParams) {
	return {
		templateUrl: 'pages/templates/unit-directive-template.html',
		restrict: 'E',
		scope: {
			unit: '=',
			click: '&',
		},
        controller:function($scope, $window, $http, $location) {
            $scope.handleClick = function() {
//                if (!$scope.unit.purchased) {
//                    if ($scope.unit.canAfford) {
//                        $scope.click()($scope.unit);
//                    }
//                }
//                else {
//                    $scope.click()($scope.unit);
//                }
            }

            $scope.unplace = function() {
                console.log('unplacing unit ' + $scope.unit.id);

                APIService.placeUnit($routeParams.gridId,
                                     $scope.unit.id,
                                     GridService.getUnitPlacementData($scope.unit.id, null, null),
                                     function(response) {
//                                         $scope.grid = response.data;
                                     });

            }
        },
    }
});