package Model.Items;

import Model.User;

public class Resource extends Item {
    public enum resourceType {
        WHEAT("wheat", 0),
        FLOUR("flour", 0),
        HOPS("hops", 0),
        ALE("ale", 0),
        STONE("stone", 0),
        IRON("iron", 0),
        WOOD("wood", 0),
        PITCH("pitch", 0),
        GOLD("gold", 0);

        //TODO: number ham ezafe beshe?

        private final String name;
        private final Double cost;
        resourceType(String name, double cost) {
            this.name = name;
            this.cost = cost;
        }
        public String getName() {
            return this.name;
        }
    }

    //TODO: set costs and numbers?
    public Resource(resourceType resourceType, User owner, double number){
        super(resourceType.name, resourceType.cost, owner, number);
    }

    public static resourceType getResourceType(String resourceName) {
        for (resourceType resourceType : resourceType.values()) {
            if(resourceType.name.equals(resourceName)) return resourceType;
        }
        return null;
    }

    public static double getCostByName(String name) {
        for (resourceType resourceType : resourceType.values()) {
            if(resourceType.name.equals(name)) return resourceType.cost;
        }
        return -1;
    }
}
