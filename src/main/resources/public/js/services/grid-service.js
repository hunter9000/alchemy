
alchApp.factory('GridService', function(APIService, $rootScope) {
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
        },

        purchaseUnit: function(gridId, unitId) {
            APIService.purchaseUnit(gridId, unitId, this.getUnitPurchaseData(unitId), function(response) {
                // broadcast update response.data
                $rootScope.$broadcast("grid.update", {'grid': response.data});
            })
        },

        unplaceUnit: function(gridId, unitId) {
            APIService.placeUnit(gridId,
                                   unitId,
                                   this.getUnitPlacementData(unitId, null, null),
                                   function(response) {
                                      $rootScope.$broadcast("grid.update", {'grid': response.data});
                                   });
        },

        placeUnit: function(gridId, unitId, row, col) {
            APIService.placeUnit(gridId,
                                 unitId,
                                 this.getUnitPlacementData(unitId, row, col),
                                 function(response) {
                                    $rootScope.$broadcast("grid.update", {'grid': response.data});
                                 });
        }
    }
});