package alch.graph;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IteratorUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/** This is a hacked together pile of crap. fix it */
public class Graph<T> {
    Set<Node<T>> nodes;
    Set<Edge<EdgeInfo>> edges;

    public Graph() {
        nodes = new HashSet<>();
        edges = new HashSet<>();
    }

    /** adds unit unlinked */
    public void addNode(T unit) {
        nodes.add(new Node(unit));
    }


    public boolean contains(T firstUnit) {
        return nodes.contains(new Node(firstUnit));
    }

    public void addLink(EdgeInfo firstEdgeInfo, EdgeInfo secondEdgeInfo) {
        Node<T> firstNode = new Node(firstEdgeInfo.unit);
        nodes.add(firstNode);
        Node<T> secondNode = new Node(secondEdgeInfo.unit);
        nodes.add(secondNode);

        edges.add(new Edge(firstEdgeInfo, secondEdgeInfo));
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

    /** Gets the edge that matches the payload objects given. */
    public Edge<EdgeInfo> getEdge(T first, T second) {
        return IteratorUtils.find(edges.iterator(), (Edge edge) -> edge.getSide1().node.getPayload().equals(first)
                                                                && edge.getSide2().node.getPayload().equals(second) );
    }

    public Collection<Edge<EdgeInfo>> getStartingEdges(T payload) {
        return CollectionUtils.select(edges, edge -> { return edge.getSide1().node.getPayload().equals(payload); } );
    }

    public Collection<Edge<EdgeInfo>> getEndingEdges(T payload) {
        return CollectionUtils.select(edges, edge -> { return edge.getSide2().node.getPayload().equals(payload); } );
    }
}
