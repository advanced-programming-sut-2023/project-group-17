package Model.Buildings;

import Model.Database;
import Model.People.NormalPeople;
import Model.People.Person;
import Model.User;

import java.util.ArrayList;

public class GateHouse extends Building{
    int maximumCapacity;
    private ArrayList<Person> livingPeople;
    boolean house;

    public GateHouse(User owner, Building building, int x, int y, GateHouseType.Type gateHouseType) {
        super(owner, building, x, y);
        this.livingPeople = new ArrayList<>();
        this.maximumCapacity = gateHouseType.getMaximumCapacity();
        this.house = gateHouseType.isHouse();
        if (Database.getLoggedInUser() != null)
            handleLivingPeople();
    }

    public int getMaximumCapacity() {
        return maximumCapacity;
    }

    public void addPerson(Person person) {
        this.livingPeople.add(person);
    }

    public void handleLivingPeople() {
        for(int i = 0; i < this.getMaximumCapacity(); i++) {
            NormalPeople normalPeople = new NormalPeople(Database.getLoggedInUser());
            Database.getLoggedInUser().getEmpire().addPopulation(normalPeople);
            this.addPerson(normalPeople);
        }
    }

    public ArrayList<Person> getLivingPeople() {
        return livingPeople;
    }

    public boolean isHouse() {
        return house;
    }
}
