package Controller;

import Model.Database;
import Model.Items.Food;
import Model.User;
import View.Enums.Messages.EmpireMenuMessages;

public class EmpireMenuController {

    private User user;
    public EmpireMenuController() {
        this.user = Database.getLoggedInUser();
    }
    public String showPopularity() {
        return user.getUsername() + "'s popularity rate is : " + user.getEmpire().getPopularityRate();
    }

    public String showPopularityFactors() {
        return user.getEmpire().toString();
    }

    public String showFoodList() {
        String result = "";
        for (Food food : user.getEmpire().getFoods()) {
            result += food.toString() + "\n";
        }
        return result;
    }

    public EmpireMenuMessages setFoodRate(int foodRate) {
        if (foodRate < -2 || 2 < foodRate) return EmpireMenuMessages.INVALID_NUMBER;
        user.getEmpire().setFoodRate(foodRate);
        return EmpireMenuMessages.SUCCESS;
    }

    public String showFoodRate() {
        return user.getUsername() + "'s food rate is : " + user.getEmpire().getFoodRate();
    }

    public EmpireMenuMessages setTaxRate(int taxRate) {
        if (taxRate < -3 || 8 < taxRate) return EmpireMenuMessages.INVALID_NUMBER;
        user.getEmpire().setTaxRate(taxRate);
        return EmpireMenuMessages.SUCCESS;
    }

    public String showTaxRate() {
        return user.getUsername() + "'s tax rate is : " + user.getEmpire().getTaxRate();
    }

    public EmpireMenuMessages setFearRate(int fearRate) {
        if (fearRate < -5 || 5 < fearRate) return EmpireMenuMessages.INVALID_NUMBER;
        user.getEmpire().setTaxRate(fearRate);
        return EmpireMenuMessages.SUCCESS;
    }

    public String showFearRate() {
        return user.getUsername() + "'s fear rate is : " + user.getEmpire().getFearRate();
    }
}
