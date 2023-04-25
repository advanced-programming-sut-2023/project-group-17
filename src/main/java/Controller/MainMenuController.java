package Controller;

import Model.Database;
import Model.User;
import View.Enums.Messages.MainMenuMessages;

public class MainMenuController {
    User loggedInUser = Database.getLoggedInUser();
    public MainMenuMessages startNewGame(String users, int turnsCount) {
        if (turnsCount <= 0) return MainMenuMessages.INVALID_NUMBER;
        String[] user = users.split(",");

        for (String usernames : user) {
            if (Database.getUserByUsername(usernames) == null) return MainMenuMessages.USERNAME_DOES_NOT_EXIST;
        }
        //TODO start new game
        return null;
    }

    public void logout() {
        if (Database.getStayLoggedInUser() != null) Database.clearStayLoggedIn();
    }
}
