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
    //TODO: emun beshe
    private final HashMap<Resource.resourceType, Integer> buildingCost;
    private final HashMap<PeopleType, Integer> numberOfWorkers;
    //TODO: nesfeshoon aslan worker nemikhan

    public Building(User owner, Building building) {
        this.owner = owner;
        this.buildingHp = building.getBuildingHp();
        this.buildingCost = building.getBuildingCost();
        this.buildingName = building.getBuildingName();
        this.numberOfWorkers = building.numberOfWorkers;
        this.category = building.getCategory();
    }

    public User getOwner() {
        return owner;
    }

    public int getBuildingHp() {
        return buildingHp;
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
}
