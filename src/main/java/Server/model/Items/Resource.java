package Server.model.Items;

import Server.model.User;

public class Resource extends Item {
    public enum resourceType {
        WHEAT("wheat", 23),
        FLOUR("flour", 32),
        HOPS("hops", 15),
        ALE("ale", 20),
        STONE("stone", 14),
        IRON("iron", 45),
        WOOD("wood", 4),
        PITCH("pitch", 100),
        GOLD("gold", 0)
        ;
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
