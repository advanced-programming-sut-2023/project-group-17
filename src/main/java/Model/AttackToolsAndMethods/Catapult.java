package Model.AttackToolsAndMethods;

import Model.Database;
import Model.User;

public class Catapult extends AttackToolsAndMethods{
    public Catapult(User owner){
        super(owner, Database.getAttackToolsDataByName("catapult"));
    }
}
