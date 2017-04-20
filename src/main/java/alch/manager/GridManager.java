package alch.manager;

import alch.model.*;
import alch.model.UnitDefinitionType;
import alch.model.user.User;
import alch.security.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class GridManager {

    private static final Logger logger = LoggerFactory.getLogger(GridManager.class);

    private Grid grid;

    public GridManager(Grid grid) {
        this.grid = grid;
    }

    private static final int SECONDS_PER_TICK = 1;

    public static Grid createGrid(User owner) {
        Grid grid = new Grid();

        Map<ResourceType, ResourceInventory> inventory = new HashMap<>();
        for (ResourceType rType : ResourceType.values()) {
            ResourceInventory inv = new ResourceInventory();
            inv.setGrid(grid);
            if (rType == ResourceType.WOOD) {
                inv.setAmount(80l);
            }
            else {
                inv.setAmount(0l);
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
//        units.add(createUnit(grid, UnitDefinitionType.DUAL_TRANSMUTER_WOOD_DIRT_GRASS));
        // stockpiles
        units.add(createUnit(grid, UnitDefinitionType.STOCKPILE_WOOD));
        units.add(createUnit(grid, UnitDefinitionType.STOCKPILE_DIRT));
        units.add(createUnit(grid, UnitDefinitionType.STOCKPILE_GRASS));
        units.add(createUnit(grid, UnitDefinitionType.STOCKPILE_ROCK));
        units.add(createUnit(grid, UnitDefinitionType.STOCKPILE_WATER));

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
        // TODO don't set these here, unit should use the definition only
//        unit.setType(unitDefinitionType.getUnitType());
//        unit.setCostAmount(unitDefinitionType.getCostAmount());
//        unit.setCostResourceType(unitDefinitionType.getCostResourceType());
//        List<UnitConnection> connections = new ArrayList<>();
//        for (UnitConnection conn : unitDefinitionType.getUnitConnections()) {
//
//        }
//        unit.setConnections(unitDefinitionType.getUnitConnections());
//        for (UnitConnection conn : unit.getConnections()) {
//            conn.setUnit(unit);
//        }
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
                if (unit.getCostResourceType() == null) {
                    unit.setCanAfford(true);
                }
                else {
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

    public void updateTicks() {
        // if the last tick is set, figure out how many tick lengths have passed, add that many ticks worth of inventory, set last tick forward by the number of ticks,
        if (grid.getLastTick() != null) {
            this.populateGrid();

            Instant lastTickInstant = grid.getLastTick().toInstant();
            Instant now = Instant.now();

            if (now.isBefore(lastTickInstant)) {
                throw new BadRequestException();
            }

            logger.debug("(grid.getLastTick() {}", grid.getLastTick());

//            LocalDate lastTickLocalDate = LocalDateTime.ofInstant(grid.getLastTick().toInstant(), ZoneId.systemDefault()).toLocalDate();
//            Period span = Period.between(lastTickLocalDate, LocalDate.now());

//            logger.debug("LocalDateTime.now() {}", LocalDateTime.now());
            logger.debug("Instant.now() {}", Instant.now());

//            long secondsBetweenLocalDateTime = ChronoUnit.SECONDS.between(LocalDateTime.ofInstant(grid.getLastTick().toInstant(), ZoneId.systemDefault()), LocalDateTime.now());

            long secondsBetweenInstant = ChronoUnit.SECONDS.between(lastTickInstant, now);

//            logger.debug("secondsBetweenLocalDateTime {}", secondsBetweenLocalDateTime);
            logger.debug("secondsBetweenInstant {}", secondsBetweenInstant);

            long numTicks = (long)(secondsBetweenInstant / (float)SECONDS_PER_TICK);
            long numSecondsPassedInTicks = numTicks * SECONDS_PER_TICK;

            long secondsLeftOverFromTicks = secondsBetweenInstant - numSecondsPassedInTicks;

            Instant newTickInstant = now.minusSeconds(secondsLeftOverFromTicks);

            grid.setLastTick(java.util.Date.from(newTickInstant));

            this.addTicksToInventory(numTicks);



        }
    }

    private void addTicksToInventory(long numTicks) {
        List<ProductionPath> paths = new GridProcessingManager(grid).getPaths();

        // get all the stockpile resource types
        List<ResourceType> typesToCreate = new ArrayList<>();
        for (ProductionPath path : paths) {
            for (Unit u : path.getStockpiles()) {
                for (UnitConnection conn : u.getInputConnections()) {
                    typesToCreate.add(conn.getResourceType());
                }
            }
        }

        // add numTicks number of each resource type
        for (ResourceType type : typesToCreate) {
            grid.getResourceInventory().get(type).addAmount(numTicks);
        }
    }
}
