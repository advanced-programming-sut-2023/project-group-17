package Model.Buildings;

import Model.Items.Resource;
import Model.People.PeopleType;
import Model.User;

import java.util.HashMap;

public class Building {
    private final User owner;
    private final String category;
    private int buildingHp;
    private final String buildingName;
    private final HashMap<Resource.resourceType, Integer> buildingCost;
    private final HashMap<PeopleType, Integer> numberOfWorkers;
    private final int x;
    private final int y;

    public Building(User owner, Building building, int x, int y) {
        this.owner = owner;
        this.buildingHp = building.getBuildingHp();
        this.buildingCost = building.getBuildingCost();
        this.buildingName = building.getBuildingName();
        this.numberOfWorkers = building.numberOfWorkers;
        this.category = building.getCategory();
        this.x = x;
        this.y = y;
    }

    public User getOwner() {
        return owner;
    }

    public int getBuildingHp() {
        return buildingHp;
    }

    public void setBuildingHp(int buildingHp) {
        this.buildingHp = buildingHp;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public HashMap<Resource.resourceType, Integer> getBuildingCost() {
        return buildingCost;
    }

    public HashMap<PeopleType, Integer> getNumberOfWorkers() {
        return numberOfWorkers;
    }
    public String getCategory() {
        return category;
    }

    public void changeBuildingHp(int hp) {
        this.buildingHp += hp;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return buildingName + " is located\n" +
                "The owner is : " + owner.getUsername() + ", and the hp is : " + buildingHp;
    }
}
