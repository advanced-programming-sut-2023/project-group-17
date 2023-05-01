package Model.Buildings;

import Model.User;
import View.EmpireMenu;

public class GateHouse extends Building{
    int maximumCapacity;
    int emptySpace;

    public GateHouse(User owner, int number, Building building, int x, int y) {
        super(owner, building, x, y);
    }

    public int getMaximumCapacity() {
        return maximumCapacity;
    }

    public int getEmptySpace() {
        return emptySpace;
    }
}
