//package alch.store;
//
//import alch.model.ResourceType;
//import alch.model.UnitType;
//import org.apache.commons.collections4.ListUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Store {
//
//    private List<PurchasableUnit> inventory;
//
//    public Store() {
//
//        inventory = new ArrayList<PurchasableUnit>();
//        // sources
//        inventory.add(new PurchasableUnit(PurchasableUnitType.SOURCE_WOOD, UnitType.Source, ResourceType.WOOD, null, 10, ResourceType.WOOD));
////        inventory.add(new PurchasableUnit(UnitType.Source, ResourceType.DIRT, null,0, ResourceType.WOOD));
////        inventory.add(new PurchasableUnit(UnitType.Source, ResourceType.GRASS, null,0, ResourceType.WOOD));
////        inventory.add(new PurchasableUnit(UnitType.Source, ResourceType.ROCK, null,0, ResourceType.WOOD));
////        inventory.add(new PurchasableUnit(UnitType.Source, ResourceType.WATER, null,0, ResourceType.WOOD));
//        // transmuters
//        inventory.add(new PurchasableUnit(PurchasableUnitType.TRANSMUTER_WOOD_TO_DIRT, UnitType.Transmuter, ResourceType.WOOD, ResourceType.DIRT, 0, ResourceType.WOOD));
//
//        // stockpiles
//        inventory.add(new PurchasableUnit(PurchasableUnitType.STOCKPILE_1, UnitType.Stockpile, null, null, 10, ResourceType.WOOD));
//        inventory.add(new PurchasableUnit(PurchasableUnitType.STOCKPILE_2, UnitType.Stockpile, null, null, 10, ResourceType.WOOD));
//        inventory.add(new PurchasableUnit(PurchasableUnitType.STOCKPILE_3, UnitType.Stockpile, null, null, 10, ResourceType.WOOD));
//
//        inventory = ListUtils.unmodifiableList(inventory);
//    }
//
//    public List<PurchasableUnit> getAllPurchasableUnits() {
//        List<PurchasableUnit> list = new ArrayList<>();
//        list.addAll(inventory);
//        return list;
//    }
//
//
//}
