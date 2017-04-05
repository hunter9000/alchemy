package alch.test.grid;

import alch.manager.GridManager;
import alch.manager.GridProcessingManager;
import alch.manager.ProductionPath;
import alch.model.DirectionType;
import alch.model.Grid;
import alch.model.Pipe;
import alch.model.Unit;
import alch.model.user.UnitDefinitionType;
import alch.model.user.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class GridProcessingManagerTest {

    Grid grid;

    @Before
    public void createInfo() {
        this.grid = GridManager.createGrid(new User());

        Unit woodSource = null;
        Unit woodDirtTransmuter = null;
        Unit dirtStockpile = null;

        Pipe sourceTransmuterPipe = null;
        Pipe transmuterStockpilePipe = null;

        for (Unit unit : grid.getUnits()) {
            if (unit.getDefinitionType() == UnitDefinitionType.SOURCE_WOOD) {
                woodSource = unit;
            }
            else if (unit.getDefinitionType() == UnitDefinitionType.TRANSMUTER_WOOD_DIRT) {
                woodDirtTransmuter = unit;
            }
            else if (unit.getDefinitionType() == UnitDefinitionType.STOCKPILE_DIRT) {
                dirtStockpile = unit;
            }
        }

        Assert.assertNotNull("wood source not found", woodSource);
        Assert.assertNotNull("wood -> dirt transmuter not found", woodDirtTransmuter);
        Assert.assertNotNull("dirt stockpile not found", dirtStockpile);

        woodSource.setRow(0);
        woodSource.setCol(0);

        woodDirtTransmuter.setRow(0);
        woodDirtTransmuter.setCol(2);

        dirtStockpile.setRow(1);
        dirtStockpile.setCol(3);

        sourceTransmuterPipe = new Pipe();
        sourceTransmuterPipe.setRow(0);
        sourceTransmuterPipe.setCol(1);
        sourceTransmuterPipe.setInDirection(DirectionType.WEST);
        sourceTransmuterPipe.setOutDirection(DirectionType.EAST);

        transmuterStockpilePipe = new Pipe();
        transmuterStockpilePipe.setRow(0);
        transmuterStockpilePipe.setCol(3);
        transmuterStockpilePipe.setInDirection(DirectionType.WEST);
        transmuterStockpilePipe.setOutDirection(DirectionType.SOUTH);

        Set<Pipe> pipes = new HashSet<>();
        pipes.add(sourceTransmuterPipe);
        pipes.add(transmuterStockpilePipe);
        grid.setPipes(pipes);

        new GridManager(grid).populateGrid();
    }

    @Test
    public void testPathMaker() {
        GridProcessingManager manager = new GridProcessingManager(grid);

        List<ProductionPath> paths = manager.getPaths();

        Assert.assertNotNull(paths);
    }
}
