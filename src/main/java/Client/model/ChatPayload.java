package Client.model;

import java.util.ArrayList;

public class ChatPayload {
    private final String header;
    private ArrayList<Chat> chats = null;
    private Chat chat = null;
    private Message message = null;

    public ChatPayload(String header) {
        this.header = header;
    }

    public ChatPayload(String header, Message message) {
        this.header = header;
        this.message = message;
    }

    public ChatPayload(String header, Chat chat) {
        this.header = header;
        this.chat = chat;
    }

    public ChatPayload(String header, ArrayList<Chat> chats) {
        this.header = header;
        this.chats = chats;
    }

    public ArrayList<Chat> getChats() {
        return chats;
    }

    public Chat getChat() {
        return chat;
    }

    public Message getMessage() {
        return message;
    }

    public String getHeader() {
        return header;
    }
}
