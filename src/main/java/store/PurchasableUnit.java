package store;

import model.ResourceType;
import model.UnitType;

public class PurchasableUnit {
    // the unit to create
    UnitType unitType;
    ResourceType resourceOutputType;  // optional, for if this is a source or transmuter
    ResourceType resourceInputType;  // optional, for if this is a transmuter

    // cost
    Integer costAmount;
    ResourceType costResourceType;

    public PurchasableUnit(UnitType unitType, ResourceType resourceOutputType, ResourceType resourceInputType, Integer costAmount, ResourceType costResourceType) {
        this.unitType = unitType;
        this.resourceOutputType = resourceOutputType;
        this.resourceInputType = resourceInputType;
        this.costAmount = costAmount;
        this.costResourceType = costResourceType;
    }
}
