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
        PITCH("pitch", 0);

        private final String name;
        private final Double cost;
        resourceType(String name, double cost) {
            this.name = name;
            this.cost = cost;
        }
    }

    //TODO: set costs
    Resource(resourceType resourceType, User owner){
        super(resourceType.name, resourceType.cost, owner);
    }

    public static boolean contains(String resourceName) {
        for (resourceType nameValue : resourceType.values()) {
            if(nameValue.name.equals(resourceName)) return true;
        }
        return false;
    }
}
