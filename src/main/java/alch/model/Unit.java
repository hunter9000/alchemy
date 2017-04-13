package alch.model;

import alch.model.user.UnitDefinitionType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.collections4.CollectionUtils;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "unit")
@JsonIgnoreProperties(value = {"grid"})
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "grid", nullable = false, updatable = false)
    private Grid grid;

    @Column(name = "col")
    private Integer col;

    @Column(name = "row")
    private Integer row;

    // TODO DON'T NEED THIS, GET IT FROM THE DEFINITIONTYPE
//    @Column(name = "type", nullable = false, updatable = false)
//    @Enumerated(EnumType.STRING)
//    private UnitType type;

    @Column(name = "definition_type", nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private UnitDefinitionType definitionType;

//    @Column(name = "output_type", updatable = false)
//    @Enumerated(EnumType.STRING)
//    private ResourceType resourceOutputType;  // optional, for if this is a source or transmuter
//
//    @Column(name = "input_type", updatable = false)
//    @Enumerated(EnumType.STRING)
//    private ResourceType resourceInputType;  // optional, for if this is a transmuter

//    @OneToMany(mappedBy = "unit")
//    List<UnitConnection> connections;

    @Column(name = "purchased", nullable = false)
    private Boolean purchased = false;

//    // TODO don't need these cost vars, get them from the definition type
//    @Column(name = "cost_amount", nullable = false, updatable = false)
//    private Integer costAmount;
//
//    // TODO don't need these cost vars, get them from the definition type
//    @Column(name = "cost_resource_type", updatable = false)
//    @Enumerated(EnumType.STRING)
//    private ResourceType costResourceType;

//	? rotation

    @Transient
    @JsonProperty(value = "isPlaced")
    public boolean isPlaced() {
        return this.col != null && this.row != null;
    }

    @Transient
    @JsonProperty(value = "canAfford")
    public boolean canAfford;
    public boolean isCanAfford() {
        return canAfford;
    }
    public void setCanAfford(boolean canAfford) {
        this.canAfford = canAfford;
    }

    @Transient
    @JsonIgnore
    public Collection<UnitConnection> getOutputConnections() {
        return CollectionUtils.select(getConnections(), (UnitConnection unitConnection) ->  !unitConnection.getInput() );
    }
    @Transient
    @JsonIgnore
    public Collection<UnitConnection> getInputConnections() {
        return CollectionUtils.select(getConnections(), (UnitConnection unitConnection) ->  unitConnection.getInput() );
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCol() {
        return col;
    }
    public void setCol(Integer col) {
        this.col = col;
    }

    public Integer getRow() {
        return row;
    }
    public void setRow(Integer row) {
        this.row = row;
    }

    public UnitDefinitionType getDefinitionType() {
        return definitionType;
    }
    public void setDefinitionType(UnitDefinitionType definitionType) {
        this.definitionType = definitionType;
    }

    public Grid getGrid() {
        return grid;
    }
    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public Boolean getPurchased() {
        return purchased;
    }
    public void setPurchased(Boolean purchased) {
        this.purchased = purchased;
    }


//    public ResourceType getResourceOutputType() {
//        return resourceOutputType;
//    }
//    public void setResourceOutputType(ResourceType resourceOutputType) {
//        this.resourceOutputType = resourceOutputType;
//    }
//
//    public ResourceType getResourceInputType() {
//        return resourceInputType;
//    }
//    public void setResourceInputType(ResourceType resourceInputType) {
//        this.resourceInputType = resourceInputType;
//    }

    @JsonProperty(value = "type")
    public UnitType getType() {
        return definitionType.getUnitType();
    }

    @JsonProperty(value = "connections")
    public List<UnitConnection> getConnections() {
        return definitionType.getUnitConnections();
    }

    @JsonProperty(value = "costAmount")
    public Integer getCostAmount() {
        return definitionType.getCostAmount();
    }

    @JsonProperty(value = "costResourceType")
    public ResourceType getCostResourceType() {
        return definitionType.getCostResourceType();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Unit)) {
            return false;
        }
        Unit other = (Unit)obj;
//        if (Objects.equals(this.id, other.id)) {
//            return true;
//        }
        return Objects.equals(this.col, other.col)
                && Objects.equals(this.row, other.row)
//                && Objects.equals(this.type, other.type)
                && Objects.equals(this.definitionType, other.definitionType);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
