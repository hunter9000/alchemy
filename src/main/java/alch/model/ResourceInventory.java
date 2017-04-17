package alch.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "resource_inventory")
@JsonIgnoreProperties(value = {"grid"})
public class ResourceInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "grid", nullable = false, updatable = false)
    private Grid grid;

    @Column(name = "type", nullable = false, updatable = false)
    private ResourceType type;

    @Column(name = "amount", nullable = false)
    private Long amount;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Grid getGrid() {
        return grid;
    }
    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public ResourceType getType() {
        return type;
    }
    public void setType(ResourceType type) {
        this.type = type;
    }

    public Long getAmount() {
        return amount;
    }
    public void setAmount(Long amount) {
        this.amount = amount;
    }

    @Transient
    @JsonIgnore
    public void addAmount(long addAmount) {
        this.amount += addAmount;
    }
}
