package alch.graph;

import alch.model.Unit;
import alch.model.UnitConnection;

public class EdgeInfo {

    public Unit unit;
    public UnitConnection connection;
    public Node<Unit> node;

    public EdgeInfo(Unit unit, UnitConnection connection) {
        this.unit = unit;
        this.connection = connection;
        this.node = new Node<>(unit);
    }
}
