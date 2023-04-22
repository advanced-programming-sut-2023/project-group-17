package Controller;

import Model.Buildings.Building;
import Model.Database;
import Utils.CheckMapCell;
import View.Enums.Messages.BuildingMenuMessages;

public class BuildingMenuController {
    public Building selectedBuilding;

    public BuildingMenuMessages dropBuilding(int x, int y, String type) {
        if (!CheckMapCell.validationOfX(x)) return BuildingMenuMessages.X_OUT_OF_BOUNDS;
        if (!CheckMapCell.validationOfY(y)) return BuildingMenuMessages.Y_OUT_OF_BOUNDS;
        if (!CheckMapCell.mapCellEmptyByCoordinates(x, y)) return BuildingMenuMessages.CELL_IS_FULL;
        //TODO building types
        return BuildingMenuMessages.SUCCESS;
    }

    public BuildingMenuMessages selectBuilding(int x, int y) {
        if (!CheckMapCell.validationOfX(x)) return BuildingMenuMessages.X_OUT_OF_BOUNDS;
        if (!CheckMapCell.validationOfY(y)) return BuildingMenuMessages.Y_OUT_OF_BOUNDS;
        switch (CheckMapCell.mapCellHaveBuildingByCoordinates(x, y, Database.getLoggedInUser())) {
            case NO_BUILDING_IN_THIS_CELL:
                return BuildingMenuMessages.CELL_IS_EMPTY;
            case OPPONENT_BUILDING:
                return BuildingMenuMessages.OPPONENT_BUILDING;
            case SUCCESS:
                selectedBuilding = Database.getCurrentMapGame().getMapCellByCoordinates(x, y).getBuilding();
                break;
        }
        return BuildingMenuMessages.SUCCESS;
    }

    public BuildingMenuMessages createUnit(String type, int count) {
        return null;
    }

    public BuildingMenuMessages repair() {
        return null;
    }
}
