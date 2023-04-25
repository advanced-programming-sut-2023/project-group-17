package Model.Buildings;

import Model.Items.Item;
import Model.Items.Resource;
import Model.People.PeopleType;
import Model.People.Person;
import Model.User;

import java.util.HashMap;

public class Building {
    private final User owner;
    private String category;
    private int buildingHp;
    private String buildingName;
    //TODO: emun beshe
    private HashMap<Resource.resourceType, Integer> buildingCost;
    private HashMap<PeopleType, Integer> numberOfWorkers;
    //TODO: nesfeshoon aslan worker nemikhan

    public Building(User owner) {
        //TODO make buildings file
        this.owner = owner;
//        this.buildingHp = buildingType.getHp();
//        this.buildingCost = buildingType.getCost();
//        this.buildingName = buildingType.getName();
//        this.numberOfWorkers = buildingType.getWorkers();
//        this.category = buildingType.getCategory();
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
