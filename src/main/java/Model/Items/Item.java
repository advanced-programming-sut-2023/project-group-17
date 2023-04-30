package Model.Items;

import Model.User;

public class Item {
    private User owner;
    private double number;
    private String itemName;
    private double cost;
    public Item(String itemName, double cost, User owner) {
        this.itemName = itemName;
        this.cost = cost;
        this.owner = owner;
        this.number = 1;
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
}
