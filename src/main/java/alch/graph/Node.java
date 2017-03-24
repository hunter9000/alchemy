package alch.graph;

import java.util.Objects;

public class Node<T> {
    private T payload;

    public Node(T unit) {
        this.payload = unit;
    }

    public T getPayload() {
        return payload;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Node)) {
            return false;
        }
        Node<T> other = (Node<T>)obj;
        return Objects.equals(payload, other.payload);
    }

    @Override
    public int hashCode() {
        return payload == null ? 0 : payload.hashCode();
    }
}
