package View;

import Controller.MapMenuController;
import View.Enums.Commands.MapMenuCommands;
import View.Enums.Messages.MapMenuMessages;

import java.util.Scanner;
import java.util.regex.Matcher;

public class MapMenu extends Menu{
    MapMenuController controller;
    public MapMenu() {
        this.controller = new MapMenuController();
    }
    @Override
    public void run(Scanner scanner) {
        Matcher matcher;
        String command;

        while (true) {
            command = scanner.nextLine();
            if ((matcher = MapMenuCommands.getMatcher(command, MapMenuCommands.MOVE_MAP)) != null)
                moveMap(matcher);
            else if ((matcher = MapMenuCommands.getMatcher(command, MapMenuCommands.SHOW_DETAILS)) != null)
                showDetails(matcher);
            else if ((matcher = MapMenuCommands.getMatcher(command, MapMenuCommands.SET_TEXTURE_ONE_BLOCK)) != null)
                setTextureOneBlock(matcher);
            else if ((matcher = MapMenuCommands.getMatcher(command, MapMenuCommands.SET_TEXTURE_MULTIPLE_BLOCKS)) != null)
                setTextureMultipleBlocks(matcher);
            else if ((matcher = MapMenuCommands.getMatcher(command, MapMenuCommands.CLEAR_BLOCK)) != null)
                clearBlock(matcher);
            else if ((matcher = MapMenuCommands.getMatcher(command, MapMenuCommands.DROP_ROCK)) != null)
                dropRock(matcher);
            else if ((matcher = MapMenuCommands.getMatcher(command, MapMenuCommands.DROP_TREE)) != null)
                dropTree(matcher);
            else if ((matcher = MapMenuCommands.getMatcher(command, MapMenuCommands.DROP_BUILDING)) != null)
                dropBuilding(matcher);
            else if ((matcher = MapMenuCommands.getMatcher(command, MapMenuCommands.DROP_UNIT)) != null)
                dropUnit(matcher);
            else if ((matcher = MapMenuCommands.getMatcher(command,MapMenuCommands.EXIT)) != null) return;
            else System.out.println("Invalid Command");
        }
    }

    private void showMap(int x, int y) {
        switch (controller.showMap(x, y)) {
            case SUCCESS:
                //TODO handle showing map
            case X_OUT_OF_BOUNDS:
                System.out.println("Show map failed : Coordinate of x is out of bounds");
                break;
            case Y_OUT_OF_BOUNDS:
                System.out.println("Show map failed : Coordinate of y is out of bounds");
                break;
        }
    }
    private void moveMap(Matcher matcher) {
        if (Menu.checkBlankField(matcher.group("direction"))) {
            System.out.println("Move map failed : Blanked field");
            return;
        }
        String directions = Menu.handleDoubleQuote(matcher.group("direction"));
        switch (controller.moveMap(directions)) {
            case INVALID_DIRECTION:
                System.out.println("Move map failed : Invalid direction");
                break;
        }
    }

    private void showDetails(Matcher matcher) {
        if (Menu.checkBlankField(matcher.group("x")) || Menu.checkBlankField(matcher.group("y"))) {
            System.out.println("Show details failed : Blanked field");
            return;
        }
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        switch (controller.showDetails(x, y)) {
            case SUCCESS:
                //TODO showing details
            case X_OUT_OF_BOUNDS:
                System.out.println("Show details failed : Coordinate of x is out of bounds");
                break;
            case Y_OUT_OF_BOUNDS:
                System.out.println("Show details failed : Coordinate of y is out of bounds");
                break;
        }
    }

    private void setTextureOneBlock(Matcher matcher) {
        if (Menu.checkBlankField(matcher.group("x")) || Menu.checkBlankField(matcher.group("y")) ||
            Menu.checkBlankField(matcher.group("type"))) {
            System.out.println("Set texture failed : Blanked field");
            return;
        }
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        String type = Menu.handleDoubleQuote(matcher.group("type"));
        switch (controller.setTextureOfOneBlock(x, y, type)) {
            case SUCCESS:
                System.out.println("Set texture successfully");
                break;
            case X_OUT_OF_BOUNDS:
                System.out.println("SSet texture failed : Coordinate of x is out of bounds");
                break;
            case Y_OUT_OF_BOUNDS:
                System.out.println("Set texture failed : Coordinate of y is out of bounds");
                break;
            case INVALID_TYPE:
                System.out.println("Set texture failed : Invalid type");
                break;
        }
    }

    private void setTextureMultipleBlocks(Matcher matcher) {
        if (Menu.checkBlankField(matcher.group("x1")) || Menu.checkBlankField(matcher.group("x2")) ||
                Menu.checkBlankField(matcher.group("x1")) || Menu.checkBlankField(matcher.group("x2"))
                || Menu.checkBlankField(matcher.group("type"))) {
            System.out.println("Set texture failed : Blanked field");
            return;
        }
        int x1 = Integer.parseInt(matcher.group("x1"));
        int x2 = Integer.parseInt(matcher.group("x2"));
        int y1 = Integer.parseInt(matcher.group("y1"));
        int y2 = Integer.parseInt(matcher.group("y2"));
        String type = Menu.handleDoubleQuote(matcher.group("type"));
        switch (controller.setTextureMultipleBlocks(x1, x2, y1, y2, type)) {
            case SUCCESS:
                System.out.println("Set texture successfully");
                break;
            case X_OUT_OF_BOUNDS:
                System.out.println("Set texture failed : Coordinate of x is out of bounds");
                break;
            case Y_OUT_OF_BOUNDS:
                System.out.println("Set texture failed : Coordinate of y is out of bounds");
                break;
            case INVALID_TYPE:
                System.out.println("Set texture failed : Invalid type");
                break;
        }
    }

