package Client.model;

import Client.view.ChatController;

import java.io.Serializable;
import java.util.Calendar;


public class Message implements Serializable {
    private final String sender;
    private String content;
    private boolean sent;
    private boolean seen;
    private transient boolean isSelected;
    private Calendar calendar;
    private final int code;

    public Message(String sender, String content, int code) {
        this.sender = sender;
        this.content = content;
        calendar = Calendar.getInstance();
        this.code = code;
    }

    public String getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void toggleSelected() {
        isSelected = !isSelected;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public int getCode() {
        return code;
    }
}
