package Controller;

import Model.Database;
import Model.Map;
import Utils.CheckMapCell;
import View.Enums.Messages.GameMenuMessages;
import View.Enums.Messages.MapMenuMessages;
import View.Menu;

public class GameMenuController {

    public GameMenuMessages chooseMapGame(int id) {
        if(id > Database.getAllMaps().size() || id < 1) return GameMenuMessages.INVALID_MAP_NUMBER;
        Map map = Database.getAllMaps().get(id-1);
        Database.setCurrentMapGame(map);
        return GameMenuMessages.SUCCESS;
    }

    public GameMenuMessages showMap(int x, int y) {
        if (!CheckMapCell.validationOfX(x)) return GameMenuMessages.X_OUT_OF_BOUNDS;
        if (!CheckMapCell.validationOfY(y)) return GameMenuMessages.Y_OUT_OF_BOUNDS;
        return GameMenuMessages.SUCCESS;
    }

    public GameMenuMessages createNewMap(int width, int length) {
        if(width <= 0) {
            return GameMenuMessages.INVALID_WIDTH;
        }

        if(length <= 0) {
            return GameMenuMessages.INVALID_LENGTH;
        }

        Map map = new Map(length, width);
        Database.getAllMaps().add(map);
        Database.setCurrentMapGame(map);
        return GameMenuMessages.SUCCESS;
    }

    public void nextTurn() {

    }

    public void applyDamages() {

    }

    public void changePopularity() {

    }

    public void changePopulation() {

    }

    public void giveFood() {

    }

    public void getTax() {

    }

    public void changeResources() {

    }

    public void handleUnemployedPopulation() {

    }

    public void soldiersFight() {

    }

    public int showTaxRate() {
        return 0;
    }

    public void buildingsFight() {

    }

    public String chooseMap() {
        return "Choose your map by id:\n" +
        "Give an id between 1 and " + Database.getAllMaps().size() + "\n" +
        "If you want to create custom map enter 0";
    }
}
