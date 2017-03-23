package alch.graph;

public class Edge<T> {
    Node<T> side1, side2;

    public Edge(Node<T> firstUnit, Node<T> secondUnit) {
        side1 = firstUnit;
        side2 = secondUnit;
    }
}
