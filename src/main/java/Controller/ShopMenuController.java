package Controller;

import Model.Database;
import Model.Empire;
import Model.Items.TradableItems;
import View.Enums.Messages.ShopMenuMessages;

public class ShopMenuController {
    public String showPriceList() {
        String result = "";
        int index = 1;
        for (TradableItems.TradableItemType tradableItemType : TradableItems.TradableItemType.values()) {
            result += index + ") " + tradableItemType.getName() + " | " + tradableItemType.getCost() + " coins" + '\n';
            index++;
        }
        return result;
    }

    public ShopMenuMessages buyItem(String itemName, Integer amount) {
        if(TradableItems.getTradableItemType(itemName) == null) return ShopMenuMessages.INVALID_ITEM_NAME;
        if(amount <= 0) return ShopMenuMessages.INVALID_ITEM_AMOUNT;
        if(Database.getLoggedInUser().getEmpire().getCoins() < TradableItems.getTradableItemType(itemName).getCost() * amount) return ShopMenuMessages.NOT_ENOUGH_COIN;

        Empire empire = Database.getLoggedInUser().getEmpire();
        TradableItems.TradableItemType tradableItemType = TradableItems.getTradableItemType(itemName);

        empire.changeCoins(-tradableItemType.getCost());
        empire.getTradableItemByName(itemName).changeNumber(amount);
        return ShopMenuMessages.SUCCESS;
    }

    public ShopMenuMessages sellItem(String itemName, Integer amount) {
        if(TradableItems.getTradableItemType(itemName) == null) return ShopMenuMessages.INVALID_ITEM_NAME;
        if(amount <= 0) return ShopMenuMessages.INVALID_ITEM_AMOUNT;
        if(Database.getLoggedInUser().getEmpire().getTradableItemAmount(itemName) < amount) return ShopMenuMessages.ITEM_DOES_NOT_EXISTS;

        Empire empire = Database.getLoggedInUser().getEmpire();
        TradableItems.TradableItemType tradableItemType = TradableItems.getTradableItemType(itemName);

        empire.changeCoins(0.8 * tradableItemType.getCost());
        empire.getTradableItemByName(itemName).changeNumber(-amount);
        return ShopMenuMessages.SUCCESS;
    }
}
