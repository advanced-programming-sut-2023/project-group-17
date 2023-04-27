package Model.People;

import Model.User;

public class OtherSoldiers extends Soldier{
    public OtherSoldiers(User owner) {
        super(owner, SoldierTypes.LADDERMAN);
    }
}
