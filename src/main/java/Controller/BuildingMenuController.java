package Controller;

import Model.Buildings.Building;
import Model.Buildings.GateHouse;
import Model.Buildings.OtherBuilding;
import Model.Buildings.ProductionBuilding;
import Model.Database;
import Model.Empire;
import Model.Items.ArmorAndWeapon;
import Model.Items.Item;
import Model.Items.Resource;
import Model.MapCell;
import Model.People.NormalPeople;
import Model.People.Person;
import Model.People.Soldier;
import Model.User;
import Utils.CheckMapCell;
import View.EmpireMenu;
import View.Enums.Messages.BuildingMenuMessages;
import View.ShopMenu;

import javax.management.NotificationEmitter;
import java.util.HashMap;
import java.util.Objects;

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

        Building newBuilding = new Building(currentUser, buildingSample, x, y);
        MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x, y);
        mapCell.addBuilding(newBuilding);
        currentUser.getEmpire().addBuilding(newBuilding);

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

        BuildingsHandler.handleBuildingFunction(selectedBuilding);
        return BuildingMenuMessages.SUCCESS;
    }

    public BuildingMenuMessages createUnit(String type, int count) {

        User currentUser = Database.getLoggedInUser();
        Soldier sampleSoldier = Database.getSoldierDataByName(type);

        if (sampleSoldier == null) return BuildingMenuMessages.INVALID_TYPE;

        if (count <= 0) return BuildingMenuMessages.INVALID_NUMBER;

        if (selectedBuilding == null) return BuildingMenuMessages.BUILDING_IS_NOT_SELECTED;
        MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x, y);

        if (!selectedBuilding.getBuildingName().equals("barracks") &&
        !selectedBuilding.getBuildingName().equals("mercenary post") && !selectedBuilding.getBuildingName().equals("engineer guild"))
            return BuildingMenuMessages.INVALID_TYPE_BUILDING;

        if (currentUser.getEmpire().getNormalPeople().size() < count) return BuildingMenuMessages.NOT_ENOUGH_CROWD;

        for (ArmorAndWeapon armorAndWeapon : currentUser.getEmpire().getWeapons()) {
            if (armorAndWeapon.getItemName().equals(sampleSoldier.getName())) {
                if (armorAndWeapon.getNumber() < count) return BuildingMenuMessages.INSUFFICIENT_STORAGE;
                else {
                    armorAndWeapon.changeNumber(-count);
                }
            }
        }

        int counterToSoldier = 0;
        for (NormalPeople normalPeople : currentUser.getEmpire().getNormalPeople()) {
            //TODO remove normal people from map around fire
            Soldier soldier = new Soldier(normalPeople.getOwner(), sampleSoldier);
            currentUser.getEmpire().getNormalPeople().remove(normalPeople);
            mapCell.addPeople(soldier);
            counterToSoldier++;
            if (counterToSoldier == count) break;
        }

        return BuildingMenuMessages.SUCCESS;
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



    public static void handleGateHouse(Building building) {
        new EmpireMenu().run();
    }

    public static void handleDrawBridge(Building building) {
        for(int i = building.getX()-1; i <= building.getX()+1; i++) {
            for(int j = building.getY()-1; j <= building.getY()+1; j++) {
                if(Utils.CheckMapCell.validationOfX(i) && Utils.CheckMapCell.validationOfY(j)) {
                    for (Soldier soldier : Database.getCurrentMapGame().
                            getMapCellByCoordinates(i, j).getSoldier()) {
                        if (!soldier.getOwner().equals(Database.getLoggedInUser())) {
                            soldier.changeSpeed(-1);
                        }
                    }
                }
            }
        }
    }

    public static void handleInn(Building building) {
        Database.getLoggedInUser().getEmpire().changePopularityRate(5);
    }

    public static void handleOilSmelter(Building building) {
        //TODO
    }

    public static void handleReligiousBuildings(Building building) {
        Database.getLoggedInUser().getEmpire().changePopularityRate(2);

        if(building.getBuildingName().equals("Cathedral")) Database.getLoggedInUser().getEmpire().changePopularityRate(2);

        //TODO: handle راهبان مبارز in constructor
    }

    public static void handleOxTether(Building building) {
    }

    public static void handleIronMine(Building building) {
        //TODO
    }

    public static void handleQuarry(Building building) {
        //TODO
    }

    public static void handleMiningBuildings(Building building) {
        //TODO
    }

    public static void handleMarket(Building building) {
        new ShopMenu().run();
    }

    public static void handleProductionBuildings(Building building) {
        Empire empire = Database.getLoggedInUser().getEmpire();
        HashMap<Item.ItemType, Item.ItemType> production = ((ProductionBuilding) building).getProductionItem();

        for (Item.ItemType itemType : production.keySet()) {
            //TODO: if storage building exists
            if(Item.getAvailableItems(production.get(itemType).getName()).getNumber() > 0) {
                Objects.requireNonNull(Item.getAvailableItems(production.get(itemType).getName())).changeNumber(-1);
                Objects.requireNonNull(Item.getAvailableItems(itemType.getName())).changeNumber(1);
                //TODO:
                if(((ProductionBuilding) building).getItemByName(itemType.getName()) != null) {
                    ((ProductionBuilding) building).addItemToStorage(Item.getAvailableItems(itemType.getName()));
                }
            }
        }
    }

    public static void handleSiegeTent(Building building) {
    }

    public static void handleSoldierProduction(Building building) {
    }

    public static void handleCagedDogs(Building building) {
    }

    public static void handleGranary(Building building) {
    }
}
