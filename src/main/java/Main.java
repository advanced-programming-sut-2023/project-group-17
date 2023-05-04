import Model.Database;
import View.LoginMenu;

import static View.Menu.scanner;

public class Main {
    public static void main(String[] args) {
        Database.loadBuildings();
        Database.loadUnits();
        Database.saveBuildingsTypes();
        Database.addDefensiveBuilding();
        Database.addStorageBuilding();
        Database.addGatehouseBuilding();
        Database.addMiningBuilding();
        Database.addOtherBuilding();
        Database.addProductionBuilding();
        Database.addSoldierBuilding();
        new LoginMenu().run();
    }
}