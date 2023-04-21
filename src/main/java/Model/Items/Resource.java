package Model.Items;

import Model.User;

public class Resource extends Item {
    public enum names {
        WHEAT("wheat"),
        FLOUR("flour"),
        HOPS("hops"),
        ALE("ale"),
        STONE("stone"),
        IRON("iron"),
        WOOD("wood"),
        PITCH("pitch");

        names(String name) {
        }
    }
    Resource(String name, double cost, User owner){
        super(name, cost, owner);
    }

    public static boolean contains(String resourceName) {
        for (names nameValue : names.values()) {
            if(nameValue.name().equals(resourceName)) return true;
        }
        return false;
    }
}
