
alchApp.factory('GridService', function($window, $location, $http, $log) {
    return {
        getUnitPurchaseData: function(id) {
            return {
                'id': id
            }
        },

    }
});