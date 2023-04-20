package Model;

import View.TradeMenu;

public class TradeRequest {
    private final User senderUser;
    private final User recieverUser;
    private static int idCount = 1;
    private final int id;
    private final String resourceType;
    private final int resourceAmount;
    private final int price;
    private final String message;
    boolean isAccepted = false;
    boolean isSeen = false;

    public TradeRequest(User senderUser, User recieverUser, String resourceType, int resourceAmount, int price, String message) {
        this.senderUser = senderUser;
        this.recieverUser = recieverUser;
        this.resourceType = resourceType;
        this.resourceAmount = resourceAmount;
        this.price = price;
        this.message = message;
        this.id = idCount;
        idCount++;
    }

    public void setAccepted() {
        this.isAccepted = true;
    }

    public void setSeen() {this.isSeen = true; }

    public User getSenderUser() {
        return senderUser;
    }

    public User getRecieverUser() {
        return recieverUser;
    }

    public int getId() {
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
    public boolean isSeen() {
        return isSeen;
    }
}
