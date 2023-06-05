package Controller;

import Model.Buildings.SoldierProduction;
import Model.Database;

import java.util.ArrayList;
import java.util.HashMap;

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

    public String getTypeBuilding(String name) {
        return Database.getBuildingDataByName(name).getCategory();
    }

    public ArrayList<String> getSoldierNames(String buildingName) {
        SoldierProduction soldierProduction = Database.getSoldierProductionDataByName(buildingName);
        ArrayList<String> soldiers = new ArrayList<>();
        for (String soldier : soldierProduction.getSoldiersTrained().keySet()) {
            soldiers.add(soldier);
        }
        return soldiers;
    }

    public int getXHeadquarter() {
        return Database.getCurrentUser().getEmpire().getHeadquarter().getX();
    }

    public int getYHeadquarter() {
        return Database.getCurrentUser().getEmpire().getHeadquarter().getY();
    }
}
