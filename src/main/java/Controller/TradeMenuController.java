package Controller;

import Model.Database;
import Model.TradeRequest;
import Model.User;
import View.Enums.Messages.TradeMenuMessages;

import java.util.concurrent.LinkedBlockingDeque;

public class TradeMenuController {
    public TradeMenuMessages tradeRequest(String resourceType, int resourceAmount, int price, String message, String username) {

    }

    public TradeMenuMessages acceptTrade(int id, String message) {

    }

    public String showUsersInTheGame() {
        String result = "";
        int index = 1;

        for (User user : Database.getUesrsInTheGame()) {
            result += index + ") " + user.getUsername() + "\n";
            index++;
        }

        return result;
    }

    public String tradeList() {
        String result = "";

        for (TradeRequest request : Database.getLoggedInUser().getEmpire().getReceivedTradeRequests()) {
            result += tradeToString(request);
        }

        return result;
    }

    public String tradeHistory() {
        String result = "";

        result += "Accepted Requests: " + "\n";
        for (TradeRequest request : Database.getLoggedInUser().getEmpire().getReceivedTradeRequests()) {
            if(request.isAccepted())
                result += tradeToString(request);
        }

        result += "Sent Requests: " + "\n";
        for (TradeRequest request : Database.getLoggedInUser().getEmpire().getSentTradeRequests()) {
            result += "id " + request.getId() + ") to " + request.getRecieverUser() + " | resource type: " +
                    request.getResourceType() + " | amount: " + request.getResourceAmount() +
                    " | price: " + request.getPrice() + " | message: " + request.getMessage() + "\n";
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

    public String tradeToString(TradeRequest request){
        return "id " + request.getId() + ") from " + request.getSenderUser() + " | resource type: " +
                request.getResourceType() + " | amount: " + request.getResourceAmount() +
                " | price: " + request.getPrice() + " | message: " + request.getMessage() + "\n";
    }
}
