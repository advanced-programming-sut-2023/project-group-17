package Server.controller;

import Server.model.Database;
import Server.model.User;

public class FriendshipMenuController {

    public String getUserRequestByIndex(int i, User user) {
        return user.getFriendReqs().get(i) + " : " + user.getFriendReqsSent().get(user.getFriendReqs().get(i));
    }

    public User getUserByUsername(String username) {
        return Database.getUserByUsername(username);
    }


    public void sendFriendRequest(User user, String username) {
        user.getFriendReqsSent().put(username, "Wait");
        user.getFriendReqs().add(username);
        Database.getUserByUsername(user.getUsername()).getFriendReqs().add(username);
        Database.saveUsers();
        Database.getUserByUsername(user.getUsername()).getFriendReqsSent().put(username, "Wait");
        Database.saveUsers();
    }

    public boolean haveUserInFriends(User user, User tmpUser) {
        return user.getFriendReqs().contains(tmpUser.getUsername());
    }
}
