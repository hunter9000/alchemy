
// broadcasts event "grid.update" when the grid is updated.

alchApp.service('GridService', function(APIService, $rootScope){
    // private functions
    this.getUnitPurchaseData = function(id) {
        return {
            'id': id
        }
    };
    this.getUnitPlacementData = function(id, row, col) {
        return {
            'id': id,
            'row': row,
            'col': col,
        }
    };
    this.broadcastUpdate = function(grid) {
        $rootScope.$broadcast("grid.update", {'grid': grid});
    };


    // public functions
    this.purchaseUnit = function(gridId, unitId) {
        var self = this;
        APIService.purchaseUnit(gridId, unitId, this.getUnitPurchaseData(unitId), function(response) {
            // broadcast update response.data
            //$rootScope.$broadcast("grid.update", {'grid': response.data});
            self.broadcastUpdate(response.data);
        })
    };

    this.unplaceUnit = function(gridId, unitId) {
        var self = this;
        APIService.placeUnit(gridId,
                               unitId,
                               this.getUnitPlacementData(unitId, null, null),
                               function(response) {
//                                    $rootScope.$broadcast("grid.update", {'grid': response.data});
                                    self.broadcastUpdate(response.data);
                               });
    };

    this.placeUnit = function(gridId, unitId, row, col) {
        var self = this;
        APIService.placeUnit(gridId,
                             unitId,
                             this.getUnitPlacementData(unitId, row, col),
                             function(response) {
//                                  $rootScope.$broadcast("grid.update", {'grid': response.data});
                                  self.broadcastUpdate(response.data);
                             });
    };
});
/*
alchApp.factory('GridService', function(APIService, $rootScope) {
    return {
        // private functions
//        broadcastUpdate: function(grid) {
//
//        },
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

        // public functions
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
*/