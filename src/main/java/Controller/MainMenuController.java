package Controller;

import Model.Database;
import Model.Empire;
import Model.User;
import View.Enums.Messages.MainMenuMessages;
import View.Menu;

public class MainMenuController {
    User loggedInUser = Database.getLoggedInUser();
    public MainMenuMessages startNewGame(String users, int turnsCount) {
        if (turnsCount <= 0) return MainMenuMessages.INVALID_NUMBER;
        String[] user = users.split(",");
        if (user.length > 8 || user.length < 1) return MainMenuMessages.INVALID_NUMBER_OF_USERS;
        for (int i = 0; i < user.length; i++)
            user[i] = Menu.handleDoubleQuote(user[i]);

        for (String username : user) {
            if (Database.getUserByUsername(username) == null) return MainMenuMessages.USERNAME_DOES_NOT_EXIST;
        }
        for (String username : user) {
            Database.getUsersInTheGame().add(Database.getUserByUsername(username));
        }
        for (User username : Database.getUsersInTheGame()) {
            username.setEmpire(new Empire());
        }
        Database.setTotalTurns(turnsCount * user.length);
        return MainMenuMessages.SUCCESS;
    }

    public void logout() {
        if (Database.getStayLoggedInUser() != null) Database.clearStayLoggedIn();
    }
}
