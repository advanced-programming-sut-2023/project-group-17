package Model.Buildings;

import Model.Items.Item;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductionBuildingType {
    public enum ProductionType {
        MILL(new HashMap<>(){{put(Item.ItemType.HOPS, Item.ItemType.WHEAT);}}),
        ARMOURER(new HashMap<>(){{put(Item.ItemType.METAL_ARMOR, Item.ItemType.IRON);}}),
        BLACKSMITH(new HashMap<>(){{put(Item.ItemType.SWORDS, Item.ItemType.IRON);put(Item.ItemType.MACE, Item.ItemType.IRON);}}),
        FLETCHER(new HashMap<>(){{put(Item.ItemType.BOW, Item.ItemType.WOOD);}}),
        POLETURNER(new HashMap<>(){{put(Item.ItemType.SPEAR, Item.ItemType.WOOD);put(Item.ItemType.PIKE, Item.ItemType.WOOD);}}),
        DAIRY(new HashMap<>(){{put(Item.ItemType.CHEESE, Item.ItemType.COW);put(Item.ItemType.LEATHER_ARMOR, Item.ItemType.COW);}}),
        BAKERY(new HashMap<>(){{put(Item.ItemType.BREAD, Item.ItemType.FLOUR);}}),
        BREWER(new HashMap<>(){{put(Item.ItemType.ALE, Item.ItemType.HOPS);}})
        ;
        private HashMap<Item.ItemType, Item.ItemType> productionItem;
        ProductionType(HashMap<Item.ItemType, Item.ItemType> productionItem) {
            this.productionItem = productionItem;
        }
    }
}
