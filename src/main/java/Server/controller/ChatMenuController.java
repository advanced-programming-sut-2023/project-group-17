package Server.controller;

import Server.model.Chat;
import Server.model.Database;

import java.util.ArrayList;

public class ChatMenuController {
    public ArrayList<Chat> getAllChats() {
        return Database.getChats();
    }

    public Chat getUpdatedChat(Chat chat) {
        for (Chat eachChat : Database.getChats()) {
            if (eachChat.equals(chat)) {
                return eachChat;
            }
        }
        return null;
    }
}
