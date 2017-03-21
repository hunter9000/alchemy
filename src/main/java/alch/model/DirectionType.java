package alch.model;

public enum DirectionType {
    N, E, S, W;

    public DirectionType opposite() {
        return DirectionType.values()[(this.ordinal() + 2) % 4];
    }
}
