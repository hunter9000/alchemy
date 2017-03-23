package alch.graph;

public class Node<T> {
    private T payload;

    public Node(T unit) {
        this.payload = unit;
    }

    public T getPayload() {
        return payload;
    }
}
