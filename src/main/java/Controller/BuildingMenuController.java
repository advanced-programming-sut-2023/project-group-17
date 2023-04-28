package Controller;

import Model.Buildings.Building;
import Model.Database;
import Model.Items.Resource;
import Model.MapCell;
import Model.People.Soldier;
import Model.User;
import Utils.CheckMapCell;
import View.Enums.Messages.BuildingMenuMessages;

public class BuildingMenuController {
    public Building selectedBuilding = null;
    public int x = 0;
    public int y = 0;

    public BuildingMenuMessages dropBuilding(int x, int y, String type) {
        if (!CheckMapCell.validationOfX(x)) return BuildingMenuMessages.X_OUT_OF_BOUNDS;
        if (!CheckMapCell.validationOfY(y)) return BuildingMenuMessages.Y_OUT_OF_BOUNDS;
        if (Database.getBuildingDataByName(type) == null) return BuildingMenuMessages.INVALID_TYPE;
        if (!CheckMapCell.mapCellEmptyByCoordinates(x, y)) return BuildingMenuMessages.CELL_IS_FULL;
        Building buildingSample = Database.getBuildingDataByName(type);
        User currentUser = Database.getLoggedInUser();

        for (Resource.resourceType recourseRequired : buildingSample.getBuildingCost().keySet()) {
            for (Resource currentResource : currentUser.getEmpire().getResources()) {
                if (recourseRequired.getName().equals(currentResource.getItemName())) {
                    if (buildingSample.getBuildingCost().get(recourseRequired) > currentResource.getNumber())
                        return BuildingMenuMessages.INSUFFICIENT_STORAGE;
                    else {
                        currentResource.changeNumber(-buildingSample.getBuildingCost().get(recourseRequired));
                    }
                }
            }
        }

        Building newBuilding = new Building(currentUser, buildingSample);
        MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x, y);
        mapCell.addBuilding(newBuilding);

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
                this.x = x;
                this.y = y;
                break;
        }
        return BuildingMenuMessages.SUCCESS;
    }

    public BuildingMenuMessages createUnit(String type, int count) {
        return null;
    }

    public BuildingMenuMessages repair() {
        if (selectedBuilding == null) return BuildingMenuMessages.BUILDING_IS_NOT_SELECTED;

        MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x, y);
        User currentUser = Database.getLoggedInUser();

        int damaged = Database.getBuildingDataByName(selectedBuilding.getBuildingName()).getBuildingHp()
                    - selectedBuilding.getBuildingHp();

        for (Soldier soldier : mapCell.getSoldier()) {
            if (!soldier.getOwner().equals(selectedBuilding.getOwner()))
                return BuildingMenuMessages.OPPONENT_SOLDIER_AROUND;
        }

        for (Resource resource : currentUser.getEmpire().getResources()) {
            if (resource.getItemName().equals("stone")) {
                if (resource.getNumber() < damaged) return BuildingMenuMessages.INSUFFICIENT_STONE;
                else {
                    resource.changeNumber(-damaged);
                }
            }
        }
        //TODO stone for each damaged
        return BuildingMenuMessages.SUCCESS;
    }

    public void back() {
        selectedBuilding = null;
        x = 0;
        y = 0;
    }
}
