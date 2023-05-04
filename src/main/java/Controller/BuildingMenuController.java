package Controller;

import Model.*;
import Model.AttackToolsAndMethods.AttackToolsAndMethods;
import Model.Buildings.*;
import Model.Items.Animal;
import Model.Items.ArmorAndWeapon;
import Model.Items.Item;
import Model.Items.Resource;
import Model.People.*;
import Utils.CheckMapCell;
import View.EmpireMenu;
import View.Enums.Messages.BuildingMenuMessages;
import View.ShopMenu;

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

        int numberOfWorkers = 0;
        for (PeopleType peopleType : buildingSample.getNumberOfWorkers().keySet()) {
            numberOfWorkers += buildingSample.getNumberOfWorkers().get(peopleType);
        }
        if (currentUser.getEmpire().getNormalPeople().size() < numberOfWorkers) return BuildingMenuMessages.NOT_ENOUGH_CROWD;

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

        //TODO تابع بزن برای نوعشون
        Building newBuilding = getTypeBuildings(currentUser, buildingSample, x, y);
        MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x, y);
        mapCell.addBuilding(newBuilding);
        currentUser.getEmpire().addBuilding(newBuilding);

        int counterToWorker = 0;
        for (PeopleType peopleType : buildingSample.getNumberOfWorkers().keySet()) {

            numberOfWorkers = buildingSample.getNumberOfWorkers().get(peopleType);

            makeWorkerAndEngineers(currentUser, mapCell, numberOfWorkers, newBuilding, peopleType);
        }

        return BuildingMenuMessages.SUCCESS;
    }

    private Building getTypeBuildings(User currentUser, Building buildingSample, int x, int y) {
        switch (buildingSample.getCategory()) {
            case "storage":
                return new StorageBuilding(currentUser, x, y,
                Database.getStorageBuildingDataByName(buildingSample.getBuildingName()));
            case "production":
                return new ProductionBuilding(currentUser, x, y,
                Database.getProductionBuildingDataByName(buildingSample.getBuildingName()));
            case "mining":
                return new MiningBuilding(currentUser, x, y,
                Database.getMiningBuildingDataByName(buildingSample.getBuildingName()));
            case "soldier production":
                return new SoldierProduction(currentUser, x, y,
                Database.getSoldierProductionDataByName(buildingSample.getBuildingName()));
            case "defensive":
                return new DefensiveBuilding(currentUser, Direction.directions.NORTH, x, y,
                Database.getDefensiveBuildingDataByName(buildingSample.getBuildingName()));
            case "gate house":
                return new GateHouse(currentUser, x, y,
                Database.getGatehouseBuildingDataByName(buildingSample.getBuildingName()));
            case "other buildings":
                return new OtherBuilding(currentUser, x, y,
                Database.getOtherBuildingDataByName(buildingSample.getBuildingName()));
            case "inn":
                return new Inn(currentUser, Database.getBuildingDataByName(buildingSample.getBuildingName()), x, y);
            case "trap":
                return new Trap(currentUser, Database.getBuildingDataByName(buildingSample.getBuildingName()), x, y);
            case "siege tent":
                return new SiegeTent(currentUser, Database.getBuildingDataByName(buildingSample.getBuildingName()), x, y);
        }
        return null;
    }

    private void makeWorkerAndEngineers(User currentUser, MapCell mapCell, int numberOfWorkers, Building building, PeopleType type) {

        int counterToWorker = 0;

        for (NormalPeople normalPeople : currentUser.getEmpire().getNormalPeople()) {
            for (MapCell cell : Database.getCurrentMapGame().getMapCells()) {
                cell.getPeople().remove(normalPeople);
            }
            currentUser.getEmpire().getNormalPeople().remove(normalPeople);
            Person person;
            if (type.equals(PeopleType.ENGINEER))
                person = new Engineer(normalPeople.getOwner());
            else
                person = new Worker(normalPeople.getOwner(), building);
            mapCell.addPeople(person);
            counterToWorker++;
            if (counterToWorker == numberOfWorkers) break;
        }
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
        !selectedBuilding.getBuildingName().equals("mercenary post") &&
                !selectedBuilding.getBuildingName().equals("engineer guild"))
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

        //TODO: shartre type sarbaza
        int counterToSoldier = 0;
        for (NormalPeople normalPeople : currentUser.getEmpire().getNormalPeople()) {
            for (MapCell cell : Database.getCurrentMapGame().getMapCells()) {
                cell.getPeople().remove(normalPeople);
            }
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
        Objects.requireNonNull(Item.getAvailableItems("oil")).changeNumber(1);
        //TODO
    }

    public static void handleReligiousBuildings(Building building) {
        Empire empire = Database.getLoggedInUser().getEmpire();
        empire.changePopularityRate(2);
        empire.changeReligionRate(2);

        if(building.getBuildingName().equals("Cathedral")) {
            empire.changePopularityRate(2);
            empire.changeReligionRate(2);
        }
    }

    public static void handleOxTether(Building building) {
    }

    public static void handleIronMine(Building building) {
        Objects.requireNonNull(Item.getAvailableItems("iron")).changeNumber(5);
    }

    public static void handleQuarry(Building building) {
        Objects.requireNonNull(Item.getAvailableItems("stone")).changeNumber(5);
        //TODO
    }

    public static void handleMiningBuildings(Building building) {
        Empire empire = Database.getLoggedInUser().getEmpire();
        Item.ItemType itemType = ((MiningBuilding) building).getProduction();
        Item item = Item.getAvailableItems(itemType.getName());
        assert item != null;
        item.changeNumber(5);
    }

    public static void handleMarket(Building building) {
        new ShopMenu().run();
    }

    public static void handleProductionBuildings(Building building) {
        Empire empire = Database.getLoggedInUser().getEmpire();
        HashMap<Item.ItemType, Item.ItemType> production = ((ProductionBuilding) building).getProductionItem();

        for (Item.ItemType itemType : production.keySet()) {
            if(Objects.requireNonNull(Item.getAvailableItems(production.get(itemType).getName())).getNumber() > 0) {
                if(building.getBuildingName().equals("dairy farmer")) {
                    Objects.requireNonNull(Item.getAvailableItems(production.get(itemType).getName())).changeNumber(-1);
                    Objects.requireNonNull(Item.getAvailableItems(itemType.getName())).changeNumber(1);
                    if(itemType.getName().equals("cheese")) {
                        if (((StorageBuilding) empire.getBuildingByName("granary"))
                                .getItemByName(itemType.getName()) != null) {
                            ((StorageBuilding) empire.getBuildingByName("granary")).
                                    addItem(Item.getAvailableItems(itemType.getName()));
                        }
                    }
                    else {
                        if (((StorageBuilding) empire.getBuildingByName(((ProductionBuilding) building).
                                getRelatedStorageBuildingName())).getItemByName(itemType.getName()) != null) {
                            ((StorageBuilding) empire.getBuildingByName(((ProductionBuilding) building).
                                    getRelatedStorageBuildingName())).addItem(Item.getAvailableItems(itemType.getName()));
                        }
                    }
                }
                else {
                    if (empire.getBuildingByName(((ProductionBuilding) building).getRelatedStorageBuildingName()) != null) {
                        Objects.requireNonNull(Item.getAvailableItems(production.get(itemType).getName())).changeNumber(-1);
                        Objects.requireNonNull(Item.getAvailableItems(itemType.getName())).changeNumber(1);
                        if (((StorageBuilding) empire.getBuildingByName(((ProductionBuilding) building).
                                getRelatedStorageBuildingName())).getItemByName(itemType.getName()) != null) {
                            ((StorageBuilding) empire.getBuildingByName(((ProductionBuilding) building).
                                    getRelatedStorageBuildingName())).addItem(Item.getAvailableItems(itemType.getName()));
                        }
                    }
                }
            }
        }
    }

    public static void handleCagedDogs(Building building) {
        int x = building.getX();
        int y = building.getY();
        MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x, y);
        Animal dogs = (new Animal(Animal.animalNames.DOG, Database.getLoggedInUser(), 3));
        mapCell.addItems(dogs);
        Database.getLoggedInUser().getEmpire().addAnimal(dogs);
    }

    public BuildingMenuMessages createAttackTool(int x, int y, String type) {
        if (!CheckMapCell.validationOfX(x)) return BuildingMenuMessages.X_OUT_OF_BOUNDS;
        if (!CheckMapCell.validationOfY(y)) return BuildingMenuMessages.Y_OUT_OF_BOUNDS;
        if(!type.equals("trebuchets") && !type.equals("portable shield") && !type.equals("battering ram")
                && !type.equals("fire ballista") && !type.equals("siege tower") && !type.equals("catapult")) {
            return BuildingMenuMessages.INVALID_TYPE;
        }
        if(!selectedBuilding.getBuildingName().equals("siege tent")) return BuildingMenuMessages.INVALID_TYPE_BUILDING;
        if(selectedBuilding == null) return BuildingMenuMessages.BUILDING_IS_NOT_SELECTED;

        MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x, y);
        if(mapCell.haveBuilding() || mapCell.haveAttackTools() || mapCell.haveMapCellItem())
            return BuildingMenuMessages.CELL_IS_FULL;

        Empire empire = Database.getLoggedInUser().getEmpire();
        int numberOfEngineers = AttackToolsAndMethods.getNumberOfEngineersByName(type);
        if(empire.getEngineers().size() < numberOfEngineers) return BuildingMenuMessages.INSUFFICIENT_ENGINEER;

        int golds = AttackToolsAndMethods.getCostByName(type);
        if(empire.getCoins() < golds) return BuildingMenuMessages.INSUFFICIENT_GOLD;

        //TODO تابع بزن برای نوعشون
        AttackToolsAndMethods attackToolsAndMethods = new AttackToolsAndMethods(Database.getLoggedInUser(),
                Objects.requireNonNull(AttackToolsAndMethods.getAttackToolsAndMethodsTypeByName(type)));

        empire.addAttackToolsAndMethods(attackToolsAndMethods);
        mapCell.addAttackToolsAndMethods(attackToolsAndMethods);
        return BuildingMenuMessages.SUCCESS;
    }
}
