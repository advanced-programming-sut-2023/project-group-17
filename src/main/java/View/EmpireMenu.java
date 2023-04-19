package View;

import Controller.EmpireMenuController;
import View.Enums.Commands.EmpireMenuCommands;

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
                setFearRate();
            else if ((matcher = EmpireMenuCommands.getMatcher(command, EmpireMenuCommands.SHOW_FEAR_RATE)) != null)
                showFearRate();
            else System.out.println("Invalid Command");
        }
    }

    private void showPopularity() {
    }

    private void showPopularityFactors() {
    }

    private void showFoodList() {
    }

    private void setFoodRate(Matcher matcher) {
    }

    private void showFoodRate() {
    }

    private void setTaxRate(Matcher matcher) {
    }

    private void showTaxRate() {
    }

    private void setFearRate() {
    }

    private void showFearRate() {
    }
}
