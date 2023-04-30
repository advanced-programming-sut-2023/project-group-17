package Controller;

import Model.Database;
import Model.Empire;
import Model.Items.TradableItems;
import Model.TradeRequest;
import Model.User;
import View.Enums.Messages.TradeMenuMessages;

public class TradeMenuController {
    public TradeMenuMessages tradeRequest(String itemTypeName, int itemAmount, int price, String message) {
        if(TradableItems.getTradableItemType(itemTypeName) == null) return TradeMenuMessages.INVALID_ITEM_TYPE;
        if(itemAmount <= 0) return TradeMenuMessages.INVALID_AMOUNT;
        if(price < 0) return TradeMenuMessages.INVALID_PRICE;

        TradableItems tradableItems = Database.getLoggedInUser().getEmpire().getTradableItemsByName(itemTypeName);
        if(tradableItems == null || tradableItems.getNumber() < itemAmount)
            return TradeMenuMessages.INSUFFICIENT_ITEM_AMOUNT;

        TradableItems.tradableItemType itemType = TradableItems.getTradableItemType(itemTypeName);
        TradeRequest tradeRequest = new TradeRequest(Database.getLoggedInUser(),
                itemType, itemAmount, price, message);

        Database.getLoggedInUser().getEmpire().addSentTradeRequests(tradeRequest);
        for (User user : Database.getUsers()) {
            user.getEmpire().addReceivedTradeRequests(tradeRequest);
        }
        return TradeMenuMessages.SUCCESS;
    }

    public TradeMenuMessages acceptTrade(int id, String message) {
        Empire receiverEmpire = Database.getLoggedInUser().getEmpire();
        if(receiverEmpire.getReceivedRequestById(id) == null) return TradeMenuMessages.ID_DOES_NOT_EXISTS;

        TradeRequest request = receiverEmpire.getReceivedRequestById(id);
        Empire senderEmpire = request.getSenderUser().getEmpire();
        String itemName = request.getItemType().getName();
        int amount = request.getResourceAmount();
        double price = request.getPrice();

        receiverEmpire.getTradableItemsByName(itemName).changeNumber(-amount);
        senderEmpire.getTradableItemsByName(itemName).changeNumber(amount);
        senderEmpire.changeCoins(-price);
        receiverEmpire.changeCoins(price);
        receiverEmpire.getReceivedRequestById(id).setAcceptMessage(message);
        receiverEmpire.getReceivedRequestById(id).setAccepted();
        return TradeMenuMessages.SUCCESS;
    }

    public String showUsersInTheGame() {
        String result = "";
        int index = 1;
        result += "users currently in the game: " + "\n";

        for (User user : Database.getUsersInTheGame()) {
            result += index + ") " + user.getUsername() + "\n";
            index++;
        }

        return result;
    }

    public String tradeList() {
        String result = "";

        for (TradeRequest request : Database.getLoggedInUser().getEmpire().getReceivedTradeRequests()) {
            result += receivedTradeToString(request);
        }

        return result;
    }

    public String tradeHistory() {
        String result = "";

        result += "Accepted Requests: " + "\n";
        for (TradeRequest request : Database.getLoggedInUser().getEmpire().getReceivedTradeRequests()) {
            if(request.isAccepted())
                result += receivedTradeToString(request);
        }

        result += "Sent Requests: " + "\n";
        for (TradeRequest request : Database.getLoggedInUser().getEmpire().getSentTradeRequests()) {
            result += sentTradeToString(request);
        }

        return result;
    }

    public String showRequestsNotification() {
        String result = "";

        for (TradeRequest request : Database.getLoggedInUser().getEmpire().getReceivedTradeRequests()) {
            if(!request.isSeen()){
                result += "id " + request.getId() + ") from " + request.getSenderUser() +
                        " | message: " + request.getSentMessage() + "\n";
                request.setSeen();
            }
        }

        return result;
    }

    public String receivedTradeToString(TradeRequest request){
        return "id " + request.getId() + ") from " + request.getSenderUser() + " | resource type: " +
                request.getItemType() + " | amount: " + request.getResourceAmount() +
                " | price: " + request.getPrice() + " | message: " + request.getSentMessage() + "\n";
    }

    public String sentTradeToString(TradeRequest request){
        return "id " + request.getId() + " | resource type: " +
                request.getItemType() + " | amount: " + request.getResourceAmount() +
                " | price: " + request.getPrice() + " | message: " + request.getSentMessage() + "\n";
    }
}
