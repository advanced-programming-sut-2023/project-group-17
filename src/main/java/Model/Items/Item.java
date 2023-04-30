package Model.Items;

import Model.User;

public class Item {

    public enum ItemType {
        WHEAT("wheat", 0),
        FLOUR("flour", 0),
        HOPS("hops", 0),
        ALE("ale", 0),
        STONE("stone", 0),
        IRON("iron", 0),
        WOOD("wood", 0),
        PITCH("pitch", 0),
        GOLD("gold", 0),
        MEAT("meat", 0),
        CHEESE("cheese", 0),
        BREAD("bread", 0),
        APPLE("apple", 0),
        BOW("bow", 0),
        CROSSBOW("crossbow", 0),
        SPEAR("spear", 0),
        PIKE("pike", 0),
        MACE("mace", 0),
        SWORDS("swords", 0),
        LEATHER_ARMOR("leather armor", 0),
        METAL_ARMOR("metal armor", 0);

        //TODO: number ham ezafe beshe?

        private final String name;
        private final Double cost;
        ItemType(String name, double cost) {
            this.name = name;
            this.cost = cost;
        }
        public String getName() {
            return this.name;
        }
        public double getCost() {
            return this.cost;
        }
    }

    private User owner;
    private double number;
    private String itemName;
    private double cost;
    public Item(String itemName, double cost, User owner, double number) {
        this.itemName = itemName;
        this.cost = cost;
        this.owner = owner;
        this.number = number;
    }
    public User getOwner() {
        return owner;
    }

    public double getNumber() {
        return number;
    }

    public String getItemName() {
        return itemName;
    }

    public double getCost() {
        return cost;
    }

    public void changeNumber(double number) {
        this.number += number;
    }

    public static ItemType getItemType(String name) {
        for (ItemType itemType : ItemType.values()) {
            if(itemType.name.equals(name)) return itemType;
        }
        return null;
    }
}
