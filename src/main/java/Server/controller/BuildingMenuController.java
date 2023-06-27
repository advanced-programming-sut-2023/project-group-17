package Server.controller;

import Model.*;
import Model.Buildings.*;
import Model.Items.Animal;
import Model.Items.ArmorAndWeapon;
import Model.Items.Resource;
import Model.MapCellItems.Stair;
import Model.MapCellItems.Wall;
import Model.People.*;
import Utils.CheckMapCell;
import Utils.Pair;
import View.EmpireMenu;
import View.Enums.Messages.BuildingMenuMessages;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.HashMap;

public class BuildingMenuController {
    public static HashMap<Building, ImageView> buildingImageViewHashMap = new HashMap<>();
    public Building selectedBuilding = null;
    public int x = 0;
    public int y = 0;

    public BuildingMenuMessages dropBuilding(int x, int y, String type, ImageView imageView) {

        if (!CheckMapCell.validationOfX(x)) return BuildingMenuMessages.X_OUT_OF_BOUNDS;

        if (!CheckMapCell.validationOfY(y)) return BuildingMenuMessages.Y_OUT_OF_BOUNDS;

        if (Database.getBuildingDataByName(type) == null) return BuildingMenuMessages.INVALID_TYPE;

        if (!CheckMapCell.mapCellEmptyByCoordinates(x, y)) return BuildingMenuMessages.CELL_IS_FULL;

        for (User user : Database.getUsersInTheGame()) {
            Empire empire = user.getEmpire();
            int xSet = empire.getHeadquarter().getX();
            int ySet = empire.getHeadquarter().getY() + 1;
            if (x == xSet && y == ySet) return BuildingMenuMessages.CELL_IS_FULL;
        }

        MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x, y);

        if ((mapCell.getMaterialMap().isWaterZone() && !type.equals("drawbridge") )||
        (!mapCell.getMaterialMap().isWaterZone() && type.equals("drawbridge"))
        ||(type.equals("ironMine") && !mapCell.getMaterialMap().equals(MaterialMap.textureMap.STONE))
        || (type.equals("pitchRig")) && !mapCell.getMaterialMap().equals(MaterialMap.textureMap.PLAIN))
            return BuildingMenuMessages.INAPPROPRIATE_TEXTURE;

        Building buildingSample = Database.getBuildingDataByName(type);
        User currentUser = Database.getCurrentUser();

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
//        newBuilding.setOnFire(true);
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
            else makeUnits(currentUser, mapCell, numberOfWorkers, newBuilding, peopleType, null, null);
        }
