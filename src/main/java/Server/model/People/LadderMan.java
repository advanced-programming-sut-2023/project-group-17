package Server.model.People;

import Server.model.Database;
import Server.model.User;

public class LadderMan extends Soldier{
    public LadderMan(User owner) {
        super(owner, Database.getSoldierDataByName("ladderman"));
    }
}
