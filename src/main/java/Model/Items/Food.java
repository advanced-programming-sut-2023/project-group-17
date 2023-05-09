package Model.Items;

import Model.User;

public class Food extends Item{
    public enum foodType {
        MEAT("meat", 8),
        CHEESE("cheese", 8),
        BREAD("bread", 8),
        APPLE("apple", 8);
        private final String name;
        private final double cost;
        foodType(String name, double cost) {
            this.name = name;
            this.cost = cost;
        }
    }
    public Food(foodType foodType, User owner, double number) {
        super(foodType.name, foodType.cost, owner, number);
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
        return getItemName() + " costs : " + getCost() + " number: " + getNumber();
    }
}
