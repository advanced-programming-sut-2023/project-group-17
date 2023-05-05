package Model.AttackToolsAndMethods;

import Model.Database;
import Model.User;

public class PortableShields extends AttackToolsAndMethods{
    public PortableShields(User owner){
        super(owner, Database.getAttackToolsDataByName("portable shield"));
    }
}
