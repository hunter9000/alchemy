package alch.graph;

public class Edge<T> {
    EdgeInfo side1, side2;

    public Edge(EdgeInfo firstUnit, EdgeInfo secondUnit) {
        side1 = firstUnit;
        side2 = secondUnit;
    }

    public EdgeInfo getSide1() {
        return side1;
    }

    public EdgeInfo getSide2() {
        return side2;
    }

}
