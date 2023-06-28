package Server.model.People;

import Server.model.Database;
import Server.model.User;

public class Tunneler extends Soldier{
    public Tunneler(User owner) {
        super(owner, Database.getSoldierDataByName("tunneler"));
    }
}
