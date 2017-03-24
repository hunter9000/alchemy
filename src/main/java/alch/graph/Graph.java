package alch.graph;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Graph<T> {
    Set<Node<T>> nodes;
    Set<Edge<T>> edges;

    public Graph() {
        nodes = new HashSet<Node<T>>();
        edges = new HashSet<>();
    }

    /** adds unit unlinked */
    public void addNode(T unit) {
        nodes.add(new Node(unit));
    }


    public boolean contains(T firstUnit) {
//        for (Node<T> node : nodes) {
//            if (node.getPayload().equals(firstUnit)) {
//                return true;
//            }
//        }
//        return false;
        return nodes.contains(new Node(firstUnit));
    }

    public void addLink(T firstUnit, T secondUnit) {
        Node<T> firstNode = new Node(firstUnit);
        nodes.add(firstNode);
        Node<T> secondNode = new Node(secondUnit);
        nodes.add(secondNode);

        edges.add(new Edge(firstNode, secondNode));
    }

    public Collection<T> getAllPayloads() {
        Collection<T> ret = new ArrayList<T>();
        CollectionUtils.collect(nodes, input -> { return input.getPayload(); }, ret);
        return ret;
    }

    /** adds all the nodes and edges from the given graph to this one. */
    public void union(Graph otherGraph) {
        this.nodes.addAll(otherGraph.nodes);
        this.edges.addAll(otherGraph.edges);
    }
}
