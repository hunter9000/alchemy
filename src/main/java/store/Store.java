package store;

import model.ResourceType;
import model.UnitType;

import java.util.ArrayList;
import java.util.List;

public class Store {
    private static List<PurchasableUnit> inventory;
    static {
        inventory = new ArrayList<PurchasableUnit>();
        // sources
        inventory.add(new PurchasableUnit(UnitType.Source, ResourceType.WOOD, null, 10, ResourceType.WOOD));
//        inventory.add(new PurchasableUnit(UnitType.Source, ResourceType.DIRT, null,0, ResourceType.WOOD));
//        inventory.add(new PurchasableUnit(UnitType.Source, ResourceType.GRASS, null,0, ResourceType.WOOD));
//        inventory.add(new PurchasableUnit(UnitType.Source, ResourceType.ROCK, null,0, ResourceType.WOOD));
//        inventory.add(new PurchasableUnit(UnitType.Source, ResourceType.WATER, null,0, ResourceType.WOOD));
        // transmuters
        inventory.add(new PurchasableUnit(UnitType.Transmuter, ResourceType.WOOD, ResourceType.DIRT, 0, ResourceType.WOOD));

        // stockpiles
        inventory.add(new PurchasableUnit(UnitType.Stockpile, null, null, 10, ResourceType.WOOD));
    }




}
