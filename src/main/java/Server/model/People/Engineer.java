package Server.model.People;

import Server.model.Database;
import Server.model.User;

public class Engineer extends Soldier{
    public Engineer(User owner) {
        super(owner, Database.getSoldierDataByName("enginner"));
    }
}
