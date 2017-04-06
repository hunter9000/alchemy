package alch.manager;

import alch.model.*;
import org.apache.log4j.Logger;

import java.util.*;

public class GridProcessingManager {

    private Logger logger = Logger.getLogger(this.getClass());

    private Grid grid;
    private List<ProductionPath> paths;

    private List<Cell> errorCells;
    private Map<Unit, ProductionPath> unitPathMembership;

    public GridProcessingManager(Grid grid) {
        this.grid = grid;
        this.paths = new ArrayList<>();

        this.errorCells = new ArrayList<>();
        this.unitPathMembership = new HashMap<>();
    }

    /** Finds all the well connected paths through the grid.
     *  @returns null if the grid doesn't have only well connected paths that include all placed units. */
    public List<ProductionPath> getPaths() {
        List<ProductionPath> paths = new ArrayList<>();

        Set<Unit> allVisitedUnits = new HashSet<>();

        // for each SOURCE in units
        for (Unit source : grid.getPlacedUnits()) {
            if (source.getType() == UnitType.SOURCE && !allVisitedUnits.contains(source)) {   // if it's not in allVisitedUnits
                // create Set<Unit> for this graph
                Set<Unit> graphVisitedUnits = new HashSet<>();

                ProductionPath path = new ProductionPath();
                path.addSource(source);

                // we know there's only one connection, and it's an output
                visitNextUnit(source, null, path, graphVisitedUnits);

                // add the path to the list, and all the units it visited to the set of all the units that have been visited
                paths.add(path);
                allVisitedUnits.addAll(graphVisitedUnits);
            }
        }

        // all sources have been processed
        // make sure that all units have been visited
        if (allVisitedUnits.size() != grid.getPlacedUnits().size()) {
            logger.debug("not all units are connected");
            return null;
        }

        // make sure that all transmuters have all their inputs supplied
        for (ProductionPath path : paths) {
            if (!path.isValid()) {
                logger.debug("path is invalid");
                return null;
            }
        }

        return paths;
    }

    /** Takes the next unit to process, and the direction it was entered from, if any. Adds it to the path and graphVisitedUnits if it's properly connected. */
    private ProductionPath visitNextUnit(Unit currUnit, DirectionType entryDir, ProductionPath path, Set<Unit> graphVisitedUnits) {
        if (graphVisitedUnits.contains(currUnit)) {
            // error, this contains a cycle
            return path;
        }
        graphVisitedUnits.add(currUnit);

        // for each output
        for (UnitConnection outputConnection : currUnit.getOutputConnections()) {
            if (outputConnection.getDirectionType().opposite() == entryDir) {
                continue;       // don't go back the way we came
            }

            // find the unit that this output is connected to
            UnitConnectionInfo neighborUnitInfo = getConnectedNeighborCell(currUnit.getRow(), currUnit.getCol(), outputConnection.getDirectionType());
            if (neighborUnitInfo == null) {
//                throw new RuntimeException("no connected neighbor found");
                return path;
            }

            // neighborCell should be a unit now
//            Cell neighborCell = neighborUnitInfo.cell;
//            if (neighborCell.isUnit()) {
            Unit nextUnit = neighborUnitInfo.unit;
            UnitConnection inputConnection = neighborUnitInfo.getInputUnitConnection();      // this is the outputConnection that

            // check that the outputConnection is valid
            // does the output of currUnit from the entryDir match the input of the u unit at neighborUnitInfo.entryDir?
            if (outputConnection.getResourceType() != inputConnection.getResourceType()) {
                // add to error list and return null;
                return null;
            }

            // if valid,
            // check if this unit is already part of another path, if so, join this path with that one and go back
            ProductionPath membershipPath = unitPathMembership.get(nextUnit);
            if (membershipPath == null) {
                // if not part of another path, add this unit to visited units, and add it to the path
                unitPathMembership.put(nextUnit, path);
                // add the unit to graphVisitedUnits
//                graphVisitedUnits.add(nextUnit);

                path.linkUnit(currUnit, nextUnit);

                // recurse
                return visitNextUnit(nextUnit, neighborUnitInfo.entryDirection, path, graphVisitedUnits);
            }
            else if (membershipPath != path) {
                // this belongs to another path already, need to join them
                // add the existing path to the current path, and get rid of the existing one
                path.addPath(membershipPath);
                // readd all the nodes to the unitPathMembership map so they all point to the new map
                for (Unit u : path.getAllUnits()) {
                    unitPathMembership.put(u, path);
                }
                // remove the old path from the list of paths
                paths.remove(membershipPath);
            }
            else if (membershipPath == path) {
                // this is a cycle, not allowed, add error
            }
        }

        return null;

        // add graphVisitedUnits to allVisitedUnits

    }

    private UnitConnectionInfo getConnectedNeighborCell(Integer row, Integer col, DirectionType entryDir) {
        if (entryDir == DirectionType.EAST) {
            col++;
        }
        if (entryDir == DirectionType.NORTH) {
            row--;
        }
        if (entryDir == DirectionType.SOUTH) {
            row++;
        }
        if (entryDir == DirectionType.WEST) {
            col--;
        }
        if (!isInBounds(row, col)) {
            return null;
        }

        Cell neighborCell = grid.getCells()[row][col];
        if (neighborCell.isUnit()) {
            UnitConnectionInfo ret = new UnitConnectionInfo();
            ret.unit = neighborCell.unit;
            ret.entryDirection = entryDir;
            return ret;
        }
        else if (neighborCell.isPipe()) {
            Pipe connectedPipe = null;
            for (Pipe pipe : neighborCell.getAllPipes()) {
                if (pipe.getInDirection().opposite() == entryDir) {     // connection is good
                    connectedPipe = pipe;
                    break;
                }
            }
            if (connectedPipe == null) {
                // error, no pipe connected to this one, add this cell to the errors and return null
                return null;
            }

//            DirectionType leavingDirection = ();
//            if (neighborCell.pipe1 != null && neighborCell.pipe1.outputDirectionFromInputDirection(entryDir) != null) {
//                leavingDirection = neighborCell.pipe1.outputDirectionFromInputDirection(entryDir);
//            }
//            else if (neighborCell.pipe2 != null && neighborCell.pipe2.outputDirectionFromInputDirection(entryDir) != null) {
//                leavingDirection = neighborCell.pipe2.outputDirectionFromInputDirection(entryDir);
//            }
////                neighborCell = getConnectedNeighborCell(neighborCell.pipe, );
//            if (leavingDirection == null) {
//                return null;
//            }
            return getConnectedNeighborCell(row, col, connectedPipe.getOutDirection());
        }
        else {
            // error, no pipe connected to this one, add this cell to the errors and return null
            return null;
        }
    }

    private boolean isInBounds(Integer row, Integer col) {
        return row >= 0 && col >= 0 && row < grid.getHeight() && col < grid.getWidth();
    }
}
