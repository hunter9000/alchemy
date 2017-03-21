package alch.manager;

import alch.model.*;
import alch.model.user.User;
import com.sun.corba.se.impl.orbutil.graph.Graph;

import java.util.*;

public class GridManager {

    private Grid grid;

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
        units.add(createUnit(grid, UnitType.SOURCE, null, ResourceType.WOOD, 10, ResourceType.WOOD));
        units.add(createUnit(grid, UnitType.SOURCE, null, ResourceType.DIRT, 10, ResourceType.WOOD));
        units.add(createUnit(grid, UnitType.SOURCE, null, ResourceType.GRASS, 10, ResourceType.WOOD));
        units.add(createUnit(grid, UnitType.SOURCE, null, ResourceType.ROCK, 10, ResourceType.WOOD));
        units.add(createUnit(grid, UnitType.SOURCE, null, ResourceType.WATER, 10, ResourceType.WOOD));
        // transmuters
        units.add(createUnit(grid, UnitType.TRANSMUTER, ResourceType.WOOD, ResourceType.DIRT, 0, ResourceType.WOOD));
        // stockpiles
        units.add(createUnit(grid, UnitType.STOCKPILE, null, null, 0, ResourceType.WOOD));
        units.add(createUnit(grid, UnitType.STOCKPILE, null, null, 50, ResourceType.WOOD));
        units.add(createUnit(grid, UnitType.STOCKPILE, null, null, 100, ResourceType.WOOD));

        grid.setUnits(units);

        grid.setOwner(owner);
        grid.setResourceInventory(inventory);
        grid.setWidth(5);
        grid.setHeight(5);

        return grid;
    }

    public static Unit createUnit(Grid grid, UnitType unitType, ResourceType resourceInputType, ResourceType resourceOutputType, Integer costAmount, ResourceType costResourceType) {
        Unit unit = new Unit();
        unit.setGrid(grid);
        unit.setType(unitType);
        unit.setCostAmount(costAmount);
        unit.setCostResourceType(costResourceType);
        unit.setResourceInputType(resourceInputType);
        unit.setResourceOutputType(resourceOutputType);
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
                cells[unit.getY()][unit.getX()].unit = unit;
            }
        }
        for (Pipe pipe : grid.getPipes()) {
            if (pipe.isPlaced()) {
                int x = pipe.getX();
                int y = pipe.getY();
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

    public List<ProductionPath> getPaths() {
        Set<Unit> allVisitedUnits = new HashSet<>();

        // for each SOURCE in units
        for (Unit source : grid.getUnits()) {
            if (source.getType() == UnitType.SOURCE && !allVisitedUnits.contains(source)) {   // if it's not in allVisitedUnits
                // create Set<Unit> for this graph
                Set<Unit> graphVisitedUnits = new HashSet<>();
                // add this source to the set
                graphVisitedUnits.add(source);

                ProductionPath path = new ProductionPath();
                visitNextUnit(source, source.getConnections().get(0).getDirectionType(), path, graphVisitedUnits);

                // do something useful with path here

                allVisitedUnits.addAll(graphVisitedUnits);
            }

        }


        return null;
    }


    private ProductionPath visitNextUnit(Unit currUnit, DirectionType entryDir, ProductionPath path, Set<Unit> graphVisitedUnits) {
        // for each output
        for (UnitConnection connection : currUnit.getConnections()) {
            if (connection.getDirectionType().opposite() == entryDir) {
                continue;       // don't go back the way we came
            }

            CellConnectionInfo neighborCellInfo = getConnectedNeighborCell(currUnit.getY(), currUnit.getX(), connection.getDirectionType());
            if (neighborCellInfo == null) {
                throw new RuntimeException("no connected neighbor found");
            }


            // neighborCell should be a unit now
            Cell neighborCell = neighborCellInfo.cell;
            if (neighborCell.isUnit()) {
                Unit u = neighborCell.unit;
                // add the unit to graphVisitedUnits
                graphVisitedUnits.add(u);

                // check that the connection is valid
                    // does the output of currUnit from the entryDir match the input of the u unit at neighborCellInfo.entryDir?

                // if valid,
                    // add this unit to the path

                // recurse
                return visitNextUnit(u, neighborCellInfo.entryDirection, path, graphVisitedUnits);
            }
        }

        return null;

        // add graphVisitedUnits to allVisitedUnits

    }

    private class CellConnectionInfo {
        Cell cell;
        DirectionType entryDirection;

    }

    private CellConnectionInfo getConnectedNeighborCell(Integer row, Integer col, DirectionType entryDir) {
        if (entryDir == DirectionType.E) {
            col++;
        }
        if (entryDir == DirectionType.N) {
            row++;
        }
        if (entryDir == DirectionType.S) {
            row--;
        }
        if (entryDir == DirectionType.W) {
            col--;
        }
        if (!isInBounds(row, col)) {
            return null;
        }

        Cell neighborCell = grid.getCells()[row][col];
        if (neighborCell.isUnit()) {
            CellConnectionInfo ret = new CellConnectionInfo();
            ret.cell = neighborCell;
            ret.entryDirection = entryDir;
            return ret;
        }
        else if (neighborCell.isPipe()) {
            DirectionType leavingDirection = null;
            if (neighborCell.pipe1 != null && neighborCell.pipe1.outputDirectionFromInputDirection(entryDir) != null) {
                leavingDirection = neighborCell.pipe1.outputDirectionFromInputDirection(entryDir);
            }
            else if (neighborCell.pipe2 != null && neighborCell.pipe2.outputDirectionFromInputDirection(entryDir) != null) {
                leavingDirection = neighborCell.pipe2.outputDirectionFromInputDirection(entryDir);
            }
//                neighborCell = getConnectedNeighborCell(neighborCell.pipe, );
            if (leavingDirection == null) {
                return null;
            }
            return getConnectedNeighborCell(row, col, leavingDirection);
        }
        else {
            return null;
        }
    }

    private boolean isInBounds(Integer row, Integer col) {
        return row >= 0 && col >= 0 && row < grid.getHeight() && col < grid.getWidth();
    }

}
