package alch.model;

import javax.persistence.*;

public class UnitConnection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "unit", nullable = false, updatable = false)
    private Unit unit;

    @Column(name = "resource_type", nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private ResourceType resourceType;

    @Column(name = "direction_type", nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private DirectionType directionType;

    private Boolean isInput;  // isOutput?

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Unit getUnit() {
        return unit;
    }
    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }
    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public DirectionType getDirectionType() {
        return directionType;
    }
    public void setDirectionType(DirectionType directionType) {
        this.directionType = directionType;
    }

    public Boolean getInput() {
        return isInput;
    }
    public void setInput(Boolean input) {
        isInput = input;
    }
}
