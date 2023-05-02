package Model.Buildings;

import Model.Items.Item;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductionBuildingType {
    public enum ProductionType {
        MILL(new HashMap<>(){{put(Item.ItemType.HOPS, Item.ItemType.WHEAT);}}, "mill"),
        ARMOURER(new HashMap<>(){{put(Item.ItemType.METAL_ARMOR, Item.ItemType.IRON);}}, "armourer"),
        BLACKSMITH(new HashMap<>(){{put(Item.ItemType.SWORDS, Item.ItemType.IRON);put(Item.ItemType.MACE, Item.ItemType.IRON);}}, "blacksmith"),
        FLETCHER(new HashMap<>(){{put(Item.ItemType.BOW, Item.ItemType.WOOD);}}, "fletcher"),
        POLETURNER(new HashMap<>(){{put(Item.ItemType.SPEAR, Item.ItemType.WOOD);put(Item.ItemType.PIKE, Item.ItemType.WOOD);}}, "poleturner"),
        DAIRY(new HashMap<>(){{put(Item.ItemType.CHEESE, Item.ItemType.COW);put(Item.ItemType.LEATHER_ARMOR, Item.ItemType.COW);}}, "diary"),
        BAKERY(new HashMap<>(){{put(Item.ItemType.BREAD, Item.ItemType.FLOUR);}}, "bakery"),
        BREWER(new HashMap<>(){{put(Item.ItemType.ALE, Item.ItemType.HOPS);}}, "brewer")
        ;
        private HashMap<Item.ItemType, Item.ItemType> productionItem;
        private String name;
        ProductionType(HashMap<Item.ItemType, Item.ItemType> productionItem, String name) {
            this.productionItem = productionItem;
            this.name = name;
        }

        public HashMap<Item.ItemType, Item.ItemType> getProductionItem() {
            return productionItem;
        }

        public String getName() {
            return name;
        }
    }
    public static ProductionType getProductionType(String name) {
        for (ProductionType productionType : ProductionType.values()) {
            if(productionType.name.equals(name)) return productionType;
        }
        return null;
    }
}
