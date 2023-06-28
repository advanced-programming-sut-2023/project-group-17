package Server.model;

import Server.model.Items.Item;
import Server.enums.Types.TradeType;

public class TradeRequest {
    private final User senderUser;
    private static int idCount = 1;
    private final int id;
    private final Item.ItemType itemType;
    private final int itemAmount;
//    private final int price;
    private final String sentMessage;
    private String acceptMessage;
    private TradeType tradeType;
    boolean isAccepted = false;
    boolean isSeen = false;

    public TradeRequest(User senderUser, Item.ItemType itemType, int itemAmount, String sentMessage, TradeType tradeType) {
        this.senderUser = senderUser;
        this.itemType = itemType;
        this.itemAmount = itemAmount;
//        this.price = price;
        this.sentMessage = sentMessage;
        this.tradeType = tradeType;
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

    public TradeType getTradeType() {
        return tradeType;
    }

    public void setTradeType(TradeType tradeType) {
        this.tradeType = tradeType;
    }

    public int getId() {
        return id;
    }

    public Item.ItemType getItemType() {
        return itemType;
    }

    public int getItemAmount() {
        return itemAmount;
    }

//    public int getPrice() {
//        return price;
//    }

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
