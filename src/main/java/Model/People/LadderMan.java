package Model.People;

import Model.User;

public class LadderMan extends Soldier{
    public LadderMan(User owner) {
        super(owner, SoldierTypes.LADDERMAN);
    }
}
