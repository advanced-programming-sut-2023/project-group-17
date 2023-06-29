package Server.controller;

import Server.model.Database;
import Server.model.User;

import java.util.ArrayList;

import static Server.model.Database.getCurrentUser;

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
        Database.getUserByUsername(username).getReceivedRequests().add(user.getUsername());
        Database.saveUsers();
    }

    public boolean haveUserInFriendsRequests(User user, User tmpUser) {
        return user.getFriendReqs().contains(tmpUser.getUsername());
    }

    public int getNumberOfFriendsRequests(User user) {
        return user.getReceivedRequests().size();
    }

    public String getFriendsRequestByIndex(User user, int index) {
        return user.getReceivedRequests().get(index % user.getReceivedRequests().size());
    }

    public void acceptFriendRequest(User user, String username) {
        Database.getUserByUsername(username).getFriendReqsSent().put(user.getUsername(), "Accept");
        Database.saveUsers();
        Database.getUserByUsername(username).getFriends().add(user.getUsername());
        Database.saveUsers();
        user.getReceivedRequests().remove(username);
        Database.getUserByUsername(user.getUsername()).getReceivedRequests().remove(username);
        Database.saveUsers();
        user.getFriends().add(username);
        Database.getUserByUsername(user.getUsername()).getFriends().add(username);
        Database.saveUsers();
    }

    public ArrayList<String> getFriends(User user) {
        return new ArrayList<>(user.getFriends());
    }

    public boolean canSendRequest(User user, User tmpUser) {
        if (tmpUser == null || tmpUser.getUsername().equals(user.getUsername()) ||
                haveUserInFriendsRequests(user, tmpUser)) return false;
        if (user.getFriends().contains(tmpUser.getUsername())) return false;
        if (user.getReceivedRequests().contains(tmpUser.getUsername())) return false;
        return true;
    }
}
