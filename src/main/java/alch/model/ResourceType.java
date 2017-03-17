package alch.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ResourceType {
    WOOD("Wood"),
    DIRT("Dirt"),
    GRASS("Grass"),
    ROCK("Rock"),
    WATER("Water");

    private String displayName;

    public String getDisplayName() {
        return displayName;
    }

    ResourceType(String displayName) {
        this.displayName = displayName;
    }
}
