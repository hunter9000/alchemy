package model;

import java.util.List;
import java.util.Map;

public class Grid {
    // resource amounts
    @MapKey("type")
    Map<ResourceType, Resource> resources;

    private List<Unit> units;
    private List<Pipe> pipes;

    // entity bucket for purchased, but unused ones?	// no, just clear the x,y
}
