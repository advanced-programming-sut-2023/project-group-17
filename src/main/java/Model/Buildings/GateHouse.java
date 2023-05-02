package Model.Buildings;

import Model.Database;
import Model.People.NormalPeople;
import Model.People.Person;
import Model.User;
import View.EmpireMenu;

import java.util.ArrayList;

public class GateHouse extends Building{
    int maximumCapacity;
    int emptySpace;
    private ArrayList<Person> livingPeople;

    public GateHouse(User owner, int number, Building building, int x, int y) {
        super(owner, building, x, y);
        this.livingPeople = new ArrayList<>();
        handleLivingPeople();
    }

    public int getMaximumCapacity() {
        return maximumCapacity;
    }

    public int getEmptySpace() {
        return emptySpace;
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
}
