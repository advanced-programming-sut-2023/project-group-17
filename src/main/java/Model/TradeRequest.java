package Model;

import View.TradeMenu;

public class TradeRequest {
    private final User senderUser;
    private final User recieverUser;
    private static int id = 1;
    private final String resourceType;
    private final int resourceAmount;
    private final int price;
    private final String message;
    boolean isAccepted;

    public TradeRequest(User senderUser, User recieverUser, String resourceType, int resourceAmount, int price, String message) {
        this.senderUser = senderUser;
        this.recieverUser = recieverUser;
        this.resourceType = resourceType;
        this.resourceAmount = resourceAmount;
        this.price = price;
        this.message = message;
        id++;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public User getSenderUser() {
        return senderUser;
    }

    public User getRecieverUser() {
        return recieverUser;
    }

    public static int getId() {
        return id;
    }

    public String getResourceType() {
        return resourceType;
    }

    public int getResourceAmount() {
        return resourceAmount;
    }

    public int getPrice() {
        return price;
    }

    public String getMessage() {
        return message;
    }

    public boolean isAccepted() {
        return isAccepted;
    }
}
