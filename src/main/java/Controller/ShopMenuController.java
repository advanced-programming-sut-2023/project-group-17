package Controller;

import Model.Database;
import Model.Empire;
import Model.Items.Item;
import View.Enums.Messages.ShopMenuMessages;

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

    public ShopMenuMessages buyItem(String itemName, Integer amount) {
        if(Item.getItemByName(itemName) == null) return ShopMenuMessages.INVALID_ITEM_NAME;
        if(amount <= 0) return ShopMenuMessages.INVALID_ITEM_AMOUNT;
        if(Database.getLoggedInUser().getEmpire().getCoins() < Item.getItemType(itemName).getCost() * amount) return ShopMenuMessages.NOT_ENOUGH_COIN;

        Empire empire = Database.getLoggedInUser().getEmpire();
        Item.ItemType itemType = Item.getItemType(itemName);
        empire.changeCoins(-itemType.getCost());

        switch (Item.getItemByName(itemName)) {
            case RESOURCE:
                empire.getResourceByName(itemName).changeNumber(amount);
                break;
            case FOOD:
                empire.getFoodByName(itemName).changeNumber(amount);
                break;
            case WEAPON:
                empire.getWeaponByName(itemName).changeNumber(amount);
                break;
        }
        return ShopMenuMessages.SUCCESS;
    }

    public ShopMenuMessages sellItem(String itemName, Integer amount) {
        if(Item.getItemByName(itemName) == null) return ShopMenuMessages.INVALID_ITEM_NAME;
        if(amount <= 0) return ShopMenuMessages.INVALID_ITEM_AMOUNT;

        item = Item.getAvailableItems(itemName);
        if(item == null || item.getNumber() < amount) return ShopMenuMessages.ITEM_DOES_NOT_EXISTS;

        Empire empire = Database.getLoggedInUser().getEmpire();
        Item.ItemType itemType = Item.getItemType(itemName);
        empire.changeCoins(0.8 * itemType.getCost());

        switch (Item.getItemByName(itemName)) {
            case RESOURCE:
                empire.getResourceByName(itemName).changeNumber(-amount);
                break;
            case WEAPON:
                empire.getWeaponByName(itemName).changeNumber(-amount);
                break;
            case FOOD:
                empire.getFoodByName(itemName).changeNumber(-amount);
                break;
        }
        return ShopMenuMessages.SUCCESS;
    }
}
