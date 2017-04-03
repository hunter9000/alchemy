package alch.model;

public enum DirectionType {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    public DirectionType opposite() {
        return DirectionType.values()[(this.ordinal() + 2) % 4];
    }
}
