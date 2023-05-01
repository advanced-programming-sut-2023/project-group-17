package Model.People;

import Model.Buildings.Building;
import Model.People.Person;
import Model.User;

public class Worker extends Person {
    private final Building workingBuilding;
    public Worker(User owner, Building workingBuilding) {
        super(owner, 10);
        this.workingBuilding = workingBuilding;
    }

    public Building getWorkingBuilding() {
        return workingBuilding;
    }
}
