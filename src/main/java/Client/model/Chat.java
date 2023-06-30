package Client.model;

import Client.view.ChatController;
import Server.model.Database;
import Server.model.Lobby;

import java.io.Serializable;
import java.util.ArrayList;

public class Chat implements Serializable {
    private final int code;
    private String name;
    private final ArrayList<String> users;
    private final ArrayList<Message> messages = new ArrayList<>();
    private final int lobbyCode;

    public Chat(String name, ArrayList<String> members, int lobbyCode) {
        this.name = name;
        this.users = members;
        this.code = getChatCode();
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

    private int getChatCode() {
        int code = (int) (Math.random() * 1000);
        for (Chat chat : ChatController.getChats()) {
            if (chat != this && code == chat.getCode()) {
                code = getChatCode();
                break;
            }
        }
        return code;
    }

    public int getLobbyCode() {
        return lobbyCode;
    }
}
