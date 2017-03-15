package manager;

import model.Grid;
import model.ResourceInventory;
import model.ResourceType;

import java.util.HashMap;
import java.util.Map;

public class GridManager {

    public static Grid createGrid() {
        Grid grid = new Grid();

        Map<ResourceType, ResourceInventory> inventory = new HashMap<>();
        for (ResourceType rType : ResourceType.values()) {
            ResourceInventory inv = new ResourceInventory();
            inv.setAmount(0);
            inv.setType(rType);
            inventory.put(rType, inv);
        }

        grid.setResourceInventory(inventory);

        return grid;
    }

}
