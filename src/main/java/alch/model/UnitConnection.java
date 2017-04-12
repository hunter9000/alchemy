package alch.model;

//@Entity
//@Table(name = "unit_connection")
//@JsonIgnoreProperties(value = {"unit"})
public class UnitConnection {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id", unique = true, nullable = false)
//    private Long id;

//    @OneToOne
//    @JoinColumn(name = "unit", nullable = false, updatable = false)
//    private Unit unit;

//    @Column(name = "resource_type", nullable = false, updatable = false)
//    @Enumerated(EnumType.STRING)
    private ResourceType resourceType;

//    @Column(name = "direction_type", nullable = false, updatable = false)
//    @Enumerated(EnumType.STRING)
    private DirectionType directionType;

//    @Column(name = "is_input", nullable = false, updatable = false)
    private Boolean isInput;  // isOutput?

    public UnitConnection() {}
    public UnitConnection(ResourceType resourceType, DirectionType directionType, boolean isInput) {
        this.resourceType = resourceType;
        this.directionType = directionType;
        this.isInput = isInput;
    }

//    public Long getId() {
//        return id;
//    }
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Unit getUnit() {
//        return unit;
//    }
//    public void setUnit(Unit unit) {
//        this.unit = unit;
//    }

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
