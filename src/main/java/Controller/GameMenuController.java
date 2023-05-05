package Controller;

import Model.*;
import Model.Buildings.Building;
import Model.Buildings.MiningBuilding;
import Model.Buildings.ProductionBuilding;
import Model.Buildings.StorageBuilding;
import Model.Items.Animal;
import Model.Items.Item;
import Model.People.Person;
import Model.People.Soldier;
import Utils.CheckMapCell;
import View.Enums.Messages.GameMenuMessages;

import java.util.HashMap;
import java.util.Objects;

public class GameMenuController {
    public GameMenuMessages chooseMapGame(int id) {
        if(id > Database.getAllMaps().size() || id < 1) return GameMenuMessages.INVALID_MAP_NUMBER;
        Map map = Database.getAllMaps().get(id-1);
        Database.setCurrentMapGame(map);
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

    public void nextTurn() {

    }

    public void applyDamages() {
        for (MapCell mapCell : Database.getCurrentMapGame().getMapCells()) {
            for (Soldier soldier : mapCell.getSoldier()) {
                mapIteration(mapCell.getX(), mapCell.getY(), soldier);
            }
        }
    }

    public void mapIteration(int startX, int startY, Soldier soldier) {
        int range = soldier.getAttackRange();
        for(int x = startX - range; x < startX + range + 1; x++) {
            for(int y = startY - range; y < startY + range + 1; y++) {
                if(!Utils.CheckMapCell.validationOfX(x) || !Utils.CheckMapCell.validationOfY(y))
                    continue;

                for (Person person : Database.getCurrentMapGame().getMapCellByCoordinates(x, y).getPeople()) {
                    if(!soldier.getOwner().equals(person.getOwner()))
                        person.changeHp(-soldier.getAttackRating());
                }
            }
        }
    }

    public int removeDeadBodies() {
        for (MapCell mapCell : Database.getCurrentMapGame().getMapCells()) {
            for (Person person : mapCell.getPeople()) {
                if(person.getHp() <= 0) {
                    mapCell.removePerson(person);
                    person.getOwner().getEmpire().removePerson(person);
                    return removeDeadBodies();
                }
            }
        }
        return -1;
    }

//    public void applyDamageToBuildings() {
//        int startX, startY, range;
//        for (MapCell mapCell : Database.getCurrentMapGame().getMapCells()) {
////            for (Soldier soldier : mapCell.getSoldier()) {
//                startX = mapCell.getX();
//                startY = mapCell.getY();
//                if(mapCell.getBuilding() instanceof DefensiveBuilding)
//                    range = ((DefensiveBuilding) mapCell.getBuilding()).getDefenceRange();
//                else
//                    range =
//                for (int x = startX - range; x < startX + range + 1; x++) {
//                for (int y = startY - range; y < startY + range + 1; y++) {
//                    if (x < 1 || y < 1 || y > Database.getCurrentMapGame().getLength() || x > Database.getCurrentMapGame().getWidth())
//                        continue;
//
//                    }
//                }
//
//
//
//
//
//                if(mapCell.getBuilding()!= null && !soldier.getOwner().equals(mapCell.getBuilding().getOwner())) {
//                    if(mapCell.getBuilding() instanceof DefensiveBuilding &&
//                       soldier.getAttackRange() > ((DefensiveBuilding) mapCell.getBuilding()).getDefenceRange())
//                            mapIteration(startX, startY, ((DefensiveBuilding) mapCell.getBuilding()).getDefenceRange(), soldier, mapCell.getBuilding());
//                    else
//                        mapIteration(startX, startY, soldier.getAttackRange(), soldier, mapCell.getBuilding());
//                }
//
////            }
//        }
//    }
//
//
//    public void mapIteration(int startX, int startY, int range, Soldier soldier, Building building) {
//        for (int x = startX - range; x < startX + range + 1; x++) {
//            for (int y = startY - range; y < startY + range + 1; y++) {
//                if (x < 1 || y < 1 || y > Database.getCurrentMapGame().getLength() || x > Database.getCurrentMapGame().getWidth())
//                    continue;
//
//                building.changeBuildingHp(-soldier.getAttackRating());
//            }
//        }
//    }

    public int removeDestroyedBuildings() {
        for (MapCell mapCell : Database.getCurrentMapGame().getMapCells()) {
            if(mapCell.getBuilding().getBuildingHp() <= 0) {
                mapCell.getBuilding().getOwner().getEmpire().removeBuilding(mapCell.getBuilding());
                mapCell.setBuilding(null);
                return removeDestroyedBuildings();
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

    public void changePopulation() {

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

    public void changeResources() {

    }

    public void handleUnemployedPopulation() {

    }

    public void soldiersFight() {

    }


    public void handleFearRate(Empire empire) {
        empire.changeEfficiency(empire.getFearRate() * -0.1);
        for (Soldier soldier : empire.getSoldiers()) {
            soldier.changeDamage(empire.getFearRate() * 0.05);
        }
    }

    public String chooseMap() {
        return "Choose your map by id:\n" +
        "Give an id between 1 and " + Database.getAllMaps().size() + "\n" +
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
        empire.makeHeadquarter(x, y, user);
        return GameMenuMessages.SUCCESS;
    }

    public static void handleCagedDogs(Building building) {
        int x = building.getX();
        int y = building.getY();
        MapCell mapCell = Database.getCurrentMapGame().getMapCellByCoordinates(x, y);
        Animal dogs = (new Animal(Animal.animalNames.DOG, Database.getLoggedInUser(), 3));
        mapCell.addItems(dogs);
        Database.getLoggedInUser().getEmpire().addAnimal(dogs);
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

    public static void handleMiningBuildings(Building building) {
        Empire empire = Database.getLoggedInUser().getEmpire();
        Item.ItemType itemType = ((MiningBuilding) building).getProduction();
        Item item = Item.getAvailableItems(itemType.getName());
        assert item != null;
        item.changeNumber(5);
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

    public static void handleIronMine(Building building) {
        Objects.requireNonNull(Item.getAvailableItems("iron")).changeNumber(5);
    }

    public static void handleQuarry(Building building) {
        Objects.requireNonNull(Item.getAvailableItems("stone")).changeNumber(5);
        //TODO
    }
}
