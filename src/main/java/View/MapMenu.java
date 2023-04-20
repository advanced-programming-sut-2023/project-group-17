package View;

import Controller.MapMenuController;
import View.Enums.Commands.MapMenuCommands;

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
                setTexture(matcher);
            else if ((matcher = MapMenuCommands.getMatcher(command, MapMenuCommands.SET_TEXTURE_MULTIPLE_BLOCKS)) != null)
                setTexture(matcher);
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
    }
    private void moveMap(Matcher matcher) {
    }

    private void showDetails(Matcher matcher) {
    }

    private void setTexture(Matcher matcher) {
    }

    private void clearBlock(Matcher matcher) {
    }

    private void dropRock(Matcher matcher) {
    }

    private void dropTree(Matcher matcher) {
    }

    private void dropBuilding(Matcher matcher) {
    }

    private void dropUnit(Matcher matcher) {
    }

}
