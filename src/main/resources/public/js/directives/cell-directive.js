
alchApp.directive('cell', function() {
	return {
		template:
			`<div style="display:inline-block; width:50px; height:50px; border: 1px solid black;" >
			    <span ng-show="cell.unit">unit</span>
			    <span ng-show="cell.pipe1">pipe1</span>
			    <span ng-show="cell.pipe2">pipe2</span>
                <span ng-show="!cell.unit && !cell.pipe1 && !cell.pipe2">empty</span>
			</div>`,
		restrict: 'E',
		scope: {
			cell: '=',
		},
//		controller: function firstDirectiveController($scope) {
//            if ($scope.initialCollapsed == undefined) {
//                $scope.initialCollapsed = false;
//            }
//            return $scope;
//		}
	}
});