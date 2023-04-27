package Model.People;

import Model.Database;
import Model.User;

public class Tunneler extends Soldier{
    public Tunneler(User owner) {
        super(owner, Database.getSoldierDataByName("tunneler"));
    }
}
