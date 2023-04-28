package View;

import Controller.GameMenuController;
import Model.Database;
import View.Enums.Commands.GameMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu extends Menu {

    private GameMenuController controller;
    private MapMenu mapMenu;

    public GameMenu() {
        this.controller = new GameMenuController();
        mapMenu = new MapMenu();
    }
    @Override
    public void run(Scanner scanner) {
        String command = null;
        Matcher matcher;
        chooseMapGame();

        while (true) {
            command = scanner.nextLine();
            if((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.SHOW_MAP)) != null)
                showMap(matcher);

            else if(GameMenuCommands.getMatcher(command, GameMenuCommands.NEXT_TURN) != null)
                nextTurn();

            else if(GameMenuCommands.getMatcher(command, GameMenuCommands.ENTER_EMPIRE_MENU) != null)
                new EmpireMenu().run(scanner);

            else if(GameMenuCommands.getMatcher(command, GameMenuCommands.ENTER_BUILDING_MENU) != null)
                new BuildingMenu().run(scanner);

            else if(GameMenuCommands.getMatcher(command, GameMenuCommands.ENTER_UNIT_MENU) != null)
                new UnitMenu().run(scanner);

            else if(GameMenuCommands.getMatcher(command, GameMenuCommands.ENTER_TRADE_MENU) != null)
                new TradeMenu().run(scanner);

            else if(GameMenuCommands.getMatcher(command, GameMenuCommands.ENTER_SHOP_MENU) != null)
                new ShopMenu().run(scanner);

            else if(GameMenuCommands.getMatcher(command, GameMenuCommands.ENTER_MAP_MENU) != null)
                mapMenu.run(scanner);

            else System.out.println("Invalid Command");
        }
    }

    private void showMap(Matcher matcher) {
        if(checkBlankField(matcher.group("x")) || checkBlankField(matcher.group("y"))) {
            System.out.println("show map failed : blank field");
            return;
        }

        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));

        switch (controller.showMap(x , y)) {
            case X_OUT_OF_BOUNDS:
                System.out.println("show map failed : x out of bounds");
                break;
            case Y_OUT_OF_BOUNDS:
                System.out.println("show map failed : y out of bounds");
                break;
            case SUCCESS:
                mapMenu.showMap(x, y);
                mapMenu.run(scanner);
                break;
        }
    }

    private void chooseMapGame() {
        System.out.println(controller.chooseMap());

        int mapId = scanner.nextInt();

        if(mapId == 0) {
            System.out.println("enter width and length");
            int width = scanner.nextInt();
            int length = scanner.nextInt();
            switch (controller.createNewMap(width, length)) {
                case INVALID_LENGTH:
                    System.out.println("create map failed : invalid length");
                    chooseMapGame();
                    break;
                case INVALID_WIDTH:
                    System.out.println("create map failed : invalid width");
                    chooseMapGame();
                    break;
                case SUCCESS:
                    System.out.println("map created successfully");
                    break;
            }
            return;
        }

        switch (controller.chooseMapGame(mapId)) {
            case INVALID_MAP_NUMBER:
                System.out.println("choose map failed : id does not exist");
                break;
            case SUCCESS:
                System.out.println("map chose successfully");
                break;
        }
    }

    public void nextTurn() {
        controller.nextTurn();
    }
}
