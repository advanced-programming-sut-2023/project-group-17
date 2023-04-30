package Model.Items;

import Model.User;

public class TradableItems extends Item{
    public enum TradableItemType {
        //TODO: tradableItems cost must be added
        MEAT("meat", 0),
        CHEESE("cheese", 0),
        BREAD("bread", 0),
        APPLE("apple", 0),
        WHEAT("wheat", 0),
        FLOUR("flour", 0),
        HOPS("hops", 0),
        ALE("ale", 0),
        STONE("stone", 0),
        IRON("iron", 0),
        WOOD("wood", 0),
        PITCH("pitch", 0),
        GOLD("gold", 0);

        private final String name;
        private final Double cost;

        TradableItemType(String name, double cost) {
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
    public TradableItems(TradableItemType tradableItemType, User owner) {
        super(tradableItemType.name, tradableItemType.cost, owner);
    }
    public static TradableItemType getTradableItemType(String tradableItemName) {
        for (TradableItemType tradableItemType : TradableItemType.values()) {
            if(tradableItemType.name.equals(tradableItemName)) return tradableItemType;
        }
        return null;
    }

    public static double getCostByName(String name) {
        for (TradableItemType tradableItemType : TradableItemType.values()) {
            if(tradableItemType.name.equals(name)) return tradableItemType.cost;
        }
        return -1;
    }


}
