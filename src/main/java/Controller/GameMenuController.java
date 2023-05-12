package Controller;

import Model.AttackToolsAndMethods;
import Model.Buildings.*;
import Model.*;
import Model.Items.Animal;
import Model.Items.Item;
import Model.MapCellItems.MapCellItems;
import Model.MapCellItems.Wall;
import Model.MapGeneration.MapOrganizer;
import Model.People.King;
import Model.People.Person;
import Model.People.Soldier;
import Utils.CheckMapCell;
import View.Enums.Messages.GameMenuMessages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class GameMenuController {
    public GameMenuMessages chooseMapGame(int id) {
        if(id > Database.getMapId().size() || id < 1) return GameMenuMessages.INVALID_MAP_NUMBER;
        MapOrganizer.loadMap(String.valueOf(id));
        return GameMenuMessages.SUCCESS;
    }

    public GameMenuMessages showMap(int x, int y) {
        if (!CheckMapCell.validationOfX(x)) return GameMenuMessages.X_OUT_OF_BOUNDS;
        if (!CheckMapCell.validationOfY(y)) return GameMenuMessages.Y_OUT_OF_BOUNDS;
        return GameMenuMessages.SUCCESS;
    }

    public GameMenuMessages createNewMap(int width, int length) {
        if(width <= 0) {
            return GameMenuMessages.INVALID_WIDTH;
        }

        if(length <= 0) {
            return GameMenuMessages.INVALID_LENGTH;
        }

        Map map = new Map(length, width);
        Database.getAllMaps().add(map);
        Database.setCurrentMapGame(map);
        return GameMenuMessages.SUCCESS;
    }

    public boolean nextTurn() {
        //TODO: deal with whose turn is it
        //TODO: check if king is alive or not
        //TODO: set currentUser to loggedInUser
        //TODO turns--
        Database.increaseTurnsPassed();

        if(Database.getTurnsPassed() % Database.getUsersInTheGame().size() == 0) {
            for (User user : Database.getUsersInTheGame()) {
                changePopularity(user.getEmpire());
                handleFearRate(user.getEmpire());
                getTax(user.getEmpire());
            }
            applyMoves();
            for (User user : Database.getUsersInTheGame()) {
                if(user.getEmpire().getBuildingByName("drawbridge") != null)
                    handleDrawBridge(user.getEmpire().getBuildingByName("drawbridge"));
            }
            applyDamageToSoldiers();
            applyDamageToBuildings();
            applyDamageToAttackToolsAndMethods();
            applyDamageByAttackToolsAndMethods();
            removeDeadSoldiers();
            removeDestroyedBuildings();
            removeDestroyedAttackToolsAndMethods();
        }
        buildingsFunctionsEachTurn();
        if (gameIsFinished()) {
            setScores();
            return true;
        }
        int index = Database.getUsersInTheGame().indexOf(Database.getCurrentUser());
        int size = Database.getUsersInTheGame().size();
        Database.setCurrentUser(Database.getUsersInTheGame().get((index + 1) % size));
        return false;
    }

    private void applyMoves() {
        Map map = Database.getCurrentMapGame();
        MapCell mapCell;
        ArrayList<MapCell> path = null;
        AttackToolsAndMethods attackToolsAndMethods = null;
        for (int i = 1; i <= map.getWidth(); i++) {
            for (int j = 1; j <= map.getLength(); j++) {
                mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(i, j);
                for (Person person : mapCell.getPeople()) {
                    if (person.getDestination() != null) {
                        path = MoveController.aStarSearch(map, mapCell.getX(),
                                mapCell.getY(), person.getDestination().getX(), person.getDestination().getY());
                        movePerson(person, path);
                    }
                }
                if (mapCell.getAttackToolsAndMethods() != null) {
                    attackToolsAndMethods = mapCell.getAttackToolsAndMethods();
                    if (attackToolsAndMethods.getDestination() != null) {
                        path = MoveController.aStarSearch(map, mapCell.getX(),
                                mapCell.getY(), attackToolsAndMethods.getDestination().getX(),
                                attackToolsAndMethods.getDestination().getY());
                        moveAttackToolsAndMethods(mapCell.getAttackToolsAndMethods(), path);
                    }
                }
            }
        }
    }

    private void movePerson(Person person, ArrayList<MapCell> path) {
        int counter = 0;
        for(int i = path.size() - 1; i > 0; i--) {
            path.get(i).removePerson(person);
            path.get(i-1).addPeople(person);

            if (path.get(i - 1).getBuilding() instanceof Trap &&
                    !path.get(i - 1).getBuilding().getOwner().equals(person.getOwner())) {

                path.get(i - 1).removePerson(person);

                person.getOwner().getEmpire().removePerson(person);

                if (person instanceof King) {
                    path.get(i - 1).getBuilding().getOwner().getEmpire().increaseNumberOfKingsKilled();
                    person.getOwner().getEmpire().killedKing();
                }

                break;
            }

            counter++;
            if (person instanceof Soldier && counter >= ((Soldier) person).getSpeed()) break;
        }
    }

    private void moveAttackToolsAndMethods(AttackToolsAndMethods attackToolsAndMethods, ArrayList<MapCell> path) {
        int counter = 0;
        for(int i = path.size() - 1; i > 0; i--) {
            path.get(i).setAttackToolsAndMethods(null);
            path.get(i-1).setAttackToolsAndMethods(attackToolsAndMethods);
            counter++;
            if (counter >= attackToolsAndMethods.getSpeed()) break;
        }
    }

    public void applyDamageToSoldiers() {
        for (MapCell mapCell : Database.getCurrentMapGame().getMapCells()) {
            for (Soldier soldier : mapCell.getSoldier()) {
                mapIterationOnSoldiers(mapCell.getX(), mapCell.getY(), soldier);
            }
        }
    }

    public void mapIterationOnSoldiers(int startX, int startY, Soldier soldier) {
        int range = soldier.getAttackRange();
        outerLoop:
        for(int x = startX - range; x < startX + range + 1; x++) {
            for(int y = startY - range; y < startY + range + 1; y++) {
                if(!Utils.CheckMapCell.validationOfX(x) || !Utils.CheckMapCell.validationOfY(y)) continue;

                for (Person person : Database.getCurrentMapGame().getMapCellByCoordinates(x, y).getPeople()) {
                    if(!soldier.getOwner().equals(person.getOwner())) {
                        person.changeHp(-soldier.getAttackRating());
                        break outerLoop;
                    }
                }
            }
        }
    }

    public int removeDeadSoldiers() {
        for (MapCell mapCell : Database.getCurrentMapGame().getMapCells()) {
            for (Person person : mapCell.getPeople()) {
                if(person.getHp() <= 0) {
                    mapCell.removePerson(person);
                    person.getOwner().getEmpire().removePerson(person);
                    return removeDeadSoldiers();
                }
            }
        }
        return -1;
    }

    public void applyDamageToBuildings() {
        int startX, startY;
        for (MapCell mapCell : Database.getCurrentMapGame().getMapCells()) {
            startX = mapCell.getX();
            startY = mapCell.getY();
            if(mapCell.getBuilding() != null) {
                if(mapCell.getBuilding() instanceof DefensiveBuilding)
                    applyDamageDefensiveBuildings(startX, startY, mapCell, ((DefensiveBuilding) mapCell.getBuilding()).getDefenceRange());
                else applyDamageOtherBuildings(startX, startY, mapCell);
            }
            else
                applyDamageWalls(startX, startY, mapCell);
        }
    }

    public void applyDamageDefensiveBuildings(int startX, int startY, MapCell mapCell, int range) {
        int distance;
        for(int x = startX - range; x < startX + range + 1; x++) {
            for(int y = startY - range; y < startY + range + 1; y++) {
                if(!Utils.CheckMapCell.validationOfX(x) || !Utils.CheckMapCell.validationOfY(y)) continue;

                distance = ((int)Math.sqrt(Math.pow(startX - x, 2) + Math.pow(startY - y, 2)));
                for (Soldier soldier : Database.getCurrentMapGame().getMapCellByCoordinates(x, y).getSoldier()) {
                    if(!soldier.getOwner().equals(mapCell.getBuilding().getOwner()) && soldier.getAttackRange() >= distance)
                        mapCell.getBuilding().changeBuildingHp(-soldier.getAttackRating());
                }
            }
        }
    }

    public void applyDamageWalls(int startX, int startY, MapCell mapCell) {
        int range;
        for (Soldier soldier : mapCell.getSoldier()) {
            range = soldier.getAttackRange();
            if(range == 1)
                for(int x = startX - range; x < startX + range + 1; x++) {
                    for(int y = startY - range; y < startY + range + 1; y++) {
                        if(!Utils.CheckMapCell.validationOfX(x) || !Utils.CheckMapCell.validationOfY(y)) continue;

                        for (MapCellItems mapCellItem : Database.getCurrentMapGame().getMapCellByCoordinates(x, y).getMapCellItems()) {
                            if(mapCellItem instanceof Wall && !soldier.getOwner().equals(mapCellItem.getOwner()))
                                ((Wall) mapCellItem).changeHp(-soldier.getAttackRating());
                        }                    }
                }
        }
    }

    public void applyDamageOtherBuildings(int startX, int startY, MapCell mapCell) {
        int range;
        for (Soldier soldier : mapCell.getSoldier()) {
            range = soldier.getAttackRange();
            for(int x = startX - range; x < startX + range + 1; x++) {
                for(int y = startY - range; y < startY + range + 1; y++) {
                    if(!Utils.CheckMapCell.validationOfX(x) || !Utils.CheckMapCell.validationOfY(y)) continue;

                    Building building = Database.getCurrentMapGame().getMapCellByCoordinates(x, y).getBuilding();
                        if(building != null && !soldier.getOwner().equals(building.getOwner()))
                            building.changeBuildingHp(-soldier.getAttackRating());
                }
            }
        }
    }

    public int removeDestroyedBuildings() {
        for (MapCell mapCell : Database.getCurrentMapGame().getMapCells()) {
            if(mapCell.getBuilding() != null && mapCell.getBuilding().getBuildingHp() <= 0) {
                mapCell.getBuilding().getOwner().getEmpire().removeBuilding(mapCell.getBuilding());
                mapCell.setBuilding(null);
                return removeDestroyedBuildings();
            }
        }
        return -1;
    }

    //TODO: Siege Tower and Portable Shield?

    public void applyDamageByAttackToolsAndMethods() {
        for (MapCell mapCell : Database.getCurrentMapGame().getMapCells()) {
            if(mapCell.getAttackToolsAndMethods() != null)
                if(mapCell.getAttackToolsAndMethods().getName().equals("battering ram"))
                    applyDamageBatteringRam(mapCell.getX(), mapCell.getY(), mapCell.getAttackToolsAndMethods());
                else
                    applyDamageByAttackToolsToSoldiersAndBuildings(mapCell.getX(), mapCell.getY(), mapCell.getAttackToolsAndMethods());
        }
    }

    public void applyDamageByAttackToolsToSoldiersAndBuildings(int startX, int startY, AttackToolsAndMethods attackToolsAndMethods) {
        int range = attackToolsAndMethods.getRange();
        outerLoop:
        for(int x = startX - range; x < startX + range + 1; x++) {
            for(int y = startY - range; y < startY + range + 1; y++) {
                if(!Utils.CheckMapCell.validationOfX(x) || !Utils.CheckMapCell.validationOfY(y)) continue;

                Building building = Database.getCurrentMapGame().getMapCellByCoordinates(x, y).getBuilding();
                if(building != null && !attackToolsAndMethods.getOwner().equals(building.getOwner())) {
                    building.changeBuildingHp(-attackToolsAndMethods.getDamage());
                    break outerLoop;
                }

                for (Soldier soldier : Database.getCurrentMapGame().getMapCellByCoordinates(x, y).getSoldier()) {
                    if(!attackToolsAndMethods.getOwner().equals(soldier.getOwner())) {
                        soldier.changeHp(-attackToolsAndMethods.getDamage());
                        break outerLoop;
                    }
                }
            }
        }
    }

    public void applyDamageBatteringRam(int startX, int startY, AttackToolsAndMethods attackToolsAndMethods) {
        int range = attackToolsAndMethods.getRange();
        for(int x = startX - range; x < startX + range + 1; x++) {
            for(int y = startY - range; y < startY + range + 1; y++) {
                if(!Utils.CheckMapCell.validationOfX(x) || !Utils.CheckMapCell.validationOfY(y)) continue;

                for (MapCellItems mapCellItem : Database.getCurrentMapGame().getMapCellByCoordinates(x, y).getMapCellItems()) {
                    if(mapCellItem instanceof Wall && !attackToolsAndMethods.getOwner().equals(mapCellItem.getOwner()))
                        ((Wall) mapCellItem).changeHp(-attackToolsAndMethods.getDamage());
                }
            }
        }
    }

    public void applyDamageToAttackToolsAndMethods() {
        for (MapCell mapCell : Database.getCurrentMapGame().getMapCells()) {
            for (Soldier soldier : mapCell.getSoldier()) {
                mapIterationOnAttackTools(mapCell.getX(), mapCell.getY(), soldier);
            }
        }
    }

    public void mapIterationOnAttackTools(int startX, int startY, Soldier soldier) {
        int range = soldier.getAttackRange();
        for(int x = startX - range; x < startX + range + 1; x++) {
            for(int y = startY - range; y < startY + range + 1; y++) {
                if(!Utils.CheckMapCell.validationOfX(x) || !Utils.CheckMapCell.validationOfY(y)) continue;

                AttackToolsAndMethods attackTool = Database.getCurrentMapGame().getMapCellByCoordinates(x, y).getAttackToolsAndMethods();
                if(attackTool != null && !soldier.getOwner().equals(attackTool.getOwner()))
                    attackTool.changeHp(-soldier.getAttackRating());
            }
        }
    }

    public int removeDestroyedAttackToolsAndMethods() {
        for (MapCell mapCell : Database.getCurrentMapGame().getMapCells()) {
            if(mapCell.getAttackToolsAndMethods() != null && mapCell.getAttackToolsAndMethods().getHp() <= 0) {
                mapCell.getAttackToolsAndMethods().getOwner().getEmpire().removeAttackToolsAndMethods(mapCell.getAttackToolsAndMethods());
                mapCell.setAttackToolsAndMethods(null);
                return removeDestroyedAttackToolsAndMethods();
            }
        }
        return -1;
    }

    public void changePopularity(Empire empire) {
        int changeAmount = 0;
        changeAmount += empire.getFoodDiversity()-1 + foodRateEffect(empire) + taxRateEffect(empire) +
                empire.getReligionRate() + fearRateEffect(empire);

        empire.changePopularityRate(changeAmount);
    }

    private int fearRateEffect(Empire empire) {
        switch (empire.getFearRate()) {
            case -5:
                return -5;
            case -4:
                return -4;
            case -3:
                return -3;
            case -2:
                return -2;
            case -1:
                return -1;
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 5;
            default:
                return 0;
        }
    }

    private int taxRateEffect(Empire empire) {
        switch (empire.getTaxRate()) {
            case -3:
                return 7;
            case -2:
                return 5;
            case -1:
                return 3;
            case 0:
                return 1;
            case 1:
                return -2;
            case 2:
                return -4;
            case 3:
                return -6;
            case 4:
                return -8;
            case 5:
                return -12;
            case 6:
                return -16;
            case 7:
                return -20;
            case 8:
                return -24;
            default:
                return 0;
        }
    }

    private int foodRateEffect(Empire empire) {
        switch (empire.getFoodRate()) {
            case -2:
                return -8;
            case -1:
                return -4;
            case 1:
                return 4;
            case 2:
                return 8;
            default:
                return 0;
        }
    }

    public void giveFood(Empire empire) {
        double number = getNumberOfGivenFoods(empire);;

        if(empire.getFoodNumbers() < number * empire.getPopulation()) {
            double ratio = empire.getFoodNumbers() / empire.getPopulation();
            if (ratio > 2)  empire.setFoodRate(2);
            else if (ratio > 1.5) empire.setFoodRate(1);
            else if (ratio > 1) empire.setFoodRate(0);
            else if (ratio > 0.5) empire.setFoodRate(-1);
            else empire.setFoodRate(-2);
        }

        number = getNumberOfGivenFoods(empire);
        empire.changeFoodNumber(-number * empire.getPopulation());
    }

    private double getNumberOfGivenFoods(Empire empire) {
        double number = 0;
        switch (empire.getFoodRate()) {
            case -2:
                number = 0;
                break;
            case -1:
                number = 0.5;
                break;
            case 0:
                number = 1;
                break;
            case 1:
                number = 1.5;
                break;
            case 2:
                number = 2;
                break;
        }
        return number;
    }

    private double getNumberOfTakenCoins(Empire empire) {
        double number = 0;
        switch (empire.getTaxRate()) {
            case -3:
                number = -1;
                break;
            case -2:
                number = -0.8;
                break;
            case -1:
                number = -0.6;
                break;
            case 0:
                number = 0;
                break;
            case 1:
                number = 0.6;
                break;
            case 2:
                number = 0.8;
                break;
            case 3:
                number = 1;
                break;
            case 4:
                number = 1.2;
                break;
            case 5:
                number = 1.4;
                break;
            case 6:
                number = 1.6;
                break;
            case 7:
                number = 1.8;
                break;
            case 8:
                number = 2;
                break;
        }
        return number;
    }

    public void getTax(Empire empire) {
        double coinNumber = getNumberOfTakenCoins(empire);

        if (coinNumber < 0) {
            if (empire.getCoins() < -coinNumber * empire.getPopulation()) {
                double ratio = empire.getCoins() / empire.getPopulation();
                if (ratio > 1)  empire.setTaxRate(-3);
                else if (ratio > 0.8) empire.setTaxRate(-2);
                else empire.setTaxRate(-1);
            }
        }

        coinNumber = getNumberOfTakenCoins(empire);
        empire.changeCoins(coinNumber * empire.getPopulation());
    }

    public void handleUnemployedPopulation() {

    }

    public void handleFearRate(Empire empire) {
        empire.changeEfficiency(empire.getFearRate() * -0.1);
        for (Soldier soldier : empire.getSoldiers()) {
            soldier.changeDamage(empire.getFearRate() * 0.05);
        }
    }

    public String chooseMap() {
        MapOrganizer.getMapId();
        return "Choose your map by id:\n" +
        "Give an id between 1 and " + Database.getMapId().size() + "\n" +
        "If you want to create custom map enter 0";
    }

    public int getNumberOfPlayers() {
        return Database.getUsersInTheGame().size();
    }

    public String getPlayerName(int i) {
        return Database.getUsersInTheGame().get(i).getUsername();
    }

    public GameMenuMessages setHeadquarters(int i, int x, int y) {

        if (!CheckMapCell.validationOfX(x)) return GameMenuMessages.X_OUT_OF_BOUNDS;
        if (!CheckMapCell.validationOfY(y)) return GameMenuMessages.Y_OUT_OF_BOUNDS;
        if (!CheckMapCell.mapCellEmptyByCoordinates(x, y)) return GameMenuMessages.CELL_IS_FULL;

        if (Database.getCurrentMapGame().getMapCellByCoordinates(x, y).getMaterialMap().isWaterZone())
            return GameMenuMessages.INAPPROPRIATE_TEXTURE;

        User user = Database.getUsersInTheGame().get(i);
        Empire empire = Database.getUsersInTheGame().get(i).getEmpire();
        Database.setCurrentUser(user);
        empire.makeHeadquarter(x, y, user);
        Database.setCurrentUser(Database.getLoggedInUser());
        return GameMenuMessages.SUCCESS;
    }

    public static void handleProductionBuildings(Building building) {
        Empire empire = Database.getCurrentUser().getEmpire();
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

    public static void handleMiningBuildings(Building building) {
        Empire empire = Database.getCurrentUser().getEmpire();
        Item.ItemType itemType = ((MiningBuilding) building).getProduction();
        Item item = Item.getAvailableItems(itemType.getName());
        assert item != null;
        item.changeNumber(5);
    }

    public static void handleReligiousBuildings(Building building) {
        Empire empire = Database.getCurrentUser().getEmpire();
        empire.changePopularityRate(2);
        empire.changeReligionRate(2);

        if(building.getBuildingName().equals("cathedral")) {
            empire.changePopularityRate(2);
            empire.changeReligionRate(2);
        }
    }

    public static void handleDrawBridge(Building building) {
        for(int i = building.getX()-1; i <= building.getX()+1; i++) {
            for(int j = building.getY()-1; j <= building.getY()+1; j++) {
                if(Utils.CheckMapCell.validationOfX(i) && Utils.CheckMapCell.validationOfY(j)) {
                    for (Soldier soldier : Database.getCurrentMapGame().
                            getMapCellByCoordinates(i, j).getSoldier()) {
                        if (!soldier.getOwner().equals(Database.getCurrentUser())) {
                            soldier.changeSpeed(-1);
                        }
                    }
                }
            }
        }
    }

    public static void handleInn(Building building) {
        Database.getCurrentUser().getEmpire().changePopularityRate(5);
    }

    public static void handleIronMine(Building building) {
        Objects.requireNonNull(Item.getAvailableItems("iron")).changeNumber(5);
    }

//    public static void handleQuarry(Building building) {
//        Objects.requireNonNull(Item.getAvailableItems("stone")).changeNumber(5);
//        //TODO
//    }

    public static void handleOilSmelter(Building building) {
        Objects.requireNonNull(Item.getAvailableItems("oil")).changeNumber(1);
        //TODO
    }

    private void buildingsFunctionsEachTurn() {
        for (Building building : Database.getCurrentUser().getEmpire().getBuildings()) {
            if(building.getCategory().equals("production")) handleProductionBuildings(building);
            else if(building.getCategory().equals("mining")) handleMiningBuildings(building);
            else if(building.getBuildingName().equals("church") || building.getBuildingName().equals("cathedral"))
                handleReligiousBuildings(building);
            else if(building.getCategory().equals("inn")) handleInn(building);
            else if(building.getBuildingName().equals("iron mine")) handleIronMine(building);
//            else if(building.getBuildingName().equals("quarry")) handleQuarry(building);
            else if(building.getBuildingName().equals("oil smelter")) handleOilSmelter(building);
            else if(building.getBuildingName().equals("ox tether")) handleOxTether(building);
        }
    }

    public static void handleOxTether(Building building) {
        Item.getAvailableItems("cow").changeNumber(1);
        Animal cow = Database.getCurrentUser().getEmpire().getAnimalByName("cow");
        Database.getCurrentMapGame().getMapCellByCoordinates(building.getX(), building.getY()).addItems(cow);
        moveCowAndOxTether(cow, building, building.getX(), building.getY());
    }

    private static void moveCowAndOxTether(Animal cow, Building building, int currentX, int currentY) {
        int goalX = -1;
        int goalY = -1;
        for (Building building1 : Database.getCurrentUser().getEmpire().getBuildings()) {
            if(building1.getBuildingName().equals("quarry")) {
                goalX = building1.getX();
                goalY = building1.getY();
                break;
            }
        }
        if(goalX == -1) return;
        ArrayList<MapCell> path = MoveController.aStarSearch(Database.getCurrentMapGame(), currentX, currentY, goalX, goalY);

        for(int i = path.size() - 1; i > 0; i--) {
            path.get(i).removeAnimal(cow);
            path.get(i).removeBuilding(building);
            path.get(i-1).addItems(cow);
            path.get(i-1).addBuilding(building);
        }

        int goalX2 = -1;
        int goalY2 = -1;
        for (Building building1 : Database.getCurrentUser().getEmpire().getBuildings()) {
            if(building1.getBuildingName().equals("stockpile")) {
                goalX2 = building1.getX();
                goalY2 = building1.getY();
                break;
            }
        }
        if(goalX2 == -1) return;
        ArrayList<MapCell> path2 = MoveController.aStarSearch(Database.getCurrentMapGame(), goalX, goalY, goalX2, goalY2);

        for(int i = path2.size() - 1; i > 0; i--) {
            path2.get(i).removeAnimal(cow);
            path2.get(i).removeBuilding(building);
            path2.get(i-1).addItems(cow);
            path2.get(i-1).addBuilding(building);
        }

        Item.getAvailableItems("stone").changeNumber(cow.getNumber() * 5);
    }

    private boolean gameIsFinished() {

        int numberOfKingsAlive = 0;
        for (User user : Database.getUsersInTheGame()) {
            if (user.getEmpire().getKing() != null) numberOfKingsAlive++;
        }

        if (numberOfKingsAlive <= 1) return true;

        if (Database.getTurnsPassed() >= Database.getTotalTurns()) return true;

        return false;
    }

    private void setScores() {
        //TODO
    }
}
