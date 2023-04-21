package Controller;

import Model.Database;
import Model.Items.Resource;
import Model.TradeRequest;
import Model.User;
import View.Enums.Messages.TradeMenuMessages;

import javax.xml.crypto.Data;
import java.util.concurrent.LinkedBlockingDeque;

public class TradeMenuController {
    public TradeMenuMessages tradeRequest(String resourceType, int resourceAmount, int price, String message, String username) {
        if(!Resource.contains(resourceType)) return TradeMenuMessages.INVALID_RESOURCE_TYPE;

        TradeRequest tradeRequest = new TradeRequest(Database.getLoggedInUser(), Database.getUserByUsername(username),
                resourceType, resourceAmount, price, message);
        Database.getLoggedInUser().getEmpire().addSentTradeRequests(tradeRequest);
        Database.getUserByUsername(username).getEmpire().addReceivedTradeRequests(tradeRequest);
        return TradeMenuMessages.SUCCESS;
    }

    public TradeMenuMessages acceptTrade(int id, String message) {
        Database.getLoggedInUser().getEmpire().getRecievedRequestById(id).setAccepted();
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
            result += recievedTradeToString(request);
        }

        return result;
    }

    public String tradeHistory() {
        String result = "";

        result += "Accepted Requests: " + "\n";
        for (TradeRequest request : Database.getLoggedInUser().getEmpire().getReceivedTradeRequests()) {
            if(request.isAccepted())
                result += recievedTradeToString(request);
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
                        " | message: " + request.getMessage() + "\n";
                request.setSeen();
            }
        }

        return result;
    }

    public String recievedTradeToString(TradeRequest request){
        return "id " + request.getId() + ") from " + request.getSenderUser() + " | resource type: " +
                request.getResourceType() + " | amount: " + request.getResourceAmount() +
                " | price: " + request.getPrice() + " | message: " + request.getMessage() + "\n";
    }

    public String sentTradeToString(TradeRequest request){
        return "id " + request.getId() + ") to " + request.getRecieverUser() + " | resource type: " +
                request.getResourceType() + " | amount: " + request.getResourceAmount() +
                " | price: " + request.getPrice() + " | message: " + request.getMessage() + "\n";
    }
}
