package Model.Buildings;

import Model.Database;
import Model.Items.Item;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductionBuildingType {
    public enum ProductionType {
        MILL(new HashMap<>(){{put(Item.ItemType.HOPS, Item.ItemType.WHEAT);}}, "mill", Database.getBuildingDataByName("stockpile")),
        ARMOURER(new HashMap<>(){{put(Item.ItemType.METAL_ARMOR, Item.ItemType.IRON);}}, "armourer", Database.getBuildingDataByName("armoury")),
        BLACKSMITH(new HashMap<>(){{put(Item.ItemType.SWORDS, Item.ItemType.IRON);put(Item.ItemType.MACE, Item.ItemType.IRON);}}, "blacksmith", Database.getBuildingDataByName("armoury")),
        FLETCHER(new HashMap<>(){{put(Item.ItemType.BOW, Item.ItemType.WOOD);}}, "fletcher", Database.getBuildingDataByName("armoury")),
        POLETURNER(new HashMap<>(){{put(Item.ItemType.SPEAR, Item.ItemType.WOOD);put(Item.ItemType.PIKE, Item.ItemType.WOOD);}}, "poleturner", Database.getBuildingDataByName("armoury")),
        DAIRY(new HashMap<>(){{put(Item.ItemType.CHEESE, Item.ItemType.COW);put(Item.ItemType.LEATHER_ARMOR, Item.ItemType.COW);}}, "dairy", Database.getBuildingDataByName("armoury")),
        BAKERY(new HashMap<>(){{put(Item.ItemType.BREAD, Item.ItemType.FLOUR);}}, "bakery", Database.getBuildingDataByName("granary")),
        BREWER(new HashMap<>(){{put(Item.ItemType.ALE, Item.ItemType.HOPS);}}, "brewer", Database.getBuildingDataByName("inn"))
        ;
        private HashMap<Item.ItemType, Item.ItemType> productionItem;
        private String name;
        private Building relatedStorageBuilding;
        ProductionType(HashMap<Item.ItemType, Item.ItemType> productionItem, String name, Building relatedStorageBuilding) {
            this.productionItem = productionItem;
            this.name = name;
            this.relatedStorageBuilding = relatedStorageBuilding;
        }

        public HashMap<Item.ItemType, Item.ItemType> getProductionItem() {
            return productionItem;
        }

        public String getName() {
            return name;
        }

        public Building getRelatedStorageBuilding() {
            return relatedStorageBuilding;
        }
    }
    public static ProductionType getProductionType(String name) {
        for (ProductionType productionType : ProductionType.values()) {
            if(productionType.name.equals(name)) return productionType;
        }
        return null;
    }
}
