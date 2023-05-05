package Model.AttackToolsAndMethods;

import Model.Database;
import Model.User;

public class FireBallista extends AttackToolsAndMethods{
    public FireBallista(User owner){
        super(owner, Database.getAttackToolsDataByName("fire ballista"));
    }
}
