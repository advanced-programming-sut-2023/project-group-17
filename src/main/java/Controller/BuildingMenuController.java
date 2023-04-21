package Controller;

import Model.Buildings.Building;
import Utils.CheckMapCell;
import View.Enums.Messages.BuildingMenuMessages;

public class BuildingMenuController {
    Building selectedBuilding;

    public BuildingMenuMessages dropBuilding(int x, int y, String type) {
        if (!CheckMapCell.validationOfX(x)) return BuildingMenuMessages.X_OUT_OF_BOUNDS;
        if (!CheckMapCell.validationOfY(y)) return BuildingMenuMessages.Y_OUT_OF_BOUNDS;
        if (!CheckMapCell.mapCellEmptyByCoordinates(x, y)) return BuildingMenuMessages.CELL_IS_FULL;
        //TODO building types
    }

    public BuildingMenuMessages selectBuilding(int x, int y) {
        return null;
    }

    public BuildingMenuMessages createUnit(String type, int count) {
        return null;
    }

    public BuildingMenuMessages repair() {
        return null;
    }
}
