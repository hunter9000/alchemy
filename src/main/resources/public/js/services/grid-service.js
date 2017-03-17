
alchApp.factory('GridService', function($window, $location, $http, $log) {
    return {
        getUnitPurchaseData: function(id) {
            return {
                'id': id
            }
        },
        getUnitPlacementData: function(id, row, col) {
            return {
                'id': id,
                'row': row,
                'col': col,
            }
        }
    }
});