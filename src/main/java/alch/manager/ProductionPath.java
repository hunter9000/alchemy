package alch.manager;

import alch.graph.Graph;
import alch.model.Unit;

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
    void linkUnit(Unit firstUnit, Unit secondUnit) {
        if (graph.contains(firstUnit)) {
            graph.addLink(firstUnit, secondUnit);
        }
    }

    public Collection<Unit> getAllUnits() {
        return graph.getAllPayloads();
    }

    public void addPath(ProductionPath membershipPath) {
        graph.union(membershipPath.graph);
        this.sourceUnits.addAll(membershipPath.sourceUnits);
    }
}
