package Model.Items;

import Model.User;

public class Item {
    private User owner;
    private int number;
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

    public int getNumber() {
        return number;
    }

    public String getItemName() {
        return itemName;
    }

    public double getCost() {
        return cost;
    }

    public void changeNumber(int number) {
        this.number += number;
    }
}
