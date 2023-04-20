package View;

import Controller.EmpireMenuController;
import View.Enums.Commands.EmpireMenuCommands;
import View.Enums.Messages.EmpireMenuMessages;

import java.util.Scanner;
import java.util.regex.Matcher;

public class EmpireMenu extends Menu {
    EmpireMenuController controller;
    public EmpireMenu() {
        this.controller = new EmpireMenuController();
    }
    @Override
    public void run(Scanner scanner) {
        Matcher matcher;
        String command;

        while (true) {
            command = scanner.nextLine();
            if ((matcher = EmpireMenuCommands.getMatcher(command, EmpireMenuCommands.SHOW_POPULARITY)) != null)
                showPopularity();
            else if ((matcher = EmpireMenuCommands.getMatcher(command, EmpireMenuCommands.SHOW_POPULARITY_FACTORS)) != null)
                showPopularityFactors();
            else if ((matcher = EmpireMenuCommands.getMatcher(command, EmpireMenuCommands.SHOW_FOOD_LIST)) != null)
                showFoodList();
            else if ((matcher = EmpireMenuCommands.getMatcher(command, EmpireMenuCommands.SET_FOOD_RATE)) != null)
                setFoodRate(matcher);
            else if ((matcher = EmpireMenuCommands.getMatcher(command, EmpireMenuCommands.SHOW_FOOD_RATE)) != null)
                showFoodRate();
            else if ((matcher = EmpireMenuCommands.getMatcher(command, EmpireMenuCommands.SET_TAX_RATE)) != null)
                setTaxRate(matcher);
            else if ((matcher = EmpireMenuCommands.getMatcher(command, EmpireMenuCommands.SHOW_TAX_RATE)) != null)
                showTaxRate();
            else if ((matcher = EmpireMenuCommands.getMatcher(command, EmpireMenuCommands.SET_FEAR_RATE)) != null)
                setFearRate(matcher);
            else if ((matcher = EmpireMenuCommands.getMatcher(command, EmpireMenuCommands.SHOW_FEAR_RATE)) != null)
                showFearRate();
            else if ((matcher = EmpireMenuCommands.getMatcher(command, EmpireMenuCommands.BACK)) != null) return;
            else System.out.println("Invalid Command");
        }
    }

    private void showPopularity() {
        System.out.println(controller.showPopularity());
    }

    private void showPopularityFactors() {
        System.out.println(controller.showPopularityFactors());
    }

    private void showFoodList() {
        System.out.println(controller.showFoodList());
    }

    private void setFoodRate(Matcher matcher) {
        if (Menu.checkBlankField(matcher.group("rateNumber"))) {
            System.out.println("Set food rate failed : blank field");
            return;
        }
        int foodRate = Integer.parseInt(matcher.group("rateNumber"));
        switch (controller.setFoodRate(foodRate)) {
            case SUCCESS:
                System.out.println("Now the food rate is : " + foodRate);
                break;
            case INVALID_NUMBER:
                System.out.println("Set food rate failed : The number is invalid");
                break;
        }
    }

    private void showFoodRate() {
        System.out.println(controller.showFoodRate());
    }

    private void setTaxRate(Matcher matcher) {
        if (Menu.checkBlankField(matcher.group("rateNumber"))) {
            System.out.println("Set tax rate failed : blank field");
            return;
        }
        int taxRate = Integer.parseInt(matcher.group("rateNumber"));
        switch (controller.setTaxRate(taxRate)) {
            case SUCCESS:
                System.out.println("Now the tax rate is : " + taxRate);
                break;
            case INVALID_NUMBER:
                System.out.println("Set tax rate failed : The number is invalid");
                break;
        }
    }

    private void showTaxRate() {
        System.out.println(controller.showTaxRate());
    }

    private void setFearRate(Matcher matcher) {
        if (Menu.checkBlankField(matcher.group("rateNumber"))) {
            System.out.println("Set fear rate failed : blank field");
            return;
        }
        int fearRate = Integer.parseInt(matcher.group("rateNumber"));
        switch (controller.setFearRate(fearRate)) {
            case SUCCESS:
                System.out.println("Now the fear rate is : " + fearRate);
                break;
            case INVALID_NUMBER:
                System.out.println("Set fear rate failed : The number is invalid");
                break;
        }
    }

    private void showFearRate() {
        System.out.println(controller.showFearRate());
    }
}
