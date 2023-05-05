package Model.AttackToolsAndMethods;

import Model.Database;
import Model.User;

public class BatteringRam extends AttackToolsAndMethods{
    public BatteringRam(User owner){
        super(owner, Database.getAttackToolsDataByName("battering ram"));
    }
}
