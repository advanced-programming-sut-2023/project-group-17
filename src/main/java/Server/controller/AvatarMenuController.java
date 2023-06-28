package Server.controller;

import Server.model.Database;
import Server.model.User;

import java.util.ArrayList;

import static Server.model.Database.getCurrentUser;

public class AvatarMenuController {
    public void changeAvatarCustom(String avatarPath) {
        Database.getCurrentUser().setAvatarPath(avatarPath);
        Database.getUserByUsername(Database.getCurrentUser().getUsername()).setAvatarPath(avatarPath);
        Database.saveUsers();
        Database.getAvatarPaths();
        Database.setStayLoggedInUser(getCurrentUser());
    }

    public void addUsers(ArrayList<String> list) {
        Database.loadUsers();
        Database.getAvatarPaths();
        for (User user : Database.getUsers()) {
            list.add(user.getUsername());
        }
    }

    public String getPathByNumber(int newValue) {
        if (Database.getUsers().get(newValue).getAvatarPath() == null)
            return Database.getCurrentUser().randomPathGenerator();
        return Database.getUsers().get(newValue).getAvatarPath();
    }

    public String getAvatarPath() {
        if (getCurrentUser().getAvatarPath() == null) {
            getCurrentUser().setAvatarPath(getCurrentUser().randomPathGenerator());
            Database.saveUsers();
        }
        return getCurrentUser().getAvatarPath();
    }
}
