package alch.manager;

import alch.model.*;
import alch.model.user.UnitDefinitionType;
import alch.model.user.User;

import java.util.*;

public class GridManager {

    private Grid grid;

    public GridManager(Grid grid) {
        this.grid = grid;
    }

    public static Grid createGrid(User owner) {
        Grid grid = new Grid();

        Map<ResourceType, ResourceInventory> inventory = new HashMap<>();
        for (ResourceType rType : ResourceType.values()) {
            ResourceInventory inv = new ResourceInventory();
            inv.setGrid(grid);
            if (rType == ResourceType.WOOD) {
                inv.setAmount(80);
            }
            else {
                inv.setAmount(0);
            }
            inv.setType(rType);
            inventory.put(rType, inv);
        }

        Set<Unit> units = new HashSet<>();
        // sources
        units.add(createUnit(grid, UnitDefinitionType.SOURCE_WOOD ));
        units.add(createUnit(grid, UnitDefinitionType.SOURCE_DIRT ));
        units.add(createUnit(grid, UnitDefinitionType.SOURCE_GRASS ));
        units.add(createUnit(grid, UnitDefinitionType.SOURCE_ROCK ));
        units.add(createUnit(grid, UnitDefinitionType.SOURCE_WATER ));
        // transmuters
        units.add(createUnit(grid, UnitDefinitionType.TRANSMUTER_WOOD_DIRT));
        // dual transumters
        units.add(createUnit(grid, UnitDefinitionType.DUAL_TRANSMUTER_WOOD_DIRT_GRASS));
        // stockpiles
        units.add(createUnit(grid, UnitDefinitionType.STOCKPILE_WOOD));
        units.add(createUnit(grid, UnitDefinitionType.STOCKPILE_DIRT));
        units.add(createUnit(grid, UnitDefinitionType.STOCKPILE_GRASS));

        grid.setUnits(units);

        grid.setOwner(owner);
        grid.setResourceInventory(inventory);
        grid.setWidth(5);
        grid.setHeight(5);

        return grid;
    }

    public static Unit createUnit(Grid grid, UnitDefinitionType unitDefinitionType) {
        Unit unit = new Unit();
        unit.setGrid(grid);
        unit.setDefinitionType(unitDefinitionType);
        unit.setType(unitDefinitionType.getUnitType());
        unit.setCostAmount(unitDefinitionType.getCostAmount());
        unit.setCostResourceType(unitDefinitionType.getCostResourceType());
        unit.setConnections(unitDefinitionType.getUnitConnections());
        return unit;
    }

    public void populateGrid() {
        // populate the Cell array
        Cell[][] cells = new Cell[grid.getHeight()][grid.getWidth()];
        for (int i=0; i<grid.getHeight(); i++) {
            for (int j=0; j<grid.getWidth(); j++) {
                cells[i][j] = new Cell();
            }
        }

        for (Unit unit : grid.getUnits()) {
            if (unit.isPlaced()) {
                cells[unit.getRow()][unit.getCol()].unit = unit;
            }
        }
        for (Pipe pipe : grid.getPipes()) {
            if (pipe.isPlaced()) {
                int x = pipe.getCol();
                int y = pipe.getRow();
                if (cells[y][x].pipe1 == null) {
                    cells[y][x].pipe1 = pipe;
                } else if (cells[y][x].pipe2 == null) {
                    cells[y][x].pipe2 = pipe;
                } else {

//                    throw new RuntimeException("more than 2 pipes");
                }
            }
        }

        grid.setCells(cells);

        // populate the purchased/purchasable unit lists
        Set<Unit> purchased = new HashSet<>();
        Set<Unit> purchasable = new HashSet<>();
        for (Unit unit : grid.getUnits()) {
            if (unit.getPurchased()) {
                purchased.add(unit);
            }
            else {
                // now check if the player can afford, or sneak peek at unit
                ResourceInventory inventory = grid.getResourceInventory().get(unit.getCostResourceType());
                // if player has 75% of the cost, show it in the list
                if (inventory.getAmount() >= unit.getCostAmount() * .75) {
                    // if player has the amount necessary, set can afford to true
                    if (inventory.getAmount() >= unit.getCostAmount()) {
                        unit.setCanAfford(true);
                    }
                    purchasable.add(unit);
                }
            }
        }

        grid.setPurchasedUnits(purchased);
        grid.setPurchasableUnits(purchasable);

//        return grid;
    }

    public Unit getUnit(Long unitId) {
        for (Unit unit : grid.getUnits()) {
            if (unit.getId().equals(unitId)) {
                return unit;
            }
        }
        return null;
    }



}
