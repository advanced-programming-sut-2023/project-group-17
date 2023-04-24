package Controller;

import Model.Database;
import Model.Map;
import Utils.CheckMapCell;
import View.Enums.Messages.GameMenuMessages;
import View.Enums.Messages.MapMenuMessages;
import View.Menu;

public class GameMenuController {

    public GameMenuMessages chooseMapGame(int id) {
        Map map = Database.getAllMaps().get(id-1);
        Database.setCurrentMapGame(map);
        return GameMenuMessages.SUCCESS;
    }

    public GameMenuMessages showMap(int x, int y) {
        if (!CheckMapCell.validationOfX(x)) return GameMenuMessages.X_OUT_OF_BOUNDS;
        if (!CheckMapCell.validationOfY(y)) return GameMenuMessages.Y_OUT_OF_BOUNDS;
        return GameMenuMessages.SUCCESS;
    }

    public GameMenuMessages defineMapSize(int width, int length) {
        Map map = new Map(length, width);
        Database.getAllMaps().add(map);
        return GameMenuMessages.SUCCESS;
    }
}
