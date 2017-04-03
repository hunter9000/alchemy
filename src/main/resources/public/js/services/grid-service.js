
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
    }

    return {
//        getUnitPurchaseData: function(id) {
//            return {
//                'id': id
//            }
//        },
//        getUnitPlacementData: function(id, row, col) {
//            return {
//                'id': id,
//                'row': row,
//                'col': col,
//            }
//        },

        purchaseUnit: function(gridId, unitId) {
            APIService.purchaseUnit(gridId, unitId, getUnitPurchaseData(unitId), function(response) {
                // broadcast update response.data
                $rootScope.$broadcast("grid.update", {'grid': response.data});
            })
        },

        unplaceUnit: function(gridId, unitId) {
            APIService.placeUnit(gridId,
                                   unitId,
                                   getUnitPlacementData(unitId, null, null),
                                   function(response) {
                                      $rootScope.$broadcast("grid.update", {'grid': response.data});
                                   });
        },

        placeUnit: function(gridId, unitId, row, col) {
            APIService.placeUnit(gridId,
                                 unitId,
                                 getUnitPlacementData(unitId, row, col),
                                 function(response) {
                                    $rootScope.$broadcast("grid.update", {'grid': response.data});
                                 });
        },

        placePipe: function(gridId, row, col, dir1, dir2) {
            getPipePlacementData(row, col, dir1, dir2);

            APIService.placePipe(gridId,
                                 getPipePlacementData(row, col, dir1, dir2),
                                 function(response) {
                                    $rootScope.$broadcast("grid.update", {'grid': response.data});
                                 });
        },
    }
});