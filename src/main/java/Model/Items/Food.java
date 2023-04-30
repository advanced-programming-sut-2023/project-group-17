package Model.Items;

import Model.User;

public class Food extends Item{
    //TODO: default numbers
    public enum foodType {
        MEAT("meat", 0),
        CHEESE("cheese", 0),
        BREAD("bread", 0),
        APPLE("apple", 0);
        private final String name;
        private final double cost;
        foodType(String name, double cost) {
            this.name = name;
            this.cost = cost;
        }
    }
    public Food(foodType foodType, User owner) {
        super(foodType.name, foodType.cost, owner);
    }
    public static boolean contains(String foodName) {
        for (Food.foodType nameValue : Food.foodType.values()) {
            if(nameValue.name.equals(foodName)) return true;
        }
        return false;
    }
    public static double getCostByName(String foodName) {
        for (Food.foodType nameValue : Food.foodType.values()) {
            if(nameValue.name.equals(foodName)) return nameValue.cost;
        }
        return -1;
    }

    @Override
    public String toString() {
        return getItemName() + " costs : " + getCost();
    }
}
