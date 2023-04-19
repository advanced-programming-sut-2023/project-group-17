package View;

import Controller.BuildingMenuController;
import View.Enums.Commands.BuildingMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class BuildingMenu extends Menu{

    BuildingMenuController controller;
    public BuildingMenu() {
        controller = new BuildingMenuController();
}
    void run(Scanner scanner) {
        Matcher matcher;
        String command;
        while (true) {
            command = scanner.nextLine();
            if ((matcher = BuildingMenuCommands.getMatcher(command, BuildingMenuCommands.DROP_BUILDING)) != null)
                dropBuilding(matcher);
            else if ((matcher = BuildingMenuCommands.getMatcher(command, BuildingMenuCommands.SELECT_BUILDING)) != null)
                selectBuilding(matcher);
            else if ((matcher = BuildingMenuCommands.getMatcher(command, BuildingMenuCommands.CREATE_UNIT)) != null)
                createUnit(matcher);
            else if ((matcher = BuildingMenuCommands.getMatcher(command, BuildingMenuCommands.REPAIR)) != null)
                repair();
            else System.out.println("invalid command");
        }
    }

    private void dropBuilding(Matcher matcher) {
    }

    private void selectBuilding(Matcher matcher) {
    }

    private void createUnit(Matcher matcher) {
    }

    private void repair() {
    }

}
