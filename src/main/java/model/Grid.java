package model;

import java.util.List;
import java.util.Map;

public class Grid {
    // resource amounts
//    @MapKey("type")
    Map<ResourceType, ResourceInventory> resourceInventory;

    private List<Unit> units;
    private List<Pipe> pipes;

    // entity bucket for purchased, but unused ones?	// no, just clear the x,y


    public Map<ResourceType, ResourceInventory> getResourceInventory() {
        return resourceInventory;
    }

    public void setResourceInventory(Map<ResourceType, ResourceInventory> resourceInventory) {
        this.resourceInventory = resourceInventory;
    }

    public List<Unit> getUnits() {
        return units;
    }

    public void setUnits(List<Unit> units) {
        this.units = units;
    }

    public List<Pipe> getPipes() {
        return pipes;
    }

    public void setPipes(List<Pipe> pipes) {
        this.pipes = pipes;
    }
}
