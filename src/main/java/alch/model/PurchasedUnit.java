//package alch.model;
//
//import alch.store.PurchasableUnitType;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "purchased_unit")
//@JsonIgnoreProperties(value = {"grid"})
//public class PurchasedUnit {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id", unique = true, nullable = false)
//    private Long id;
//
//    @OneToOne
//    @JoinColumn(name = "grid", nullable = false, updatable = false)
//    private Grid grid;
//
//    @Column(name = "type")
//    @Enumerated(EnumType.STRING)
//    private PurchasableUnitType purchasableUnitType;
//
//    public Long getId() {
//        return id;
//    }
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Grid getGrid() {
//        return grid;
//    }
//    public void setGrid(Grid grid) {
//        this.grid = grid;
//    }
//
//    public PurchasableUnitType getPurchasableUnitType() {
//        return purchasableUnitType;
//    }
//    public void setPurchasableUnitType(PurchasableUnitType purchasableUnitType) {
//        this.purchasableUnitType = purchasableUnitType;
//    }
//}
