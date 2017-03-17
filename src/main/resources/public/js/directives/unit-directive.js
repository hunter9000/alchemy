
alchApp.directive('unit', function() {
	return {
		templateUrl: 'pages/templates/unit-directive-template.html',
		restrict: 'E',
		scope: {
			unit: '=',
			click: '&',
		},
        controller:function($scope, $window, $http, $location) {
            $scope.handleClick = function() {
                if (!$scope.unit.purchased) {
                    if ($scope.unit.canAfford) {
                        $scope.click()($scope.unit);
                    }
                }
                else {
                    $scope.click()($scope.unit);
                }
            }
        },
    }
});