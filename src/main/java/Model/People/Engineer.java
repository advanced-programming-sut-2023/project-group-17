package Model.People;

import Model.User;

public class Engineer extends Soldier{
    public Engineer(User owner) {
        super(owner, SoldierTypes.ENGINEER);
    }
}
