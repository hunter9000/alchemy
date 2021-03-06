package alch.test.grid;

import alch.manager.GridManager;
import alch.manager.GridProcessingManager;
import alch.manager.ProductionPath;
import alch.model.Grid;
import alch.model.Pipe;
import alch.model.Unit;
import alch.model.UnitDefinitionType;
import alch.model.user.User;
import alch.test.util.GridTestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Test grid with just units and no pipes connecting them. */
public class GridNoPipesTest {

    Grid grid;

    @Before
    public void setupGrid() {
        this.grid = GridManager.createGrid(new User());

        Unit woodSource = null;
        Unit woodDirtTransmuter = null;
        Unit dirtStockpile = null;

        Pipe sourceTransmuterPipe = null;
        Pipe transmuterStockpilePipe = null;

        woodSource = GridTestUtils.findUnitByTypeForTesting(grid, UnitDefinitionType.SOURCE_WOOD);
        woodDirtTransmuter = GridTestUtils.findUnitByTypeForTesting(grid, UnitDefinitionType.TRANSMUTER_WOOD_DIRT);
        dirtStockpile = GridTestUtils.findUnitByTypeForTesting(grid, UnitDefinitionType.STOCKPILE_DIRT);

        Assert.assertNotNull("wood source not found", woodSource);
        Assert.assertNotNull("wood -> dirt transmuter not found", woodDirtTransmuter);
        Assert.assertNotNull("dirt stockpile not found", dirtStockpile);

        woodSource.setRow(0);
        woodSource.setCol(0);

        woodDirtTransmuter.setRow(0);
        woodDirtTransmuter.setCol(2);

        dirtStockpile.setRow(1);
        dirtStockpile.setCol(3);

//        sourceTransmuterPipe = GridTestUtils.createPipeForTesting(0, 1, DirectionType.WEST, DirectionType.EAST);
//        transmuterStockpilePipe = GridTestUtils.createPipeForTesting(0, 3, DirectionType.WEST, DirectionType.SOUTH);

        Set<Pipe> pipes = new HashSet<>();
//        pipes.add(sourceTransmuterPipe);
//        pipes.add(transmuterStockpilePipe);
        grid.setPipes(pipes);

        new GridManager(grid).populateGrid();
    }

    @Test
    public void testPathMaker() {
        GridProcessingManager manager = new GridProcessingManager(grid);

        List<ProductionPath> paths = manager.getPaths();

        Assert.assertNull(paths);
        Assert.assertTrue(!manager.getCellErrors().isEmpty());
    }
}
