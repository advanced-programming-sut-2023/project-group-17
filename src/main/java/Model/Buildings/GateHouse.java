package Model.Buildings;

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
    }

    public int getMaximumCapacity() {
        return maximumCapacity;
    }

    public int getEmptySpace() {
        return emptySpace;
    }

    public void addPeople(Person person) {
        this.livingPeople.add(person);
    }
}
