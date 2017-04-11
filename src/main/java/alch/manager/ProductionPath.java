package alch.manager;

import alch.graph.Edge;
import alch.graph.EdgeInfo;
import alch.graph.Graph;
import alch.model.Unit;
import alch.model.UnitConnection;
import alch.model.UnitType;
import alch.response.CellError;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ProductionPath {
    Set<Unit> sourceUnits;

    Graph<Unit> graph;

    public ProductionPath() {
        sourceUnits = new HashSet<>();
        graph = new Graph<>();
    }

    void addSource(Unit unit) {
        sourceUnits.add(unit);
        graph.addNode(unit);
    }

    /** Adds the second unit, creating an edge from the first to second. */
    void linkUnit(Unit firstUnit, UnitConnection firstConnection, Unit secondUnit, UnitConnection secondConnection) {
        if (graph.contains(firstUnit)) {
            graph.addLink(new EdgeInfo(firstUnit, firstConnection), new EdgeInfo(secondUnit, secondConnection));
        }
    }

    public Collection<Unit> getAllUnits() {
        return graph.getAllPayloads();
    }

    public void addPath(ProductionPath membershipPath) {
        graph.union(membershipPath.graph);
        this.sourceUnits.addAll(membershipPath.sourceUnits);
    }

    public Collection<Unit> getStockpiles() {
        return CollectionUtils.select(this.graph.getAllPayloads(), unit -> { return unit.getType() == UnitType.STOCKPILE; });
    }

    public Collection<CellError> isValid() {
        Collection<CellError> errors = new ArrayList<>();

        for (Unit unit : graph.getAllPayloads()) {
            // look at all the transmuters
            if (unit.getType() == UnitType.TRANSMUTER) {
                // get the edges that come into the unit
                Collection<Edge<EdgeInfo>> edges = graph.getEndingEdges(unit);
                Set<UnitConnection> usedConnections = new HashSet<>();

                // for each of the input connections on this unit
                for (UnitConnection connection : unit.getInputConnections()) {
                    // find the edge that connects to that connection, and add it to the set
                    for (Edge<EdgeInfo> edgeInfo : edges) {
                        if (edgeInfo.getSide2().connection.equals(connection)) {
                            usedConnections.add(connection);
                            break;
                        }
                    }
                }

                // if all the unit's connections are in the set, then the unit is well connected
                if (usedConnections.size() != unit.getInputConnections().size()) {
                    errors.add(new CellError(unit.getRow(), unit.getCol(), "Inputs are missing connections"));
                }
            }
        }
        return errors;
    }
}
