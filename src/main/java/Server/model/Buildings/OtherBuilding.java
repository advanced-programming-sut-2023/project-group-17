package Server.model.Buildings;

import Server.model.Database;
import Server.model.Empire;
import Server.model.MapCell;
import Server.model.People.Soldier;
import Server.model.User;

import java.util.Objects;

public class OtherBuilding extends Building{
    boolean religiousBuilding;
    int capacity;
    public OtherBuilding(User owner, int x, int y, OtherBuilding otherBuilding) {
        super(owner, otherBuilding, x, y);
        this.religiousBuilding = otherBuilding.religiousBuilding;
        this.capacity = otherBuilding.capacity;
        if(otherBuilding.getBuildingName().equals("cathedral") && Database.getCurrentUser() != null) makeBlankMonk(x, y);
    }

    private void makeBlankMonk(int x, int y) {
        MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x, y);
        Empire empire = Database.getCurrentUser().getEmpire();
        Soldier soldier = new Soldier(Database.getCurrentUser(),
                Objects.requireNonNull(Database.getSoldierDataByName("black monk")));
        empire.addPopulation(new Soldier(Database.getCurrentUser(), soldier));
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
