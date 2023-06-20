package Controller;

import Model.Database;
import Model.Empire;
import Model.Items.Food;
import Model.User;
import View.Enums.Messages.EmpireMenuMessages;
import javafx.scene.control.Label;

public class EmpireMenuController {
    public String showPopularity() {
        return Database.getCurrentUser().getUsername() + "'s popularity rate is : " + Database.getCurrentUser().getEmpire().getPopularityRate();
    }

    public String showPopularityFactors() {
        return Database.getCurrentUser().getEmpire().toString();
    }

    public String showFoodList() {
        String result = "";
        if (Database.getCurrentUser().getEmpire().getFoods() == null) return result;
        for (Food food : Database.getCurrentUser().getEmpire().getFoods()) {
            result += food.toString() + "\n";
        }
        return result;
    }

    public EmpireMenuMessages setFoodRate(int foodRate) {
        if (foodRate < -2 || 2 < foodRate) return EmpireMenuMessages.INVALID_NUMBER;
        Database.getCurrentUser().getEmpire().setFoodRate(foodRate);
        return EmpireMenuMessages.SUCCESS;
    }

    public String showFoodRate() {
        return Database.getCurrentUser().getUsername() + "'s food rate is : " + Database.getCurrentUser().getEmpire().getFoodRate();
    }

    public EmpireMenuMessages setTaxRate(int taxRate) {
        if (taxRate < -3 || 8 < taxRate) return EmpireMenuMessages.INVALID_NUMBER;
        Database.getCurrentUser().getEmpire().setTaxRate(taxRate);
        return EmpireMenuMessages.SUCCESS;
    }

    public String showTaxRate() {
        return Database.getCurrentUser().getUsername() + "'s tax rate is : " + Database.getCurrentUser().getEmpire().getTaxRate();
    }

    public EmpireMenuMessages setFearRate(int fearRate) {
        if (fearRate < -5 || 5 < fearRate) return EmpireMenuMessages.INVALID_NUMBER;
        Database.getCurrentUser().getEmpire().setFearRate(fearRate);
        return EmpireMenuMessages.SUCCESS;
    }

    public String showFearRate() {
        return Database.getCurrentUser().getUsername() + "'s fear rate is : " + Database.getCurrentUser().getEmpire().getFearRate();
    }

    public double getFearRate() {
        return Database.getCurrentUser().getEmpire().getFearRate();
    }

    public double getFoodRate() {
        return Database.getCurrentUser().getEmpire().getFoodRate();
    }

    public double getTaxRate() {
        return Database.getCurrentUser().getEmpire().getTaxRate();
    }

    public double getReligiousRate() {
        return Database.getCurrentUser().getEmpire().getReligionRate();
    }

    public double getPopularityRate() {
        return Database.getCurrentUser().getEmpire().getPopularityRate();
    }

    public int getPopularityGrowth() {
        Empire empire = Database.getCurrentUser().getEmpire();
        int changeAmount = 0;
        changeAmount += empire.getFoodDiversity()-1 + foodRateEffect(empire) + taxRateEffect(empire) +
                empire.getReligionRate() + fearRateEffect(empire);

        return changeAmount;
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
}
