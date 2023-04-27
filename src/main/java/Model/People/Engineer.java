package Model.People;

import Model.Database;
import Model.User;

public class Engineer extends Soldier{
    public Engineer(User owner) {
        super(owner, Database.getSoldierDataByName("enginner"));
    }
}
