package Controller;

import Model.Database;
import Model.User;
import View.Enums.Messages.MainMenuMessages;

public class MainMenuController {
    User loggedInUser = Database.getLoggedInUser();
    public MainMenuMessages startNewGame(String users, int turnsCount) {
        //TODO start new game and if users doesn't exist return Username doesn't exist
        return null;
    }
}
