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

    public GateHouse(User owner, int x, int y, GateHouse gateHouse) {
        super(owner, gateHouse, x, y);
        this.livingPeople = new ArrayList<>();
        this.maximumCapacity = gateHouse.getMaximumCapacity();
        this.house = gateHouse.isHouse();
        if (Database.getCurrentUser() != null)
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
            NormalPeople normalPeople = new NormalPeople(Database.getUserInTheGameByUsername(getOwner().getUsername()));
            Database.getUserInTheGameByUsername(getOwner().getUsername()).getEmpire().addPopulation(normalPeople);
            this.addPerson(normalPeople);
            Database.getCurrentMapGame().getMapCellByCoordinates(this.getX(), this.getY()).addPeople(normalPeople);
        }
    }

    public ArrayList<Person> getLivingPeople() {
        return livingPeople;
    }

    public boolean isHouse() {
        return house;
    }
}
