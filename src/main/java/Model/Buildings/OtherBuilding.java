package Model.Buildings;

import Model.Database;
import Model.People.Soldier;
import Model.User;

public class OtherBuilding extends Building{
    public OtherBuilding(User owner, int number, Building building, int x, int y) {
        super(owner, building, x, y);
    }
}
