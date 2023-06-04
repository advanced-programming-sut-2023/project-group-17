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
        System.out.println(Database.getProductionBuildings().size());
        return Database.getProductionBuildings().size();
    }

    public String getProductionBuildingNameByNumber(int index) {
        return Database.getProductionBuildings().get(index).getBuildingName();
    }
}
