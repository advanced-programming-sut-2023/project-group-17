package Controller;

import Model.Database;
import Model.User;

public class HeadquarterMenuController {
    public int numberOfPlayerInTheGame() {
        return Database.getUsersInTheGame().size();
    }

    public String getUsernameByNumber(int index) {
        return Database.getUsersInTheGame().get(index).getUsername();
    }

    public void setHeadquartersByNumber(int i, String x, String y) {
        Database.getUsersInTheGame().get(i/2).getEmpire().
                setHeadquarter(Database.getCurrentMapGame().getMapCellByCoordinates(Integer.parseInt(x), Integer.parseInt(y))
                        ,Database.getUsersInTheGame().get(i/2));
    }
}
