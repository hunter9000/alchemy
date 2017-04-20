package alch.test.grid;

import alch.manager.GridManager;
import alch.manager.GridProcessingManager;
import alch.manager.ProductionPath;
import alch.model.DirectionType;
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

/** connects a wood source to a wood stockpile with a single pipe. */
public class GridDirectConnectionTest {

    Grid grid;

    @Before
    public void setupGrid() {
        this.grid = GridManager.createGrid(new User());

        Unit woodSource = null;
        Unit woodStockpile = null;

        Pipe pipe = null;

        woodSource = GridTestUtils.findUnitByTypeForTesting(grid, UnitDefinitionType.SOURCE_WOOD);
        woodStockpile = GridTestUtils.findUnitByTypeForTesting(grid, UnitDefinitionType.STOCKPILE_WOOD);

        Assert.assertNotNull("wood source not found", woodSource);
        Assert.assertNotNull("wood stockpile not found", woodStockpile);

        woodSource.setRow(0);
        woodSource.setCol(0);

        woodStockpile.setRow(1);
        woodStockpile.setCol(1);

        pipe = GridTestUtils.createPipeForTesting(0, 1, DirectionType.SOUTH, DirectionType.WEST);

        Set<Pipe> pipes = new HashSet<>();
        pipes.add(pipe);
        grid.setPipes(pipes);

        new GridManager(grid).populateGrid();
    }



    @Test
    public void testPathMaker() {
        GridProcessingManager manager = new GridProcessingManager(grid);

        List<ProductionPath> paths = manager.getPaths();

        Assert.assertNotNull(paths);
        Assert.assertTrue(manager.getCellErrors().isEmpty());
        Assert.assertTrue(paths.size() == 1);
    }
}
