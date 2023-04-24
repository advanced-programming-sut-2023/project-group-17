package Controller;

import Model.Database;
import Model.Empire;
import Model.Items.Resource;
import Model.TradeRequest;
import Model.User;
import View.Enums.Messages.TradeMenuMessages;

public class TradeMenuController {
    public TradeMenuMessages tradeRequest(String resourceTypeName, int resourceAmount, int price, String message) {
        if(Resource.getResourceType(resourceTypeName) == null) return TradeMenuMessages.INVALID_RESOURCE_TYPE;
        if(resourceAmount <= 0) return TradeMenuMessages.INVALID_AMOUNT;
        if(price < 0) return TradeMenuMessages.INVALID_PRICE;

        Resource resource = Database.getLoggedInUser().getEmpire().getResourceByName(resourceTypeName);
        if(resource == null || resource.getNumber() < resourceAmount)
            return TradeMenuMessages.INSUFFICIENT_RESOURCE_AMOUNT;

        Resource.resourceType resourceType = Resource.getResourceType(resourceTypeName);
        TradeRequest tradeRequest = new TradeRequest(Database.getLoggedInUser(),
                resourceType, resourceAmount, price, message);

        Database.getLoggedInUser().getEmpire().addSentTradeRequests(tradeRequest);
        for (User user : Database.getUsers()) {
            user.getEmpire().addReceivedTradeRequests(tradeRequest);
        }
        return TradeMenuMessages.SUCCESS;
    }

    public TradeMenuMessages acceptTrade(int id, String message) {
        Empire receiverEmpire = Database.getLoggedInUser().getEmpire();
        if(receiverEmpire.getRecievedRequestById(id) == null) return TradeMenuMessages.ID_DOES_NOT_EXISTS;

        TradeRequest request = receiverEmpire.getRecievedRequestById(id);
        Empire senderEmpire = request.getSenderUser().getEmpire();
        String resourceName = request.getResourceType().getName();
        int amount = request.getResourceAmount();
        double price = request.getPrice();

        receiverEmpire.getResourceByName(resourceName).changeNumber(-amount);
        senderEmpire.getResourceByName(resourceName).changeNumber(amount);
        senderEmpire.changeCoins(-price);
        receiverEmpire.changeCoins(price);
        receiverEmpire.getRecievedRequestById(id).setAcceptMessage(message);
        receiverEmpire.getRecievedRequestById(id).setAccepted();
        return TradeMenuMessages.SUCCESS;
    }

    public String showUsersInTheGame() {
        String result = "";
        int index = 1;
        result += "users currently in the game: " + "\n";

        for (User user : Database.getUesrsInTheGame()) {
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
                request.getResourceType() + " | amount: " + request.getResourceAmount() +
                " | price: " + request.getPrice() + " | message: " + request.getSentMessage() + "\n";
    }

    public String sentTradeToString(TradeRequest request){
        return "id " + request.getId() + " | resource type: " +
                request.getResourceType() + " | amount: " + request.getResourceAmount() +
                " | price: " + request.getPrice() + " | message: " + request.getSentMessage() + "\n";
    }
}
