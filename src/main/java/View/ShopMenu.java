package View;

import Server.controller.ShopMenuController;
import View.Enums.Commands.ShopMenuCommands;

import java.util.regex.Matcher;

public class ShopMenu extends Menu{
    private ShopMenuController controller;

    public ShopMenu() {
        controller = new ShopMenuController();
    }

    @Override
    public void run() {
        System.out.println("entered shop menu successfully");
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
            else if((matcher = ShopMenuCommands.getMatcher(command, ShopMenuCommands.BACK)) != null) {
                System.out.println("entered game menu successfully");
                return;
            }
            else if((matcher = ShopMenuCommands.getMatcher(command, ShopMenuCommands.SHOW_CURRENT_MENU)) != null)
                System.out.println("Shop menu");
            else System.out.println("Invalid Command");

        }

    }

    private void showPriceList() {
        System.out.print(controller.showPriceList());
    }

    private void buyItem(Matcher matcher) {
        if(checkBlankField(matcher.group("name")) || checkBlankField(matcher.group("amount")))
            System.out.println("buy item failed : blank field");

        String itemName = handleDoubleQuote(matcher.group("name"));
        Integer amount = Integer.parseInt(matcher.group("amount"));

        switch (controller.buyItem(itemName, amount)) {
            case SUCCESS:
                System.out.println("item bought successfully");
                break;
            case NOT_ENOUGH_COIN:
                System.out.println("buy item failed : not enough coin");
                break;
            case INVALID_ITEM_NAME:
                System.out.println("buy item failed : invalid item name");
                break;
            case INVALID_ITEM_AMOUNT:
                System.out.println("buy item failed : invalid item amount");
                break;
        }

    }

    private void sellItem(Matcher matcher) {
        if(checkBlankField(matcher.group("name")) || checkBlankField(matcher.group("amount")))
            System.out.println("buy item failed : blank field");

        String itemName = handleDoubleQuote(matcher.group("name"));
        Integer amount = Integer.parseInt(matcher.group("amount"));

        switch (controller.sellItem(itemName, amount)) {
            case SUCCESS:
                System.out.println("item sold successfully");
                break;
            case INVALID_ITEM_AMOUNT:
                System.out.println("sell item failed : invalid item amount");
                break;
            case INVALID_ITEM_NAME:
                System.out.println("sell item failed : invalid item name");
                break;
            case ITEM_DOES_NOT_EXISTS:
                System.out.println("sell item failed : you don't have this item");
                break;
        }
    }
}
