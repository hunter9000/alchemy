package alch.test.util;

import alch.model.DirectionType;
import alch.model.Grid;
import alch.model.Pipe;
import alch.model.Unit;
import alch.model.user.UnitDefinitionType;

public class GridTestUtils {
    public static Unit findUnitByTypeForTesting(Grid grid, UnitDefinitionType type) {
        for (Unit unit : grid.getUnits()) {
            if (unit.getDefinitionType() == type) {
                return unit;
            }
        }
        return null;
    }

    public static Pipe createPipeForTesting(int row, int col, DirectionType in, DirectionType out) {
        Pipe pipe = new Pipe();
        pipe.setRow(row);
        pipe.setCol(col);
        pipe.setInDirection(in);
        pipe.setOutDirection(out);
        return pipe;
    }
}
