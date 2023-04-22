package Controller;

import Model.Database;
import Model.MapCell;
import Utils.CheckMapCell;
import View.Enums.Messages.MapMenuMessages;

public class MapMenuController {
    public MapMenuMessages showMap(int x, int y) {
        return null;
    }

    public MapMenuMessages moveMap(String directions) {
        return null;
    }

    public MapMenuMessages showDetails(int x, int y) {
        return null;
    }

    public MapMenuMessages setTextureOfOneBlock(int x, int y, String type) {
        if (!CheckMapCell.validationOfX(x)) return MapMenuMessages.X_OUT_OF_BOUNDS;
        if (!CheckMapCell.validationOfY(y)) return MapMenuMessages.Y_OUT_OF_BOUNDS;
        //TODO checking that type is valid or not
        return MapMenuMessages.SUCCESS;
    }

    public MapMenuMessages setTextureMultipleBlocks(int x1, int x2, int y1, int y2, String type) {
        if (!CheckMapCell.validationOfX(x1)) return MapMenuMessages.X_OUT_OF_BOUNDS;
        if (!CheckMapCell.validationOfX(x2)) return MapMenuMessages.X_OUT_OF_BOUNDS;
        if (!CheckMapCell.validationOfY(y1)) return MapMenuMessages.Y_OUT_OF_BOUNDS;
        if (!CheckMapCell.validationOfY(y2)) return MapMenuMessages.Y_OUT_OF_BOUNDS;
        //TODO checking that type is valid or not
        return MapMenuMessages.SUCCESS;
    }

    public MapMenuMessages clearBlock(int x, int y) {
        if (!CheckMapCell.validationOfX(x)) return MapMenuMessages.X_OUT_OF_BOUNDS;
        if (!CheckMapCell.validationOfY(y)) return MapMenuMessages.Y_OUT_OF_BOUNDS;
        MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x, y);
        mapCell.addBuilding(null);
        mapCell.setMapCellItems(null);
        mapCell.setItems(null);
        mapCell.setAttackToolsAndMethods(null);
        mapCell.setPeople(null);
        //TODO set material map to default
        return MapMenuMessages.SUCCESS;
    }

    public MapMenuMessages dropRock(int x, int y, String direction) {
        if (!CheckMapCell.validationOfX(x)) return MapMenuMessages.X_OUT_OF_BOUNDS;
        if (!CheckMapCell.validationOfY(y)) return MapMenuMessages.Y_OUT_OF_BOUNDS;
        //TODO check directions and drop the rock
        return MapMenuMessages.SUCCESS;
    }

    public MapMenuMessages dropTree(int x, int y, String type) {
        return null;
    }

    public MapMenuMessages dropBuilding(int x, int y, String type) {
        return null;
    }

    public MapMenuMessages dropUnit(int x, int y, String type, int count) {
        return null;
    }
}
