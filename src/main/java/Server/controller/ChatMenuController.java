package Server.controller;

import Server.model.Chat;
import Server.model.Database;
import Server.model.Message;

import java.util.ArrayList;

public class ChatMenuController {
    public ArrayList<Chat> getAllChats() {
        return Database.getChats();
    }

    public Chat getUpdatedChat(int chat) {
        for (Chat eachChat : Database.getChats()) {
            if (eachChat.getCode() == chat) {
                Database.saveChats();
                return eachChat;
            }
        }
        return null;
    }

    public void updateChat(Message message, int chatCode) {
        Chat chat = Database.getChatByCode(chatCode);
        chat.getAllMessages().add(message);
        Database.saveChats();
    }
}
