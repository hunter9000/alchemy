package alch.model;

import alch.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "grid")
@JsonIgnoreProperties(value = {"owner", "units"})
public class Grid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "owner", nullable = false, updatable = false)
    private User owner;

    // resource amounts
    @MapKey(name = "type")
    @OneToMany(mappedBy = "grid", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Map<ResourceType, ResourceInventory> resourceInventory;

    @OneToMany(mappedBy = "grid", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Unit> units;

    @Transient
    @JsonProperty(value = "purchasedUnits")
    private Set<Unit> purchasedUnits;

    @Transient
    @JsonProperty(value = "purchasableUnits")
    private Set<Unit> purchasableUnits;

    @OneToMany(mappedBy = "grid", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Pipe> pipes;

//    @OneToMany(mappedBy = "grid", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    private List<PurchasedUnit> purchasedUnits;

    @Column(name = "width", nullable = false)
    private Integer width;

    @Column(name = "height", nullable = false)
    private Integer height;

    @Transient
    private Cell[][] cells;

    @Transient
    @JsonIgnore
    public Collection<Unit> getPlacedUnits() {
        return org.apache.commons.collections4.CollectionUtils.select(units, unit -> { return unit.isPlaced(); });
    }

    // entity bucket for purchased, but unused ones?	// no, just clear the x,y


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }
    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Map<ResourceType, ResourceInventory> getResourceInventory() {
        return resourceInventory;
    }
    public void setResourceInventory(Map<ResourceType, ResourceInventory> resourceInventory) {
        this.resourceInventory = resourceInventory;
    }

    public Set<Unit> getUnits() {
        return units;
    }
    public void setUnits(Set<Unit> units) {
        this.units = units;
    }

    public Set<Unit> getPurchasedUnits() {
        return purchasedUnits;
    }
    public void setPurchasedUnits(Set<Unit> purchasedUnits) {
        this.purchasedUnits = purchasedUnits;
    }

    public Set<Unit> getPurchasableUnits() {
        return purchasableUnits;
    }
    public void setPurchasableUnits(Set<Unit> purchasableUnits) {
        this.purchasableUnits = purchasableUnits;
    }

    public Set<Pipe> getPipes() {
        return pipes;
    }
    public void setPipes(Set<Pipe> pipes) {
        this.pipes = pipes;
    }

//    public List<PurchasedUnit> getPurchasedUnits() {
//        return purchasedUnits;
//    }
//    public void setPurchasedUnits(List<PurchasedUnit> purchasedUnits) {
//        this.purchasedUnits = purchasedUnits;
//    }

    public Integer getWidth() {
        return width;
    }
    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }
    public void setHeight(Integer height) {
        this.height = height;
    }

    public Cell[][] getCells() {
        return cells;
    }
    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }
}
