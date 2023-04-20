package View;

import Controller.ShopMenuController;
import View.Enums.Commands.ShopMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ShopMenu extends Menu{
    private ShopMenuController controller;

    public ShopMenu() {
        controller = new ShopMenuController();
    }

    @Override
    public void run(Scanner scanner) {
        Matcher matcher;
        String command;

        while(true) {
            command = scanner.nextLine();
            if((matcher = ShopMenuCommands.getMatcher(command, ShopMenuCommands.SHOW_PRICE_LIST)) != null)
                showPriceList();
            else if((matcher = ShopMenuCommands.getMatcher(command, ShopMenuCommands.BUY_ITEM)) != null)
                buyItem(matcher);
            else if((matcher = ShopMenuCommands.getMatcher(command, ShopMenuCommands.SELL_ITEM)) != null)
                sellItem(matcher);
            else if((matcher = ShopMenuCommands.getMatcher(command, ShopMenuCommands.BACK)) != null)
                return;
            else System.out.println("Invalid Command");

        }

    }

    private void showPriceList() {

    }

    private void buyItem(Matcher matcher) {

    }

    private void sellItem(Matcher matcher) {

    }
}
