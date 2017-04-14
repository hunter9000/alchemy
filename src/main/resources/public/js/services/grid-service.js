
alchApp.factory('GridService', function(APIService, $rootScope) {

    var getUnitPurchaseData = function(id) {
        return {
            'id': id
        }
    };
    var getUnitPlacementData = function(id, row, col) {
        return {
            'id': id,
            'row': row,
            'col': col,
        }
    };
    var getPipePlacementData = function(row, col, dir1, dir2) {
        return {
            'row': row,
            'col': col,
            'dir1': dir1,
            'dir2': dir2,
        }
    };
    var broadcastGridUpdateCallback = function() {
        return function(response) {
            $rootScope.$broadcast('grid.update', {'grid': response.data});
        }
    };
//    var broadcastErrorsCallback = function() {
//        return function(response) {
//            $rootScope.$broadcast('grid.errors', {'grid': response.data.grid, 'errors': response.data.cellErrors});
//        }
//    };

    return {
        purchaseUnit: function(gridId, unitId) {
            APIService.purchaseUnit(gridId, unitId, getUnitPurchaseData(unitId), broadcastGridUpdateCallback());
        },

        unplaceUnit: function(gridId, unitId) {
            APIService.placeUnit(gridId,
                                   unitId,
                                   getUnitPlacementData(unitId, null, null),
                                   broadcastGridUpdateCallback());
        },

        placeUnit: function(gridId, unitId, row, col) {
            APIService.placeUnit(gridId,
                                 unitId,
                                 getUnitPlacementData(unitId, row, col),
                                 broadcastGridUpdateCallback());
        },

        placePipe: function(gridId, row, col, dir1, dir2) {
            getPipePlacementData(row, col, dir1, dir2);

            APIService.placePipe(gridId, getPipePlacementData(row, col, dir1, dir2), broadcastGridUpdateCallback());
        },

        removePipe: function(gridId, pipeId) {
            APIService.removePipe(gridId, pipeId, broadcastGridUpdateCallback());
        },

    }
});