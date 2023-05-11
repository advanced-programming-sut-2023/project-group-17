package Controller;

import Model.*;
import Model.Buildings.*;
import Model.Items.Animal;
import Model.Items.ArmorAndWeapon;
import Model.Items.Resource;
import Model.MapCellItems.Wall;
import Model.People.*;
import Utils.CheckMapCell;
import View.EmpireMenu;
import View.Enums.Messages.BuildingMenuMessages;
import View.ShopMenu;

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
        User currentUser = Database.getCurrentUser();

        if (currentUser.getEmpire().getEngineers().size() < 1) return BuildingMenuMessages.NOT_ENOUGH_ENGINEERS;

        int numberOfWorkers = 0;
        for (PeopleType peopleType : buildingSample.getNumberOfWorkers().keySet()) {
            if (peopleType.equals(PeopleType.ENGINEER)) {
                if (currentUser.getEmpire().getEngineers().size() < buildingSample.getNumberOfWorkers().get(peopleType)) {
                    return BuildingMenuMessages.NOT_ENOUGH_ENGINEERS;
                }
            }
            else numberOfWorkers += buildingSample.getNumberOfWorkers().get(peopleType);
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

        Building newBuilding = getTypeBuildings(currentUser, buildingSample, x, y);
        MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x, y);
        mapCell.addBuilding(newBuilding);
        currentUser.getEmpire().addBuilding(newBuilding);

        for (PeopleType peopleType : buildingSample.getNumberOfWorkers().keySet()) {

            if (peopleType.equals(PeopleType.ENGINEER)) {
                int counter = 0;
                for (Engineer engineer : currentUser.getEmpire().getEngineers()) {
                    counter++;
                    MapCell previousMapCell =
                            Database.getCurrentMapGame().getMapCellByCoordinates(engineer.getX(), engineer.getY());
                    previousMapCell.removePerson(engineer);
                    mapCell.addPeople(engineer);
                    if (counter == buildingSample.getNumberOfWorkers().get(peopleType)) break;
                }
            }
            else makeUnits(currentUser, mapCell, numberOfWorkers, newBuilding, peopleType, null);
        }

        return BuildingMenuMessages.SUCCESS;
    }
    public BuildingMenuMessages dropWall(String thickness, String height, int x, int y) {

        if (!CheckMapCell.validationOfX(x)) return BuildingMenuMessages.X_OUT_OF_BOUNDS;

        if (!CheckMapCell.validationOfY(y)) return BuildingMenuMessages.Y_OUT_OF_BOUNDS;

        if (!CheckMapCell.mapCellEmptyByCoordinates(x, y)) return BuildingMenuMessages.CELL_IS_FULL;

        if (Wall.getThickness(thickness) == null) return BuildingMenuMessages.INVALID_TYPE;

        if (Wall.getHeight(height) == null) return BuildingMenuMessages.INVALID_TYPE;

        MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x, y);
        Wall wall = new Wall(Database.getLoggedInUser(), Wall.getHeight(height), Wall.getThickness(thickness));
        mapCell.addMapCellItems(wall);

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

    private void makeUnits(User currentUser, MapCell mapCell, int numberOfWorkers, Building building, PeopleType type, Soldier soldier) {

        int counterToWorker = 0;

        for (NormalPeople normalPeople : currentUser.getEmpire().getNormalPeople()) {
            for (MapCell cell : Database.getCurrentMapGame().getMapCells()) {
                cell.getPeople().remove(normalPeople);
            }
            currentUser.getEmpire().getNormalPeople().remove(normalPeople);
            Person person;
            if (type.equals(PeopleType.SOLDIER))
                person = new Soldier(normalPeople.getOwner(), soldier);
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

        switch (CheckMapCell.mapCellHaveBuildingByCoordinates(x, y, Database.getCurrentUser())) {
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

        User currentUser = Database.getCurrentUser();
        Soldier sampleSoldier = Database.getSoldierDataByName(type);

        if (sampleSoldier == null) return BuildingMenuMessages.INVALID_TYPE;

        if (count <= 0) return BuildingMenuMessages.INVALID_NUMBER;

        if (selectedBuilding == null) return BuildingMenuMessages.BUILDING_IS_NOT_SELECTED;
        MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x, y);

        if (!selectedBuilding.getCategory().equals("soldier production"))
            return BuildingMenuMessages.INVALID_TYPE_BUILDING;

        if (currentUser.getEmpire().getNormalPeople().size() < count) return BuildingMenuMessages.NOT_ENOUGH_CROWD;

        for (ArmorAndWeapon armorAndWeapon : currentUser.getEmpire().getWeapons()) {
            if (armorAndWeapon.getItemName().equals(sampleSoldier.getWeapon())) {
                if (armorAndWeapon.getNumber() < count) return BuildingMenuMessages.INSUFFICIENT_STORAGE;
                else {
                    armorAndWeapon.changeNumber(-count);
                }
            }
        }

        if (!((SoldierProduction) selectedBuilding).getSoldiersTrained().containsKey(type)) {
            return BuildingMenuMessages.INVALID_TYPE_BUILDING;
        }

        makeUnits(currentUser, mapCell, count, selectedBuilding, PeopleType.SOLDIER, sampleSoldier);

        return BuildingMenuMessages.SUCCESS;
    }

    public BuildingMenuMessages repair() {
        if (selectedBuilding == null) return BuildingMenuMessages.BUILDING_IS_NOT_SELECTED;

        MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x, y);
        User currentUser = Database.getCurrentUser();

        int damaged = Database.getBuildingDataByName(selectedBuilding.getBuildingName()).getBuildingHp()
                    - selectedBuilding.getBuildingHp();

        for (Soldier soldier : mapCell.getSoldier()) {
            if (!soldier.getOwner().equals(selectedBuilding.getOwner()))
                return BuildingMenuMessages.OPPONENT_SOLDIER_AROUND;
        }

        for (Resource resource : currentUser.getEmpire().getResources()) {
            if (resource.getItemName().equals("stone")) {
                if (resource.getNumber() < Math.ceil(damaged * 0.1)) return BuildingMenuMessages.INSUFFICIENT_STONE;
                else {
                    resource.changeNumber(-Math.ceil(damaged * 0.1));
                }
            }
        }
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

    public static void handleOxTether(Building building) {
        //TODO
    }

    public static void handleMarket(Building building) {
        new ShopMenu().run();
    }

    public BuildingMenuMessages createAttackTool(int x, int y, String type) {
        if (!CheckMapCell.validationOfX(x)) return BuildingMenuMessages.X_OUT_OF_BOUNDS;
        if (!CheckMapCell.validationOfY(y)) return BuildingMenuMessages.Y_OUT_OF_BOUNDS;

        AttackToolsAndMethods sampleAttackToolsAndMethods = Database.getAttackToolsDataByName(type);
        if (sampleAttackToolsAndMethods == null) return BuildingMenuMessages.INVALID_TYPE;

        if(selectedBuilding == null) return BuildingMenuMessages.BUILDING_IS_NOT_SELECTED;

        if(!selectedBuilding.getBuildingName().equals("siege tent")) return BuildingMenuMessages.INVALID_TYPE_BUILDING;

        MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x, y);
        if(!Utils.CheckMapCell.mapCellEmptyByCoordinates(x, y))
            return BuildingMenuMessages.CELL_IS_FULL;

        Empire empire = Database.getCurrentUser().getEmpire();
        if(empire.getEngineers().size() < sampleAttackToolsAndMethods.getNumberOfEngineers())
            return BuildingMenuMessages.INSUFFICIENT_ENGINEER;

        int golds = sampleAttackToolsAndMethods.getCost();
        if(empire.getCoins() < golds) return BuildingMenuMessages.INSUFFICIENT_GOLD;

        AttackToolsAndMethods sample = Database.getAttackToolsDataByName(type);
        AttackToolsAndMethods attackToolsAndMethods = new AttackToolsAndMethods(Database.getCurrentUser(), sample);

        empire.addAttackToolsAndMethods(attackToolsAndMethods);
        mapCell.addAttackToolsAndMethods(attackToolsAndMethods);
        return BuildingMenuMessages.SUCCESS;
    }

    public static void handleCagedDogs(Building building) {
        int x = building.getX();
        int y = building.getY();
        MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x, y);
        Animal dogs = (new Animal(Animal.animalNames.DOG, Database.getCurrentUser(), 3));
        mapCell.addItems(dogs);
        Database.getCurrentUser().getEmpire().addAnimal(dogs);
    }
}
