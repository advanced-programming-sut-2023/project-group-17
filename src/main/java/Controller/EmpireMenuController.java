package Controller;

import Model.Database;
import Model.User;

public class EmpireMenuController {

    private User user;
    public EmpireMenuController() {
        this.user = Database.getLoggedInUser();
    }
    public String showPopularity() {
        return null;
    }

    public String showPopularityFactors() {
        return null;
    }

    public String showFoodList() {
        return null;
    }

    public String setFoodRate(int foodRate) {
        return null;
    }

    public String showFoodRate() {
        return null;
    }

    public String setTaxRate(int taxRate) {
        return null;
    }

    public String showTaxRate() {
        return null;
    }

    public String setFearRate(int fearRate) {
        return null;
    }

    public String showFearRate() {
        return null;
    }
}
