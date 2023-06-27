package Server.controller;

import Model.Database;
import Model.Empire;
import Model.User;

public class HeadquarterMenuController {
    public int numberOfPlayerInTheGame() {
        return Database.getUsersInTheGame().size();
    }

    public String getUsernameByNumber(int index) {
        return Database.getUsersInTheGame().get(index).getUsername();
    }

    public void setHeadquartersByNumber(int i, String x, String y) {
        User user = Database.getUsersInTheGame().get(i);
        Empire empire = Database.getUsersInTheGame().get(i).getEmpire();
        Database.setCurrentUser(user);
        empire.makeHeadquarter(Integer.parseInt(x), Integer.parseInt(y), user);
        Database.setCurrentUser(Database.getLoggedInUser());
    }
}
