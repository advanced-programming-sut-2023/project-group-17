package Model;

import Model.Items.Resource;
import View.TradeMenu;

public class TradeRequest {
    private final User senderUser;
    private final User recieverUser;
    private static int idCount = 1;
    private final int id;
    private final Resource.resourceType resourceType;
    private final int resourceAmount;
    private final int price;
    private final String sentMessage;
    private String acceptMessage;
    boolean isAccepted = false;
    boolean isSeen = false;

    public TradeRequest(User senderUser, User recieverUser, Resource.resourceType resourceType, int resourceAmount, int price, String sentMessage) {
        this.senderUser = senderUser;
        this.recieverUser = recieverUser;
        this.resourceType = resourceType;
        this.resourceAmount = resourceAmount;
        this.price = price;
        this.sentMessage = sentMessage;
        this.id = idCount;
        idCount++;
    }
    public void setAccepted() {
        this.isAccepted = true;
    }

    public void setSeen() { this.isSeen = true; }
    public void setAcceptMessage(String acceptMessage) {
        this.acceptMessage = acceptMessage;
    }

    public User getSenderUser() {
        return senderUser;
    }

    public User getRecieverUser() {
        return recieverUser;
    }

    public int getId() {
        return id;
    }

    public Resource.resourceType getResourceType() {
        return resourceType;
    }

    public int getResourceAmount() {
        return resourceAmount;
    }

    public int getPrice() {
        return price;
    }

    public String getSentMessage() {
        return sentMessage;
    }
    public String getAcceptMessage() {
        return acceptMessage;
    }
    public boolean isAccepted() {
        return isAccepted;
    }
    public boolean isSeen() {
        return isSeen;
    }
}
