package Controller;

import Model.Database;
import Model.Items.Food;
import Model.User;
import View.Enums.Messages.EmpireMenuMessages;

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
        Database.getCurrentUser().getEmpire().setTaxRate(fearRate);
        System.out.println(fearRate);
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
}
