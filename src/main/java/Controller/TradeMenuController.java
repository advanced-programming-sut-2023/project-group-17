package Controller;

import Model.Database;
import Model.Empire;
import Model.Items.*;
import Model.TradeRequest;
import Model.User;
import View.Enums.Messages.TradeMenuMessages;
import View.Enums.Types.TradeType;

public class TradeMenuController {
    Item item;
    public TradeMenuMessages tradeRequest(String itemName, int itemAmount, String message, String user, TradeType type) {
//        if(Item.getItemByName(itemName) == null) return TradeMenuMessages.INVALID_ITEM_NAME;
//        if(itemAmount <= 0) return TradeMenuMessages.INVALID_AMOUNT;
//        if(price < 0) return TradeMenuMessages.INVALID_PRICE;
        if (user.equals("")) return TradeMenuMessages.NO_USER_SELECTED;

        item = Item.getAvailableItems(itemName);
        if((item == null || item.getNumber() < itemAmount) && type.equals(TradeType.DONATE)) return TradeMenuMessages.INSUFFICIENT_ITEM_AMOUNT;

        Item.ItemType itemType = Item.getItemType(itemName);
        TradeRequest tradeRequest = new TradeRequest(Database.getCurrentUser(),
                itemType, itemAmount, message, type);

        String[] users = user.split(",");

        Database.getCurrentUser().getEmpire().addSentTradeRequests(tradeRequest);
        for (String sendToUser : users) {
            Database.getUserInTheGameByUsername(sendToUser).getEmpire().addReceivedTradeRequests(tradeRequest);
            if (type.equals(TradeType.DONATE)) {
                changeResourceAmount(Database.getUserInTheGameByUsername(sendToUser).getEmpire(), Database.getCurrentUser().getEmpire(), itemAmount, itemName);
            }
        }

//        for (User user1 : Database.getUsersInTheGame()) {
//            for (TradeRequest receivedTradeRequest : user1.getEmpire().getReceivedTradeRequests()) {
//                System.out.println("received trade request : " + receivedTradeRequest);
//            }
//        }

        return TradeMenuMessages.SUCCESS;
    }

    public TradeMenuMessages acceptRequest(int id, String message) {
        Empire receiverEmpire = Database.getCurrentUser().getEmpire();
//        if(receiverEmpire.getReceivedRequestById(id) == null) return TradeMenuMessages.ID_DOES_NOT_EXISTS;

        TradeRequest request = receiverEmpire.getReceivedRequestById(id);
        Empire senderEmpire = request.getSenderUser().getEmpire();
        int amount = request.getItemAmount();
        String itemName = request.getItemType().getName();

        changeResourceAmount(receiverEmpire, senderEmpire, amount, itemName);
//        double price = request.getPrice();

//        switch (Item.getItemByName(itemName)) {
//            case RESOURCE:
//                receiverEmpire.getResourceByName(itemName).changeNumber(-amount);
//                senderEmpire.getResourceByName(itemName).changeNumber(amount);
//                break;
//            case FOOD:
//                receiverEmpire.getFoodByName(itemName).changeNumber(-amount);
//                senderEmpire.getFoodByName(itemName).changeNumber(amount);
//                break;
//            case WEAPON:
//                receiverEmpire.getWeaponByName(itemName).changeNumber(-amount);
//                senderEmpire.getWeaponByName(itemName).changeNumber(amount);
//                break;
//        }

//        senderEmpire.changeCoins(-price);
//        receiverEmpire.changeCoins(price);
        receiverEmpire.getReceivedRequestById(id).setAcceptMessage(message);
        receiverEmpire.getReceivedRequestById(id).setAccepted();
        return TradeMenuMessages.SUCCESS;
    }

    public void changeResourceAmount(Empire receiverEmpire, Empire senderEmpire, int amount, String itemName) {
        switch (Item.getItemByName(itemName)) {
            case RESOURCE:
                receiverEmpire.getResourceByName(itemName).changeNumber(amount);
                senderEmpire.getResourceByName(itemName).changeNumber(-amount);
                System.out.println("receiver : " + receiverEmpire.getResourceByName(itemName).getNumber());
                System.out.println("sender : " + senderEmpire.getResourceByName(itemName).getNumber());
                break;
            case FOOD:
                receiverEmpire.getFoodByName(itemName).changeNumber(amount);
                senderEmpire.getFoodByName(itemName).changeNumber(-amount);
                System.out.println("receiver : " + receiverEmpire.getFoodByName(itemName).getNumber());
                System.out.println("sender : " + senderEmpire.getFoodByName(itemName).getNumber());
                break;
            case WEAPON:
                receiverEmpire.getWeaponByName(itemName).changeNumber(amount);
                senderEmpire.getWeaponByName(itemName).changeNumber(-amount);
                System.out.println("receiver : " + receiverEmpire.getWeaponByName(itemName).getNumber());
                System.out.println("sender : " + senderEmpire.getWeaponByName(itemName).getNumber());
                break;
        }
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

//    public String tradeList() {
//        String result = "";
//
//        for (TradeRequest request : Database.getCurrentUser().getEmpire().getReceivedTradeRequests()) {
//            result += receivedTradeToString(request);
//        }
//
//        return result;
//    }

//    public String tradeHistory() {
//        String result = "";
//
//        result += "Accepted Requests: " + "\n";
//        for (TradeRequest request : Database.getCurrentUser().getEmpire().getReceivedTradeRequests()) {
//            if(request.isAccepted())
//                result += receivedTradeToString(request);
//        }
//
//        result += "Sent Requests: " + "\n";
//        for (TradeRequest request : Database.getCurrentUser().getEmpire().getSentTradeRequests()) {
//            result += sentTradeToString(request);
//        }
//
//        return result;
//    }
//
//    public String showRequestsNotification() {
//        String result = "";
//
//        for (TradeRequest request : Database.getCurrentUser().getEmpire().getReceivedTradeRequests()) {
//            if(!request.isSeen()){
//                result += "id " + request.getId() + ") from " + request.getSenderUser() +
//                        " | message: " + request.getSentMessage() + "\n";
//                request.setSeen();
//            }
//        }
//
//        return result;
//    }

//    public String receivedTradeToString(TradeRequest request){
//        return "id " + request.getId() + ") from " + request.getSenderUser().getUsername() + " | resource type: " +
//                request.getItemType() + " | amount: " + request.getItemAmount() +
//                " | price: " + request.getPrice() + " | message: " + request.getSentMessage() + "\n";
//    }

//    public String sentTradeToString(TradeRequest request){
//        return "id " + request.getId() + " | resource type: " +
//                request.getItemType() + " | amount: " + request.getItemAmount() +
//                " | price: " + request.getPrice() + " | message: " + request.getSentMessage() + "\n";
//    }
}
