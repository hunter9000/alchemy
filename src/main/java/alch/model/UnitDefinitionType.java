package alch.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Arrays;
import java.util.List;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum UnitDefinitionType {
    // sources have output to the east
    SOURCE_WOOD(UnitType.SOURCE, 0, ResourceType.WOOD, new UnitConnection(ResourceType.WOOD, DirectionType.EAST, false)),
    SOURCE_DIRT(UnitType.SOURCE, 20, ResourceType.DIRT, new UnitConnection(ResourceType.DIRT, DirectionType.EAST, false)),
    SOURCE_GRASS(UnitType.SOURCE, 10, ResourceType.DIRT,  new UnitConnection(ResourceType.GRASS, DirectionType.EAST, false)),
    SOURCE_ROCK(UnitType.SOURCE, 10, ResourceType.GRASS,  new UnitConnection(ResourceType.ROCK, DirectionType.EAST, false)),
    SOURCE_WATER(UnitType.SOURCE, 10, ResourceType.ROCK,  new UnitConnection(ResourceType.WATER, DirectionType.EAST, false)),

    // west to east
    TRANSMUTER_WOOD_DIRT(UnitType.TRANSMUTER, 10, ResourceType.WOOD, new UnitConnection(ResourceType.WOOD, DirectionType.WEST, true),
                                                                     new UnitConnection(ResourceType.DIRT, DirectionType.EAST, false)),
    TRANSMUTER_DIRT_GRASS(UnitType.TRANSMUTER, 10, ResourceType.DIRT, new UnitConnection(ResourceType.DIRT, DirectionType.WEST, true),
                                                                      new UnitConnection(ResourceType.GRASS, DirectionType.EAST, false)),

    // dual transmuters
    // in north and west, out south
    DUAL_TRANSMUTER_WOOD_DIRT_GRASS(UnitType.DUAL_TRANSMUTER, 10, ResourceType.DIRT, new UnitConnection(ResourceType.WOOD, DirectionType.NORTH, true),
                                                                                new UnitConnection(ResourceType.DIRT, DirectionType.WEST, true),
                                                                                new UnitConnection(ResourceType.GRASS, DirectionType.SOUTH, false)),

    // stockpiles have inputs from the north
    STOCKPILE_WOOD(UnitType.STOCKPILE, 0, ResourceType.WOOD, new UnitConnection(ResourceType.WOOD, DirectionType.NORTH, true)),
    STOCKPILE_DIRT(UnitType.STOCKPILE, 10, ResourceType.WOOD, new UnitConnection(ResourceType.DIRT, DirectionType.NORTH, true)),
    STOCKPILE_GRASS(UnitType.STOCKPILE, 10, ResourceType.DIRT, new UnitConnection(ResourceType.GRASS, DirectionType.NORTH, true)),
    STOCKPILE_ROCK(UnitType.STOCKPILE, 150, ResourceType.GRASS, new UnitConnection(ResourceType.ROCK, DirectionType.NORTH, true)),
    STOCKPILE_WATER(UnitType.STOCKPILE, 200, ResourceType.ROCK, new UnitConnection(ResourceType.WATER, DirectionType.NORTH, true));

    private UnitType unitType;
    private Integer costAmount;
    private ResourceType costResourceType;
    private List<UnitConnection> unitConnections;

    UnitDefinitionType(UnitType unitType, Integer costAmount, ResourceType costResourceType, UnitConnection... unitConnections) {
        this.unitType = unitType;
        this.costAmount = costAmount;
        this.costResourceType = costResourceType;
        this.unitConnections = Arrays.asList(unitConnections);
    }

    public UnitType getUnitType() {
        return unitType;
    }

    public Integer getCostAmount() {
        return costAmount;
    }

    public ResourceType getCostResourceType() {
        return costResourceType;
    }

    public List<UnitConnection> getUnitConnections() {
        return unitConnections;
    }

}
