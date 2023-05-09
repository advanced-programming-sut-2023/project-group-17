package Model.Items;

import Model.Database;
import Model.Empire;
import Model.User;

public class Item {

    public enum ItemType {
        WHEAT("wheat", 23),
        FLOUR("flour", 32),
        HOPS("hops", 15),
        ALE("ale", 20),
        STONE("stone", 14),
        IRON("iron", 45),
        WOOD("wood", 4),
        PITCH("pitch", 100),
        GOLD("gold", 0),
        MEAT("meat", 8),
        CHEESE("cheese", 8),
        BREAD("bread", 8),
        APPLE("apple", 8),
        BOW("bow", 31),
        CROSSBOW("crossbow", 58),
        SPEAR("spear", 20),
        PIKE("pike", 36),
        MACE("mace", 58),
        SWORDS("swords", 58),
        LEATHER_ARMOR("leather armor", 25),
        METAL_ARMOR("metal armor", 58),
        OIL("oil", 0),
        COW("cow", 0),
        DOG("dog", 0),
        HORSE("horse", 0)
        ;

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

    public static ItemTypes getItemByName(String name) {
        Empire empire = Database.getCurrentUser().getEmpire();

        if(empire.getFoodByName(name) != null) {
            return ItemTypes.FOOD;
        }
        else if(empire.getWeaponByName(name) != null) {
            return ItemTypes.WEAPON;
        }
        else if(empire.getResourceByName(name) != null) {
            return ItemTypes.RESOURCE;
        }
        else if (name.equals("cow")) {
            return ItemTypes.ANIMAL;
        }
        return null;
    }

    public static Item getAvailableItems(String itemName) {
        Empire empire = Database.getCurrentUser().getEmpire();
        switch (getItemByName(itemName)) {
            case RESOURCE:
                return empire.getResourceByName(itemName);
            case FOOD:
                return empire.getFoodByName(itemName);
            case WEAPON:
                return empire.getWeaponByName(itemName);
            case ANIMAL:
                return empire.getAnimalByName(itemName);
        }
        return null;
    }

}
