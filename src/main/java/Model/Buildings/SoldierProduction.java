package Model.Buildings;

import Model.People.Soldier;
import Model.User;

import java.util.ArrayList;
import java.util.HashMap;

public class SoldierProduction extends Building{
    private ArrayList<Soldier> storedSoldiers;
    private HashMap<Soldier, Integer> soldiersTrained;
    public SoldierProduction(User owner, Building building, int x , int y, SoldierProductionType.SoldierType soldierProductionType) {
        super(owner, building, x, y);
        this.storedSoldiers = new ArrayList<>();
        this.soldiersTrained = soldierProductionType.getSoldiersTrained();
    }

    public ArrayList<Soldier> getStoredSoldiers() {
        return storedSoldiers;
    }

    public HashMap<Soldier, Integer> getSoldiersTrained() {
        return soldiersTrained;
    }
}
