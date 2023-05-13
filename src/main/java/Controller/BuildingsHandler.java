package Controller;

import Model.Buildings.Building;

public class BuildingsHandler {
    public static void handleBuildingFunction(Building building) {

        switch (building.getCategory()) {
            case "gate house":
                BuildingMenuController.handleGateHouse(building);
                break;
            case "other buildings":
                handleOtherBuildings(building);
                break;
            case "storage":
                handleStorageBuildings(building);
                break;
            default:
                break;
        }
    }

    private static void handleStorageBuildings(Building building) {
        if (building.getBuildingName().equals("caged war dogs")) {
            BuildingMenuController.handleCagedDogs(building);
        }
    }

    public static void handleOtherBuildings(Building building) {
        if (building.getBuildingName().equals("market")) {
            BuildingMenuController.handleMarket(building);
        }
    }
}
