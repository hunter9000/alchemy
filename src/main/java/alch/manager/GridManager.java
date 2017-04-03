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
        units.add(createUnit(grid, UnitType.SOURCE, UnitDefinitionType.SOURCE_WOOD, 10, ResourceType.WOOD, new UnitConnection(ResourceType.WOOD, DirectionType.EAST, false) ));
        units.add(createUnit(grid, UnitType.SOURCE, UnitDefinitionType.SOURCE_DIRT,10, ResourceType.WOOD, new UnitConnection(ResourceType.DIRT, DirectionType.EAST, false) ));
        units.add(createUnit(grid, UnitType.SOURCE, UnitDefinitionType.SOURCE_GRASS,10, ResourceType.WOOD,  new UnitConnection(ResourceType.GRASS, DirectionType.EAST, false) ));
        units.add(createUnit(grid, UnitType.SOURCE, UnitDefinitionType.SOURCE_ROCK,10, ResourceType.WOOD,  new UnitConnection(ResourceType.ROCK, DirectionType.EAST, false) ));
        units.add(createUnit(grid, UnitType.SOURCE, UnitDefinitionType.SOURCE_WATER,10, ResourceType.WOOD,  new UnitConnection(ResourceType.WATER, DirectionType.EAST, false) ));
        // transmuters
        units.add(createUnit(grid, UnitType.TRANSMUTER, UnitDefinitionType.TRANSMUTER_WOOD_DIRT,0, ResourceType.WOOD, new UnitConnection(ResourceType.WOOD, DirectionType.WEST, true),
                                                                                                                                  new UnitConnection(ResourceType.DIRT, DirectionType.EAST, false)));
        // stockpiles
        units.add(createUnit(grid, UnitType.STOCKPILE, UnitDefinitionType.STOCKPILE_WOOD,0, ResourceType.WOOD, new UnitConnection(ResourceType.WOOD, DirectionType.NORTH, true)));
        units.add(createUnit(grid, UnitType.STOCKPILE, UnitDefinitionType.STOCKPILE_DIRT,50, ResourceType.WOOD, new UnitConnection(ResourceType.DIRT, DirectionType.NORTH, true)));
        units.add(createUnit(grid, UnitType.STOCKPILE, UnitDefinitionType.STOCKPILE_GRASS,100, ResourceType.WOOD, new UnitConnection(ResourceType.GRASS, DirectionType.NORTH, true)));

        grid.setUnits(units);

        grid.setOwner(owner);
        grid.setResourceInventory(inventory);
        grid.setWidth(5);
        grid.setHeight(5);

        return grid;
    }

    public static Unit createUnit(Grid grid, UnitType unitType, UnitDefinitionType unitDefinitionType, Integer costAmount, ResourceType costResourceType, UnitConnection... unitConnections) {
        Unit unit = new Unit();
        unit.setGrid(grid);
        unit.setType(unitType);
        unit.setDefinitionType(unitDefinitionType);
        unit.setCostAmount(costAmount);
        unit.setCostResourceType(costResourceType);
//        unit.setResourceInputType(resourceInputType);
//        unit.setResourceOutputType(resourceOutputType);
        unit.setConnections(Arrays.asList(unitConnections));
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
                    throw new RuntimeException("more than 2 pipes");
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
