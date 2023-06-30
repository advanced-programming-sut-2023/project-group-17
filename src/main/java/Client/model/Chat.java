package Client.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Chat implements Serializable {
    private String name;
    private final ArrayList<String> users;
    private final ArrayList<Message> messages = new ArrayList<>();

    public Chat(String name, ArrayList<String> members) {
        this.name = name;
        this.users = members;
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

}