    private void clearBlock(Matcher matcher) {
        if (Menu.checkBlankField(matcher.group("x")) || Menu.checkBlankField(matcher.group("y"))) {
            System.out.println("Clear block failed : Blanked field");
            return;
        }
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        switch (controller.clearBlock(x, y)) {
            case SUCCESS:
                System.out.println("Block cleared successfully");
                break;
            case X_OUT_OF_BOUNDS:
                System.out.println("Clear block failed : Coordinate of x is out of bounds");
                break;
            case Y_OUT_OF_BOUNDS:
                System.out.println("Clear block failed : Coordinate of y is out of bounds");
                break;
        }
    }

    private void dropRock(Matcher matcher) {
        if (Menu.checkBlankField(matcher.group("x")) || Menu.checkBlankField(matcher.group("y")) ||
        Menu.checkBlankField(matcher.group("direction"))) {
            System.out.println("Drop rock failed : Blanked field");
            return;
        }
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        String direction = Menu.handleDoubleQuote(matcher.group("direction"));
        switch (controller.dropRock(x, y, direction)) {
            case SUCCESS:
                System.out.println("Rock dropped successfully");
                break;
            case X_OUT_OF_BOUNDS:
                System.out.println("Drop rock failed : Coordinate of x is out of bounds");
                break;
            case Y_OUT_OF_BOUNDS:
                System.out.println("Drop rock failed : Coordinate of y is out of bounds");
                break;
            case INVALID_DIRECTION:
                System.out.println("Drop rock failed : Invalid direction");
                break;
            case CELL_IS_FULL:
                System.out.println("Drop rock failed : Cell is full");
                break;
            case INAPPROPRIATE_TEXTURE:
                System.out.println("Drop rock failed : Inappropriate cell texture");
                break;
        }
    }

    private void dropTree(Matcher matcher) {
        if (Menu.checkBlankField(matcher.group("x")) || Menu.checkBlankField(matcher.group("y")) ||
                Menu.checkBlankField(matcher.group("type"))) {
            System.out.println("Drop tree failed : Blanked field");
            return;
        }
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        String type = Menu.handleDoubleQuote(matcher.group("type"));
        switch (controller.dropTree(x, y, type)) {
            case SUCCESS:
                System.out.println("Tree dropped successfully");
                break;
            case X_OUT_OF_BOUNDS:
                System.out.println("Drop tree failed : Coordinate of x is out of bounds");
                break;
            case Y_OUT_OF_BOUNDS:
                System.out.println("Drop tree failed : Coordinate of y is out of bounds");
                break;
            case INVALID_TYPE:
                System.out.println("Drop tree failed : Invalid type");
                break;
            case CELL_IS_FULL:
                System.out.println("Drop tree failed : Cell is full");
                break;
            case INAPPROPRIATE_TEXTURE:
                System.out.println("Drop tree failed : Inappropriate cell texture");
                break;
        }
    }

    private void dropBuilding(Matcher matcher) {
        if (Menu.checkBlankField(matcher.group("x")) || Menu.checkBlankField(matcher.group("y")) ||
                Menu.checkBlankField(matcher.group("type"))) {
            System.out.println("Drop building failed : Blanked field");
            return;
        }
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        String type = Menu.handleDoubleQuote(matcher.group("type"));
        switch (controller.dropBuilding(x, y, type)) {
            case SUCCESS:
                System.out.println("Building dropped successfully");
                break;
            case X_OUT_OF_BOUNDS:
                System.out.println("Drop building failed : Coordinate of x is out of bounds");
                break;
            case Y_OUT_OF_BOUNDS:
                System.out.println("Drop building failed : Coordinate of y is out of bounds");
                break;
            case INVALID_TYPE:
                System.out.println("Drop building failed : Invalid type");
                break;
            case CELL_IS_FULL:
                System.out.println("Drop building failed : Cell is full");
                break;
            case INAPPROPRIATE_TEXTURE:
                System.out.println("Drop building failed : Inappropriate cell texture");
                break;
        }
    }

    private void dropUnit(Matcher matcher) {
        if (Menu.checkBlankField(matcher.group("x")) || Menu.checkBlankField(matcher.group("y")) ||
                Menu.checkBlankField(matcher.group("type")) || Menu.checkBlankField(matcher.group("count"))) {
            System.out.println("Drop unit failed : Blanked field");
            return;
        }
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        String type = Menu.handleDoubleQuote(matcher.group("type"));
        int count = Integer.parseInt(matcher.group("count"));
        switch (controller.dropUnit(x, y, type, count)) {
            case SUCCESS:
                System.out.println("Unit dropped successfully");
                break;
            case X_OUT_OF_BOUNDS:
                System.out.println("Drop unit failed : Coordinate of x is out of bounds");
                break;
            case Y_OUT_OF_BOUNDS:
                System.out.println("Drop unit failed : Coordinate of y is out of bounds");
                break;
            case INVALID_TYPE:
                System.out.println("Drop unit failed : Invalid type");
                break;
            case INVALID_NUMBER:
                System.out.println("Drop unit failed : Invalid number");
                break;
            case CELL_IS_FULL:
                System.out.println("Drop unit failed : Cell is full");
                break;
            case INAPPROPRIATE_TEXTURE:
                System.out.println("Drop unit failed : Inappropriate cell texture");
                break;
        }
    }
}
