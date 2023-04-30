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
        //TODO: storage capacity must be added

        Empire empire = Database.getLoggedInUser().getEmpire();
        TradableItems.TradableItemType tradableItemType = TradableItems.getTradableItemType(itemName);

        empire.changeCoins(-tradableItemType.getCost());
        //TODO: item must be added to the storage

        return null;
    }

    public ShopMenuMessages sellItem(String itemName, Integer amount) {
        if(TradableItems.getTradableItemType(itemName) == null) return ShopMenuMessages.INVALID_ITEM_NAME;
        if(amount <= 0) return ShopMenuMessages.INVALID_ITEM_AMOUNT;
        if(Database.getLoggedInUser().getEmpire().getTradableItemAmount(itemName) < amount) return ShopMenuMessages.ITEM_DOES_NOT_EXISTS;

        Empire empire = Database.getLoggedInUser().getEmpire();
        TradableItems.TradableItemType tradableItemType = TradableItems.getTradableItemType(itemName);

        empire.changeCoins(0.9 * tradableItemType.getCost());
        //TODO: remove item from storage;

        return null;
    }
}
