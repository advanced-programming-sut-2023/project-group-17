package Model.Buildings;

import Model.Items.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StorageBuildingType {
    public enum StorageType {
        ARMORY("armory", new ArrayList<Item.ItemType>(Arrays.asList(Item.ItemType.SPEAR, Item.ItemType.SWORDS, Item.ItemType.MACE,
            Item.ItemType.METAL_ARMOR, Item.ItemType.LEATHER_ARMOR, Item.ItemType.BOW, Item.ItemType.CROSSBOW,
            Item.ItemType.PIKE))),
        CAGED_WAR_DOGS("caged war dogs", new ArrayList<Item.ItemType>(List.of(Item.ItemType.DOG))),
        STABLE("stable", new ArrayList<Item.ItemType>(List.of(Item.ItemType.HORSE))),
        GRANARY("granary", new ArrayList<Item.ItemType>(Arrays.asList(Item.ItemType.MEAT, Item.ItemType.CHEESE,
            Item.ItemType.BREAD, Item.ItemType.APPLE))),
        STOCKPILE("stockpile", new ArrayList<Item.ItemType>(Arrays.asList(Item.ItemType.WHEAT, Item.ItemType.FLOUR,
            Item.ItemType.HOPS, Item.ItemType.ALE, Item.ItemType.STONE, Item.ItemType.IRON, Item.ItemType.WOOD,
            Item.ItemType.PITCH, Item.ItemType.GOLD)))
        ;
        String name;
        private ArrayList<Item.ItemType> storedItemsTypes;
        StorageType(String name, ArrayList<Item.ItemType> storedItemsTypes) {
            this.name = name;
            this.storedItemsTypes = storedItemsTypes;
        }

        public String getName() {
            return name;
        }

        public ArrayList<Item.ItemType> getStoredItemsTypes() {
            return storedItemsTypes;
        }
    }
}
