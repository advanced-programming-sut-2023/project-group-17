package Controller;

import Model.Database;
import Model.Empire;
import Model.Map;
import Utils.CheckMapCell;
import View.Enums.Messages.GameMenuMessages;
import View.Enums.Messages.MapMenuMessages;
import View.Menu;

public class GameMenuController {
    private final Empire currentEmpire = Database.getLoggedInUser().getEmpire();

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

    }

    public void changePopularity() {
        //TODO: set religion rate yadet nare
        //TODO: set fear rate effect
        int changeAmount = 0;
        changeAmount += currentEmpire.getFoodDiversity()-1 + foodRateEffect() + taxRateEffect() +
                currentEmpire.getReligionRate();

        currentEmpire.changePopularityRate(changeAmount);
    }

    private int taxRateEffect() {
        switch (currentEmpire.getTaxRate()) {
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

    private int foodRateEffect() {
        switch (currentEmpire.getFoodRate()) {
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

    public void giveFood() {
        double number = getNumberOfGivenFoods();;

        if(currentEmpire.getFoodNumbers() < number * currentEmpire.getPopulation()) {
            double ratio = currentEmpire.getFoodNumbers() / currentEmpire.getPopulation();
            if (ratio > 2)  currentEmpire.setFoodRate(2);
            else if (ratio > 1.5) currentEmpire.setFoodRate(1);
            else if (ratio > 1) currentEmpire.setFoodRate(0);
            else if (ratio > 0.5) currentEmpire.setFoodRate(-1);
            else currentEmpire.setFoodRate(-2);
        }

        number = getNumberOfGivenFoods();
        currentEmpire.changeFoodNumber(-number * currentEmpire.getPopulation());
    }

    private double getNumberOfGivenFoods() {
        double number = 0;
        switch (currentEmpire.getFoodRate()) {
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

    private double getNumberOfTakenCoins() {
        double number = 0;
        switch (currentEmpire.getTaxRate()) {
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

    public void getTax() {
        double coinNumber = getNumberOfTakenCoins();

        if (coinNumber < 0) {
            if (currentEmpire.getCoins() < -coinNumber * currentEmpire.getPopulation()) {
                double ratio = currentEmpire.getCoins() / currentEmpire.getPopulation();
                if (ratio > 1)  currentEmpire.setTaxRate(-3);
                else if (ratio > 0.8) currentEmpire.setTaxRate(-2);
                else currentEmpire.setTaxRate(-1);
            }
        }

        coinNumber = getNumberOfTakenCoins();
        currentEmpire.changeCoins(coinNumber * currentEmpire.getPopulation());
    }

    public void changeResources() {

    }

    public void handleUnemployedPopulation() {

    }

    public void soldiersFight() {

    }

    public int showTaxRate() {
        return 0;
    }

    public void buildingsFight() {

    }

    public String chooseMap() {
        return "Choose your map by id:\n" +
        "Give an id between 1 and " + Database.getAllMaps().size() + "\n" +
        "If you want to create custom map enter 0";
    }
}