//        System.out.println("x : " + x + "\ty : " + y);
//        System.out.println(Database.getCurrentMapGame().getMapCellByCoordinates(x, y).getBuilding().getOwner().getUsername());
        if (buildingSample.getBuildingName().equals("oxTether")) nearEnemyGatehouse(x, y);
        buildingImageViewHashMap.put(newBuilding, imageView);

        return BuildingMenuMessages.SUCCESS;
    }

    private void nearEnemyGatehouse(int x, int y) {
        for (User user : Database.getUsersInTheGame()) {
            Empire empire = user.getEmpire();
            int xHeadquarter = empire.getHeadquarter().getX();
            int yHeadquarter = empire.getHeadquarter().getY();
            if (Math.sqrt(Math.pow((xHeadquarter - x), 2) + Math.pow((yHeadquarter - y), 2)) <= 4 &&
                    !Database.getCurrentUser().getUsername().equals(user.getUsername())) {
                empire.poison();
            }
        }
    }

    public BuildingMenuMessages dropWall(String thickness, String height, int x, int y) {

        if (!CheckMapCell.validationOfX(x)) return BuildingMenuMessages.X_OUT_OF_BOUNDS;

        if (!CheckMapCell.validationOfY(y)) return BuildingMenuMessages.Y_OUT_OF_BOUNDS;

        if (!CheckMapCell.mapCellEmptyByCoordinates(x, y)) return BuildingMenuMessages.CELL_IS_FULL;

        if (Wall.getThickness(thickness) == null) return BuildingMenuMessages.INVALID_TYPE;

        if (Wall.getHeight(height) == null) return BuildingMenuMessages.INVALID_TYPE;

        MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x, y);
        Wall wall = new Wall(Database.getCurrentUser(), Wall.getHeight(height), Wall.getThickness(thickness), x, y);
        mapCell.addMapCellItems(wall);

        return BuildingMenuMessages.SUCCESS;
    }

    public BuildingMenuMessages dropStair(int x, int y) {

        if (!CheckMapCell.validationOfX(x)) return BuildingMenuMessages.X_OUT_OF_BOUNDS;

        if (!CheckMapCell.validationOfY(y)) return BuildingMenuMessages.Y_OUT_OF_BOUNDS;

        if (!CheckMapCell.mapCellEmptyByCoordinates(x, y)) return BuildingMenuMessages.CELL_IS_FULL;

        MapCell mapCell;

        outer :
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (Utils.CheckMapCell.validationOfY(y + j) && Utils.CheckMapCell.validationOfX(x + i)) {
                    mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x + i, y + j);
                    if ((mapCell.getWall() != null) && mapCell.getWall().getOwner().equals(Database.getCurrentUser())) {
                        break outer;
                    } else if (i == 1 && j == 1) return BuildingMenuMessages.THERE_IS_NOT_ANY_WALLS_NEAR;
                }
            }
        }

        Stair stair = new Stair(Database.getCurrentUser(), x, y);
        mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x, y);
        mapCell.addMapCellItems(stair);

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

    private void makeUnits(User currentUser, MapCell mapCell, int numberOfWorkers, Building building, PeopleType type, Soldier soldier, ImageView imageView) {

        int counterToWorker = 0;

        for (NormalPeople normalPeople : currentUser.getEmpire().getNormalPeople()) {
            for (MapCell cell : Database.getCurrentMapGame().getMapCells()) {
                cell.getPeople().remove(normalPeople);
            }
            currentUser.getEmpire().getNormalPeople().remove(normalPeople);
            Person person;
            if (type.equals(PeopleType.SOLDIER)) {
                person = new Soldier(normalPeople.getOwner(), soldier);
                UnitMenuController.soldierImageViewHashMap.put((Soldier)person, imageView);
            } else
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

    public BuildingMenuMessages createUnit(int columnIndex, int rowIndex, String type, int count, ImageView imageView) {

        User currentUser = Database.getCurrentUser();
        Soldier sampleSoldier = Database.getSoldierDataByName(type);

//        if (sampleSoldier == null) return BuildingMenuMessages.INVALID_TYPE;
//
//        if (count <= 0) return BuildingMenuMessages.INVALID_NUMBER;
//
//        if (selectedBuilding == null) return BuildingMenuMessages.BUILDING_IS_NOT_SELECTED;
        x = columnIndex; y = rowIndex;
        MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x, y);

//        if (!selectedBuilding.getCategory().equals("soldier production"))
//            return BuildingMenuMessages.INVALID_TYPE_BUILDING;

        if (currentUser.getEmpire().getNormalPeople().size() < count) return BuildingMenuMessages.NOT_ENOUGH_CROWD;

        for (ArmorAndWeapon armorAndWeapon : currentUser.getEmpire().getWeapons()) {
            if (armorAndWeapon.getItemName().equals(sampleSoldier.getWeapon()) &&
                    sampleSoldier.getNationality().equals(UnitAttributes.Nationality.EUROPEAN)) {
                if (armorAndWeapon.getNumber() < count) return BuildingMenuMessages.INSUFFICIENT_STORAGE;
                else {
                    armorAndWeapon.changeNumber(-count);
                }
            }
        }

//        if (!((SoldierProduction) selectedBuilding).getSoldiersTrained().containsKey(type)) {
//            return BuildingMenuMessages.INVALID_TYPE_BUILDING;
//        }

        makeUnits(currentUser, mapCell, count, selectedBuilding, PeopleType.SOLDIER, sampleSoldier, imageView);

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

//    public static void handleMarket(Building building) {
//        new ShopMenu().run();
//    }

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
        int numberOfEngineers = sampleAttackToolsAndMethods.getNumberOfEngineers();
        if(empire.getEngineers().size() < numberOfEngineers)
            return BuildingMenuMessages.INSUFFICIENT_ENGINEER;

        int golds = sampleAttackToolsAndMethods.getCost();
        if(empire.getCoins() < golds) return BuildingMenuMessages.INSUFFICIENT_GOLD;

        AttackToolsAndMethods sample = Database.getAttackToolsDataByName(type);
        AttackToolsAndMethods attackToolsAndMethods = new AttackToolsAndMethods(Database.getCurrentUser(), sample, x, y);
        for (Engineer engineer : empire.getEngineers()) {
            attackToolsAndMethods.getEngineers().add(engineer);
            Database.getCurrentMapGame().getMapCellByCoordinates(engineer.getX(), engineer.getY()).removePerson(engineer);
            Database.getCurrentMapGame().getMapCellByCoordinates(engineer.getX(), engineer.getY()).addPeople(engineer);
            if(attackToolsAndMethods.getEngineers().size() == numberOfEngineers) break;
        }
        empire.changeCoins(-golds);
        empire.addAttackToolsAndMethods(attackToolsAndMethods);
        mapCell.addAttackToolsAndMethods(attackToolsAndMethods);
        return BuildingMenuMessages.SUCCESS;
    }

    public static void handleCagedDogs(Building building) {
        int x = building.getX();
        int y = building.getY();
        MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x, y);
        Empire empire = Database.getCurrentUser().getEmpire();
        for (int i = 0; i < 5; i++) {
            for (int j = x - i; j < x + i; j++) {
                for (int h = y - i; h < y + i; h++) {
                    if (Utils.CheckMapCell.validationOfY(h) && Utils.CheckMapCell.validationOfX(j)) {
                        mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(j, h);
                        for (Soldier soldier : mapCell.getSoldier()) {
                            Animal dogs = empire.getAnimalByName("dog");
                            ArrayList<MapCell> path =
                                    MoveController.aStarSearch(Database.getCurrentMapGame(), x, y, soldier.getX(), soldier.getY());
                            for (int z = path.size() - 1; z > 0; z--) {
                                path.get(z).removeAnimal(dogs);
                                path.get(z - 1).addAnimal(dogs);

                                if (path.get(z - 1).getBuilding() instanceof Trap &&
                                        !path.get(z - 1).getBuilding().getOwner().equals(dogs.getOwner())) {

                                    path.get(i - 1).removeAnimal(dogs);
                                    dogs.getOwner().getEmpire().removeAnimal(dogs, 3);
                                    return;
                                }
                            }
                            if (path.size() != 0) {
                                soldier.changeHp(-100);
                                if (soldier.getHp() > 0) {
                                    path.get(i - 1).removeAnimal(dogs);
                                    dogs.getOwner().getEmpire().removeAnimal(dogs, 3);
                                    Database.getCurrentMapGame().getMapCellByCoordinates(building.getX(),building.getY())
                                            .removeBuilding(building);
                                    building.getOwner().getEmpire().removeBuilding(building);
                                    return;
                                } else {
                                    soldier.getOwner().getEmpire().removePerson(soldier);
                                    Database.getCurrentMapGame().getMapCellByCoordinates(soldier.getX(), soldier.getY())
                                            .removePerson(soldier);
                                    for (int z = 0; z < path.size() - 1; z++) {
                                        path.get(z).removeAnimal(dogs);
                                        path.get(z + 1).addAnimal(dogs);
                                    }
                                }
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    //TODO: coordinates x = 1 , y = 1 null pointer exception mikhore
    public boolean isThisUserBuilding(int i, int j) {
//        System.out.println("i : " + i + "\tj : " + j);
//        System.out.println("Current user : " + Database.getCurrentUser().getUsername());
//        System.out.println("Building owner : " + Database.getCurrentMapGame().getMapCellByCoordinates(i, j).getBuilding().
//                getOwner().getUsername());
        return Database.getCurrentMapGame().getMapCellByCoordinates(i, j).getBuilding().
                getOwner().equals(Database.getCurrentUser());
    }

    public String dropViaPaste(int droppedX, int droppedY, int copyX, int copyY, ImageView imageView) {
        MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(copyX, copyY);
        if (mapCell.getBuilding() == null || !mapCell.getBuilding().getOwner().equals(Database.getCurrentUser()))
            return null;
        if (dropBuilding(droppedX, droppedY, mapCell.getBuilding().getBuildingName(), imageView).equals(BuildingMenuMessages.SUCCESS))
            return mapCell.getBuilding().getBuildingName();
        return null;
    }

    public boolean poisonCell(int x, int y) {
        for (User user : Database.getUsersInTheGame()) {
            Empire empire = user.getEmpire();
            int xHeadquarter = empire.getHeadquarter().getX();
            int yHeadquarter = empire.getHeadquarter().getY();
            if (Math.sqrt(Math.pow((xHeadquarter - x), 2) + Math.pow((yHeadquarter - y), 2)) <= 4 &&
                    !Database.getCurrentUser().getUsername().equals(user.getUsername())) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Pair> getBuildingsOnFire() {
        ArrayList<Pair> pairs = new ArrayList<>();
        for (int i = 0; i < Database.getCurrentMapGame().getWidth(); i++) {
            for (int j = 0; j < Database.getCurrentMapGame().getLength(); j++) {
                MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(i + 1, j + 1);
                if (mapCell.haveBuilding() && mapCell.getBuilding().isOnFire()) {
                    Pair pair = new Pair(i, j);
                    pairs.add(pair);
                }
            }
        }
        return pairs;
    }

    public void setImageForBuildings(ImageView imageView, int xBuildingForHeadquarter, int yBuildingForHeadquarter) {
        MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(xBuildingForHeadquarter, yBuildingForHeadquarter);
        buildingImageViewHashMap.put(mapCell.getBuilding(), imageView);
    }
}
