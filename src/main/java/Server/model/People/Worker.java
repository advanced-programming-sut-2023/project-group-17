package Server.model.People;

import Server.model.Buildings.Building;
import Server.model.User;

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
