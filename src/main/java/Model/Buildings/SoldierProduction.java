package Model.Buildings;

import Model.People.Soldier;
import Model.User;

import java.util.ArrayList;
import java.util.HashMap;

public class SoldierProduction extends Building{
    private ArrayList<Soldier> storedSoldiers;
    private HashMap<Soldier, Integer> soldiersTrained;
    public SoldierProduction(User owner, int x , int y, SoldierProduction soldierProduction) {
        super(owner, soldierProduction, x, y);
        this.storedSoldiers = new ArrayList<>();
        this.soldiersTrained = soldierProduction.getSoldiersTrained();
    }

    public ArrayList<Soldier> getStoredSoldiers() {
        return storedSoldiers;
    }

    public HashMap<Soldier, Integer> getSoldiersTrained() {
        return soldiersTrained;
    }
}
