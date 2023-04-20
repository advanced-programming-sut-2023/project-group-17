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
            else if ((matcher = BuildingMenuCommands.getMatcher(command, BuildingMenuCommands.BACK)) != null) return;
            else System.out.println("invalid command");
        }
    }

    private void dropBuilding(Matcher matcher) {
        if (Menu.checkBlankField(matcher.group("x")) || Menu.checkBlankField(matcher.group("y")) ||
        Menu.checkBlankField(matcher.group("type"))) {
            System.out.println("error : blank field");
            return;
        }
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        String type = Menu.handleDoubleQuote(matcher.group("type"));
        switch (controller.dropBuilding(x, y, type)) {
            case SUCCESS:
                System.out.println("The building successfully dropped");
                break;
            case X_OUT_OF_BOUNDS:
                System.out.println("Coordinate of x is out of bounds");
                break;
            case Y_OUT_OF_BOUNDS:
                System.out.println("Coordinate of y is out of bounds");
                break;
            case INVALID_TYPE:
                System.out.println("Type of building you entered is invalid");
                break;
            case INAPPROPRIATE_TEXTURE:
                System.out.println("Texture of this place is inappropriate for placing this building");
                break;
            case CELL_IS_FULL:
                System.out.println("There is another building in this cell");
                break;
        }
    }

    private void selectBuilding(Matcher matcher) {
        if (Menu.checkBlankField(matcher.group("x")) || Menu.checkBlankField(matcher.group("y"))) {
            System.out.println("error : blank field");
            return;
        }
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        switch (controller.selectBuilding(x, y)) {
            case SUCCESS:
                System.out.println("The building successfully selected");
                break;
            case CELL_IS_EMPTY:
                System.out.println("There is not any building in this cell");
                break;
            case OPPONENT_BUILDING:
                System.out.println("The building belongs to your opponents");
                break;
        }
    }

    private void createUnit(Matcher matcher) {
        if (Menu.checkBlankField(matcher.group("type")) || Menu.checkBlankField(matcher.group("count")) ||
                Menu.checkBlankField(matcher.group("type"))) {
            System.out.println("error : blank field");
            return;
        }
        String type = Menu.handleDoubleQuote(matcher.group("type"));
        int count = Integer.parseInt(matcher.group("count"));
        switch (controller.createUnit(type, count)) {
            case SUCCESS:
                System.out.println("Units successfully crated");
                break;
            case INVALID_NUMBER:
                System.out.println("The number you entered is invalid");
                break;
            case INSUFFICIENT_STORAGE:
                System.out.println("There is not enough resource for creating units");
                break;
            case NOT_ENOUGH_CROWD:
                System.out.println("There is not enough crowd for creating units");
                break;
            case BUILDING_IS_NOT_SELECTED:
                System.out.println("You did not select any building");
                break;
        }
    }

    private void repair() {
        switch (controller.repair()) {
            case SUCCESS:
                System.out.println("Units successfully crated");
                break;
            case INSUFFICIENT_STONE:
                System.out.println("There is not enough stone for repairing this building");
                break;
            case OPPONENT_SOLDIER_AROUND:
                System.out.println("Opponent soldier is near this building");
                break;
            case BUILDING_IS_NOT_SELECTED:
                System.out.println("You did not select any building");
                break;
        }
    }

}
