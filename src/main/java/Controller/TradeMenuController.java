package Controller;

import Model.Database;
import Model.User;
import View.Enums.Messages.TradeMenuMessages;

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
}
