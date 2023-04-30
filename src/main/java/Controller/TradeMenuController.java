package Controller;

import Model.Database;
import Model.Empire;
import Model.Items.*;
import Model.TradeRequest;
import Model.User;
import View.Enums.Messages.TradeMenuMessages;

public class TradeMenuController {
    Item item;
    public TradeMenuMessages tradeRequest(String itemTypeName, int itemAmount, int price, String message) {
        if(getItemByName(itemTypeName) == null) return TradeMenuMessages.INVALID_ITEM_NAME;
        if(itemAmount <= 0) return TradeMenuMessages.INVALID_AMOUNT;
        if(price < 0) return TradeMenuMessages.INVALID_PRICE;
        if(item == null || item.getNumber() < itemAmount) return TradeMenuMessages.INSUFFICIENT_ITEM_AMOUNT;

        Item.ItemType itemType = Item.getItemType(itemTypeName);
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
        int amount = request.getItemAmount();
        double price = request.getPrice();

        switch (getItemByName(itemName)) {
            case RESOURCE:
                receiverEmpire.getResourceByName(itemName).changeNumber(-amount);
                senderEmpire.getResourceByName(itemName).changeNumber(amount);
                break;
            case FOOD:
                receiverEmpire.getFoodByName(itemName).changeNumber(-amount);
                senderEmpire.getFoodByName(itemName).changeNumber(amount);
                break;
            case WEAPON:
                receiverEmpire.getWeaponByName(itemName).changeNumber(-amount);
                senderEmpire.getWeaponByName(itemName).changeNumber(amount);
                break;
        }

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
                request.getItemType() + " | amount: " + request.getItemAmount() +
                " | price: " + request.getPrice() + " | message: " + request.getSentMessage() + "\n";
    }

    public String sentTradeToString(TradeRequest request){
        return "id " + request.getId() + " | resource type: " +
                request.getItemType() + " | amount: " + request.getItemAmount() +
                " | price: " + request.getPrice() + " | message: " + request.getSentMessage() + "\n";
    }

    public ItemTypes getItemByName(String name) {
        Empire empire = Database.getStayLoggedInUser().getEmpire();

        if(empire.getFoodByName(name) != null) {
            item = empire.getFoodByName(name);
            return ItemTypes.FOOD;
        }
        else if(empire.getWeaponByName(name) != null) {
            item = empire.getWeaponByName(name);
            return ItemTypes.WEAPON;
        }
        else if(empire.getResourceByName(name) != null) {
            item = empire.getResourceByName(name);
            return ItemTypes.RESOURCE;
        }
            item = null;
            return null;
    }
}
