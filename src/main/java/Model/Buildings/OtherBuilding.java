package Model.Buildings;

import Model.Database;
import Model.Empire;
import Model.MapCell;
import Model.People.Soldier;
import Model.User;

import javax.xml.crypto.Data;
import java.util.Objects;

public class OtherBuilding extends Building{
    boolean religiousBuilding;
    int capacity;
    public OtherBuilding(User owner, int number, Building building, int x, int y) {
        super(owner, building, x, y);
        if(building.getBuildingName().equals("cathedral")) makeBlankMonk(x, y);
    }

    private void makeBlankMonk(int x, int y) {
        MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x, y);
        Empire empire = Database.getLoggedInUser().getEmpire();
        Soldier soldier = new Soldier(Database.getLoggedInUser(),
                Objects.requireNonNull(Database.getSoldierDataByName("black monk")));
        empire.addPopulation(new Soldier(Database.getLoggedInUser(), soldier));
        mapCell.addPeople(soldier);
    }

    public boolean isReligiousBuilding() {
        return religiousBuilding;
    }

    public void setReligiousBuilding(boolean religiousBuilding) {
        this.religiousBuilding = religiousBuilding;
    }

    public int getCapacity() {
        return capacity;
    }

}
