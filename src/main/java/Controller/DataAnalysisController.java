package Controller;

import Model.Buildings.Building;
import Model.Buildings.SoldierProduction;
import Model.Database;
import Model.Items.Item;
import Model.People.Soldier;

import java.util.ArrayList;

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
        System.out.println("building name : " + Database.getOtherBuildings().get(index).getBuildingName());
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

    public ArrayList<Item.ItemType> getItemsName() {
        ArrayList<Item.ItemType> item = new ArrayList<>();
        item.add(Item.ItemType.BOW);
        item.add(Item.ItemType.CROSSBOW);
        item.add(Item.ItemType.PIKE);
        item.add(Item.ItemType.SPEAR);
        item.add(Item.ItemType.MACE);
        item.add(Item.ItemType.SWORDS);
        item.add(Item.ItemType.LEATHER_ARMOR);
        item.add(Item.ItemType.METAL_ARMOR);

        item.add(Item.ItemType.WHEAT);
        item.add(Item.ItemType.FLOUR);
        item.add(Item.ItemType.HOPS);
        item.add(Item.ItemType.ALE);
        item.add(Item.ItemType.STONE);
        item.add(Item.ItemType.IRON);
        item.add(Item.ItemType.WOOD);
        item.add(Item.ItemType.PITCH);

        item.add(Item.ItemType.MEAT);
        item.add(Item.ItemType.CHEESE);
        item.add(Item.ItemType.BREAD);
        item.add(Item.ItemType.APPLE);
        return item;
    }

    public boolean isSoldierName(String name) {
        for (Soldier soldier : Database.getAllSoldiers()) {
            if (soldier.getName().equals(name)) return true;
        }
        return false;
    }

    public boolean isBuildingName(String name) {
        for (Building building : Database.getBuildings()) {
            if (building.getBuildingName().equals(name)) return true;
        }
        return false;
    }

    public ArrayList<String> getCaptchas() {
        return Database.getCaptcha();
    }
}
