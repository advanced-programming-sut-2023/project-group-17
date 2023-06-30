package Server.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Chat implements Serializable {
    private String name;
    private final ArrayList<String> users;
    private ArrayList<Message> messages = new ArrayList<>();
    private final int code;
    private final int lobbyCode;

    public Chat(String name, ArrayList<String> members, int code, int lobbyCode) {
        this.name = name;
        this.users = members;
        this.code = code;
        this.lobbyCode = lobbyCode;
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public ArrayList<Message> getAllMessages() {
        return messages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public int getCode() {
        return code;
    }

    public int getLobbyCode() {
        return lobbyCode;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }
}
