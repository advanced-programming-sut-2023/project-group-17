package Controller;

import Model.Database;

public class DataAnalysisController {
    public int getGatehouseBuildingsSize() {
        return Database.getGateHouses().size();
    }

    public String getGatehouseBuildingNameByNumber(int index) {
        return Database.getGateHouses().get(index).getBuildingName();
    }

    public int getProductionBuildingsSize() {
        return Database.getProductionBuildings().size();
    }

    public String getProductionBuildingNameByNumber(int index) {
        return Database.getProductionBuildings().get(index).getBuildingName();
    }

    public int getDefensiveBuildingsSize() {
        return Database.getDefensiveBuildings().size();
    }

    public String getDefensiveBuildingNameByNumber(int index) {
        return Database.getDefensiveBuildings().get(index).getBuildingName();
    }
    public int getMiningBuildingsSize() {
        return Database.getMiningBuildings().size();
    }

    public String getMiningBuildingNameByNumber(int index) {
        return Database.getMiningBuildings().get(index).getBuildingName();
    }
    public int getOtherBuildingsSize() {
        return Database.getOtherBuildings().size();
    }

    public String getOtherBuildingNameByNumber(int index) {
        return Database.getOtherBuildings().get(index).getBuildingName();
    }
    public int getSoldierBuildingsSize() {
        return Database.getSoldierProductions().size();
    }

    public String getSoldierBuildingNameByNumber(int index) {
        return Database.getSoldierProductions().get(index).getBuildingName();
    }
    public int getStorageBuildingsSize() {
        return Database.getStorageBuildings().size();
    }

    public String getStorageBuildingNameByNumber(int index) {
        return Database.getStorageBuildings().get(index).getBuildingName();
    }
}
