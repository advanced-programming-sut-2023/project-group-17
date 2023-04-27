package Model.People;

import Model.Database;
import Model.User;

public class LadderMan extends Soldier{
    public LadderMan(User owner) {
        super(owner, Database.getSoldierDataByName("ladderman"));
    }
}
