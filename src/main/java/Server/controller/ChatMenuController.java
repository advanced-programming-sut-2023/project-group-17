package Server.controller;

import Server.model.Chat;
import Server.model.Database;
import Server.model.Message;

import java.util.ArrayList;

public class ChatMenuController {
    public synchronized ArrayList<Chat> getAllChats(int lobbyCode, String username) {
        ArrayList<Chat> chats = new ArrayList<>();
        for (Chat chat : Database.getChats()) {
            if (chat.getLobbyCode() == lobbyCode && chat.getUsers().contains(username)) {
                chats.add(chat);
            }
        }
        return chats;
    }

    public synchronized Chat getUpdatedChat(int chat) {
        return Database.getChatByCode(chat);
    }

//    public Chat getUpdatedChat(Chat chat) {
//        for (Chat eachChat : Database.getChats()) {
//            if (eachChat.getCode() == chat.getCode()) {
//                Database.getChats().remove(eachChat);
//                Database.addChat(chat);
//                Database.saveChats();
//                return chat;
//            }
//        }
//        return null;
//    }

    public synchronized void updateChat(Message message, int chatCode) {
        Chat chat = Database.getChatByCode(chatCode);
        chat.getAllMessages().add(message);
        Database.saveChats();
    }

    public synchronized void saveChat(Chat chat) {
        Chat sampleChat = Database.getChatByCode(chat.getCode());
        if (sampleChat == null) {
            Database.addChat(chat);
        } else {
            Database.getChats().remove(sampleChat);
            Database.addChat(chat);
        }
        Database.saveChats();
    }

    public void editChat(int chatCode, Message message) {
        ArrayList<Message> messages = new ArrayList<>();
        Chat chat = Database.getChatByCode(chatCode);
        for (Message allMessage : chat.getAllMessages()) {
            if (message.getCode() == allMessage.getCode()) {
                allMessage.setContent(message.getContent());
            }
            messages.add(allMessage);
        }
        chat.setMessages(messages);
        Database.saveChats();
    }

    public void seenChats(int chatCode) {
        Chat chat = Database.getChatByCode(chatCode);
        for (Message allMessage : chat.getAllMessages()) {
            allMessage.setSeen(true);
        }
        Database.saveChats();
    }
}
