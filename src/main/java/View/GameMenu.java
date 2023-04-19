package View;

import View.Enums.Commands.GameMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu extends Menu {

    @Override
    public void run(Scanner scanner) {
        String command = null;
        Matcher matcher;

        while (true) {
            command = scanner.nextLine();
            if((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.CHOOSE_GAME_MAP)) != null)
                chooseMapGame(matcher);
            else if((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.SHOW_MAP)) != null)
                showMap(matcher);
            else if((matcher = GameMenuCommands.getMatcher(command, GameMenuCommands.DEFINE_MAP_SIZE)) != null)
                defineMapSize(matcher);
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
            else System.out.println("Invalid Command");
        }

    }

    private void defineMapSize(Matcher matcher) {
    }

    private void showMap(Matcher matcher) {
    }

    private void chooseMapGame(Matcher matcher) {
    }
}
