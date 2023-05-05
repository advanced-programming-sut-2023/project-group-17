package Model.AttackToolsAndMethods;

import Model.Database;
import Model.User;

public class SiegeTower extends AttackToolsAndMethods{
    public SiegeTower(User owner){
        super(owner, Database.getAttackToolsDataByName("siege tower"));
    }
}
