package alch.manager;

import alch.model.DirectionType;
import alch.model.Unit;
import alch.model.UnitConnection;

class UnitConnectionInfo {
//    Cell cell;
    Unit unit;
    DirectionType entryDirection;

    public UnitConnection getInputUnitConnection() {
        for (UnitConnection unitConnection : unit.getConnections()) {
            if (unitConnection.getInput() && unitConnection.getDirectionType().opposite() == entryDirection) {
                return unitConnection;
            }
        }
        return null;
    }
}
