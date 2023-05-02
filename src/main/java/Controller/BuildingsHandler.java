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
            case "defensive":
                handleDefensiveBuildings(building);
                break;
            case "inn":
                BuildingMenuController.handleInn(building);
                break;
            case "mining":
                handleMiningBuildings(building);
                break;
            case "production":
                BuildingMenuController.handleProductionBuildings(building);
                break;
            case "siege tent":
                BuildingMenuController.handleSiegeTent(building);
                break;
            case "soldier production":;
                BuildingMenuController.handleSoldierProduction(building);
                break;
            case "storage":
                handleStorageBuildings(building);
                break;
        }
    }

    private static void handleStorageBuildings(Building building) {
        switch (building.getBuildingName()) {
            case "caged war dogs":
                BuildingMenuController.handleCagedDogs(building);
                break;
            case "granary":
                BuildingMenuController.handleGranary(building);
                break;
            default:
                break;
        }
    }

    private static void handleMiningBuildings(Building building) {
        switch (building.getBuildingName()) {
            case "oil smelter":
                BuildingMenuController.handleOilSmelter(building);
                break;
            case "iron mine":
                BuildingMenuController.handleIronMine(building);
                break;
            case "quarry":
                BuildingMenuController.handleQuarry(building);
                break;
            default:
                BuildingMenuController.handleMiningBuildings(building);
                break;
                //apple orchard - dairy farmer - hops farmer - hunter post
                //- wheat farmer - pitch rig - woodcutter
        }
    }

    private static void handleDefensiveBuildings(Building building) {
        switch (building.getBuildingName()) {
            //TODO
            case "lookout tower":
                break;
            case "perimeter tower":
                break;
            case "defence turret":
                break;
            case "square tower":
                break;
            case "round tower":
                break;
        }
    }

    public static void handleOtherBuildings(Building building) {
        switch (building.getBuildingName()) {
            case "drawbridge":
                BuildingMenuController.handleDrawBridge(building);
                break;
            case "market":
                BuildingMenuController.handleMarket(building);
                break;
            case "ox tether":
                BuildingMenuController.handleOxTether(building);
                break;
            case "hovel":
                BuildingMenuController.handleHovel(building);
                break;
            default:
                BuildingMenuController.handleReligiousBuildings(building);
                break;
        }
    }
}