package Server.controller;

import Server.model.Database;
import Server.model.Empire;
import Server.model.Items.Item;
import Server.enums.Messages.ShopMenuMessages;

public class ShopMenuController {
    Item item;
    public String showPriceList() {
        String result = "";
        int index = 1;
        for (Item.ItemType itemType : Item.ItemType.values()) {
            result += index + ") " + itemType.getName() + " | " + itemType.getCost() + " coins" + '\n';
            index++;
        }
        return result;
    }

    public ShopMenuMessages buyItem(String itemName, int amount) {
//        if(Item.getItemByName(itemName) == null) return ShopMenuMessages.INVALID_ITEM_NAME;
//        if(amount <= 0) return ShopMenuMessages.INVALID_ITEM_AMOUNT;
        if(Database.getCurrentUser().getEmpire().getCoins() < Item.getItemType(itemName).getCost() * amount) return ShopMenuMessages.NOT_ENOUGH_COIN;

        Empire empire = Database.getCurrentUser().getEmpire();
        System.out.println("before coin : " + empire.getCoins());

        Item.ItemType itemType = Item.getItemType(itemName);
        empire.changeCoins(-itemType.getCost());

        switch (Item.getItemByName(itemName)) {
            case RESOURCE:
                empire.getResourceByName(itemName).changeNumber(amount);
                System.out.println(itemName + " : " + empire.getResourceByName(itemName).getNumber());
                break;
            case FOOD:
                empire.getFoodByName(itemName).changeNumber(amount);
                System.out.println(itemName + " : " + empire.getFoodByName(itemName).getNumber());
                break;
            case WEAPON:
                empire.getWeaponByName(itemName).changeNumber(amount);
                System.out.println(itemName + " : " + empire.getWeaponByName(itemName).getNumber());
                break;
        }
        System.out.println("after coin : " + empire.getCoins());
        return ShopMenuMessages.SUCCESS;
    }

    public ShopMenuMessages sellItem(String itemName, int amount) {
//        if(Item.getItemByName(itemName) == null) return ShopMenuMessages.INVALID_ITEM_NAME;
//        if(amount <= 0) return ShopMenuMessages.INVALID_ITEM_AMOUNT;

        item = Item.getAvailableItems(itemName);
        if(item == null || item.getNumber() < amount) return ShopMenuMessages.ITEM_DOES_NOT_EXISTS;

        Empire empire = Database.getCurrentUser().getEmpire();
        System.out.println("before coin : " + empire.getCoins());

        Item.ItemType itemType = Item.getItemType(itemName);
        empire.changeCoins(0.8 * itemType.getCost());

        switch (Item.getItemByName(itemName)) {
            case RESOURCE:
                empire.getResourceByName(itemName).changeNumber(-amount);
                System.out.println(itemName + " : " + empire.getResourceByName(itemName).getNumber());
                break;
            case WEAPON:
                empire.getWeaponByName(itemName).changeNumber(-amount);
                System.out.println(itemName + " : " + empire.getWeaponByName(itemName).getNumber());
                break;
            case FOOD:
                System.out.println(itemName + " : " + empire.getFoodByName(itemName).getNumber());
                empire.getFoodByName(itemName).changeNumber(-amount);
                break;
        }
        System.out.println("after coin : " + empire.getCoins());
        return ShopMenuMessages.SUCCESS;
    }
}
