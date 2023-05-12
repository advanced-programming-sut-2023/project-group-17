package Controller;

import Model.Database;
import Model.Empire;
import Model.User;
import Model.empireColors;
import View.Enums.Messages.MainMenuMessages;
import View.Menu;

public class MainMenuController {
    User loggedInUser = Database.getCurrentUser();
    public MainMenuMessages startNewGame(String users, int turnsCount) {
        if (turnsCount <= 0) return MainMenuMessages.INVALID_NUMBER;
        String[] user = users.split(",");
        if (user.length > 8 || user.length < 1) return MainMenuMessages.INVALID_NUMBER_OF_USERS;
        for (int i = 0; i < user.length; i++) {
            if (user[i].startsWith("\"")) user[i] = user[i].substring(1);
            if (user[i].endsWith("\"")) user[i] = user[i].substring(0, user[i].length() - 1);
        }

        for (String username : user) {
            if (Database.getUserByUsername(username) == null) return MainMenuMessages.USERNAME_DOES_NOT_EXIST;
        }
        empireColors.addColors();
        Database.setEmpireColors(empireColors.getColors());

        for (String username : user) {
            Database.getUsersInTheGame().add(Database.getUserByUsername(username));
        }

        for (int i = 0; i < Database.getUsersInTheGame().size(); i++) {
            Database.getUsersInTheGame().get(i).setEmpire(
                new Empire(Database.getUsersInTheGame().get(i), Database.getEmpireColors().get(i)));
        }
        Database.setTotalTurns(turnsCount * user.length);
//        Database.setCurrentUser(Database.getLoggedInUser());
        return MainMenuMessages.SUCCESS;
    }

    public void logout() {
        if (Database.getStayLoggedInUser() != null) Database.clearStayLoggedIn();
        Database.setLoggedInUser(null);
    }
}
