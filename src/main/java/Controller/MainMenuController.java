package Controller;

import Model.Database;
import Model.Empire;
import Model.User;
import Model.empireColors;
import Utils.CheckMapCell;
import View.Enums.Messages.MainMenuMessages;
import View.Enums.Messages.MapMenuMessages;
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
        empireColors.addColors();
        Database.setEmpireColors(empireColors.getColors());

        for (String username : user) {
            Database.getUsersInTheGame().add(Database.getUserByUsername(username));
        }

        for (int i = 0; i < Database.getUsersInTheGame().size(); i++) {
            Database.getUsersInTheGame().get(i).setEmpire(new Empire(Database.getEmpireColors().get(i)));
        }
        Database.setTotalTurns(turnsCount * user.length);
        return MainMenuMessages.SUCCESS;
    }

    public void logout() {
        if (Database.getStayLoggedInUser() != null) Database.clearStayLoggedIn();
    }

    public int getNumberOfPlayers() {
        return Database.getUsersInTheGame().size();
    }

    public String getPlayerName(int i) {
        return Database.getUsersInTheGame().get(i).getUsername();
    }

    public MapMenuMessages setHeadquarters(int i, int x, int y) {

        if (!CheckMapCell.validationOfX(x)) return MapMenuMessages.X_OUT_OF_BOUNDS;
        if (!CheckMapCell.validationOfY(y)) return MapMenuMessages.Y_OUT_OF_BOUNDS;
        if (!CheckMapCell.mapCellEmptyByCoordinates(x, y)) return MapMenuMessages.CELL_IS_FULL;

        if (Database.getCurrentMapGame().getMapCellByCoordinates(x, y).getMaterialMap().isWaterZone())
            return MapMenuMessages.INAPPROPRIATE_TEXTURE;

        User user = Database.getUsersInTheGame().get(i);
        Empire empire = Database.getUsersInTheGame().get(i).getEmpire();
        empire.makeHeadquarter(x, y, user);
        return MapMenuMessages.SUCCESS;
    }
}
