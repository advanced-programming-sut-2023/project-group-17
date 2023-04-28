package Model.Items;

public enum TradableResources {
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

    //TODO: add weapon and armor
    private final String name;
    private final Double cost;
    private TradableResources(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }
    public String getName() {
        return this.name;
    }
    public static TradableResources getResourceType(String resourceName) {
        for (TradableResources resourceType : TradableResources.values()) {
            if(resourceType.name.equals(resourceName)) return resourceType;
        }
        return null;
    }
}
