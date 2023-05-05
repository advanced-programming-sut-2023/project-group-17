package Model.AttackToolsAndMethods;

import Model.Database;
import Model.User;

public class Trebuchets extends AttackToolsAndMethods{
    public Trebuchets(User owner){
        super(owner, Database.getAttackToolsDataByName("trebuchets"));
    }
}
