package Controller;

import Model.Database;

public class AvatarMenuController {
    public void changeAvatarCustom(String avatarPath) {
        Database.getCurrentUser().setAvatarPath(avatarPath);
        Database.getUserByUsername(Database.getCurrentUser().getUsername()).setAvatarPath(avatarPath);
        Database.saveUsers();
        Database.getAvatarPaths();
    }
}
