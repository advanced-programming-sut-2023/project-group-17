package Server.controller;

import Server.model.Database;
import Server.model.User;
import Server.enums.Messages.ProfileMenuMessages;
import Server.Utils.Randoms;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

import static Server.model.Database.*;

public class ProfileMenuController {
    public ProfileMenuMessages changeUsername(String username) {
        if (!username.matches("[A-Za-z0-9_]+"))
            return ProfileMenuMessages.INVALID_USERNAME;

        if (getUserByUsername(username) != null)
            return ProfileMenuMessages.USERNAME_EXISTS;

        if (getCurrentUser().getUsername().equals(username))
            return ProfileMenuMessages.SAME_USERNAME;

        getCurrentUser().setUsername(username);
        Database.getUserByUsername(getCurrentUser().getUsername()).setUsername(username);
        Database.saveUsers();
        if (Database.getStayLoggedInUser() != null) Database.setStayLoggedInUser(getCurrentUser());
        return ProfileMenuMessages.SUCCESS;
    }

    public ProfileMenuMessages changeUsernameErrors(String username) {
        if (!username.matches("[A-Za-z0-9_]+"))
            return ProfileMenuMessages.INVALID_USERNAME;

        if (getUserByUsername(username) != null)
            return ProfileMenuMessages.USERNAME_EXISTS;

        if (getCurrentUser().getUsername().equals(username))
            return ProfileMenuMessages.SAME_USERNAME;

        return ProfileMenuMessages.SUCCESS;
    }

    public ProfileMenuMessages changeNickname(String nickname) {
        if (getCurrentUser().getNickname().equals(nickname))
            return ProfileMenuMessages.SAME_NICKNAME;

        getCurrentUser().setNickname(nickname);
        Database.getUserByUsername(getCurrentUser().getUsername()).setNickname(nickname);
        Database.saveUsers();
        if (Database.getStayLoggedInUser() != null) Database.setStayLoggedInUser(getCurrentUser());
        return ProfileMenuMessages.SUCCESS;
    }

//    public ProfileMenuMessages changeNicknameErrors(String nickname) {
//        if (getCurrentUser().getNickname().equals(nickname))
//            return ProfileMenuMessages.SAME_NICKNAME;
//
//        return ProfileMenuMessages.SUCCESS;
//    }

    public ProfileMenuMessages changePassword(String oldPassword, String newPassword) {
        if (!getCurrentUser().getPassword().equals(User.SHA256Code(oldPassword)))
            return ProfileMenuMessages.INCORRECT_PASSWORD;

        if (getCurrentUser().getPassword().equals(User.SHA256Code(newPassword)))
            return ProfileMenuMessages.SAME_PASSWORD;

        if (newPassword.equals("random"))
            return ProfileMenuMessages.RANDOM_PASSWORD;

        getCurrentUser().setPassword(User.SHA256Code(newPassword));
        Database.getUserByUsername(getCurrentUser().getUsername()).setPassword(User.SHA256Code(newPassword));
        Database.saveUsers();
        if (Database.getStayLoggedInUser() != null) Database.setStayLoggedInUser(getCurrentUser());
        return ProfileMenuMessages.SUCCESS;
    }

//    public ProfileMenuMessages setNewPassword(String newPassword, String confirmationPassword) {
//        if (!newPassword.equals(confirmationPassword))
//            return ProfileMenuMessages.PASSWORDS_DO_NOT_MATCH;
//
//        if (!CheckValidation.isPasswordStrong(newPassword).equals(UtilsMessages.PASSWORD_IS_STRONG)) {
//            switch (CheckValidation.isPasswordStrong(newPassword)) {
//                case SHORT_PASSWORD:
//                    return ProfileMenuMessages.SHORT_PASSWORD;
//                case PASSWORD_DOES_NOT_CONTAIN_LOWERCASE:
//                    return ProfileMenuMessages.PASSWORD_DOES_NOT_CONTAIN_LOWERCASE;
//                case PASSWORD_DOES_NOT_CONTAIN_INTEGER:
//                    return ProfileMenuMessages.PASSWORD_DOES_NOT_CONTAIN_INTEGER;
//                case PASSWORD_DOES_NOT_CONTAIN_UPPERCASE:
//                    return ProfileMenuMessages.PASSWORD_DOES_NOT_CONTAIN_UPPERCASE;
//                case PASSWORD_DOES_NOT_CONTAIN_SPECIFIC_CHARACTER:
//                    return ProfileMenuMessages.PASSWORD_DOES_NOT_CONTAIN_SPECIFIC_CHARACTER;
//                case PASSWORD_IS_STRONG:
//                    return ProfileMenuMessages.PASSWORD_IS_STRONG;
//            }
//        }
//
//        getCurrentUser().setPassword(User.SHA256Code(newPassword));
//        Database.saveUsers();
//        if (Database.getStayLoggedInUser() != null) Database.setStayLoggedInUser(getCurrentUser());
//        return ProfileMenuMessages.SUCCESS;
//    }

    public ProfileMenuMessages changeEmail(String email){
        if(emailExists(email))
            return ProfileMenuMessages.EMAIL_EXISTS;

        if(!email.matches("([A-Za-z0-9_.]+@[A-Za-z0-9_.]+\\.[A-Za-z0-9_.]+)"))
            return ProfileMenuMessages.INVALID_EMAIL;

        if(getCurrentUser().getEmail().equals(email))
            return ProfileMenuMessages.SAME_EMAIL;

        getCurrentUser().setEmail(email);
        Database.getUserByUsername(getCurrentUser().getUsername()).setEmail(email);
        Database.saveUsers();
        if (Database.getStayLoggedInUser() != null) Database.setStayLoggedInUser(getCurrentUser());
        return ProfileMenuMessages.SUCCESS;
    }

    public ProfileMenuMessages changeEmailError(String email) {
        if(emailExists(email))
            return ProfileMenuMessages.EMAIL_EXISTS;

        if(!email.matches("([A-Za-z0-9_.]+@[A-Za-z0-9_.]+\\.[A-Za-z0-9_.]+)"))
            return ProfileMenuMessages.INVALID_EMAIL;

        if(getCurrentUser().getEmail().equals(email))
            return ProfileMenuMessages.SAME_EMAIL;

        return ProfileMenuMessages.SUCCESS;
    }

    public ProfileMenuMessages changeSlogan (String slogan){
        if (slogan.equals("")) removeSlogan();

        getCurrentUser().setSlogan(slogan);
        Database.getUserByUsername(getCurrentUser().getUsername()).setSlogan(slogan);
        Database.saveUsers();
        if (Database.getStayLoggedInUser() != null) Database.setStayLoggedInUser(getCurrentUser());
        return ProfileMenuMessages.SUCCESS;
    }

    public ProfileMenuMessages removeSlogan() {
        if(getCurrentUser().getSlogan() == null)
            return ProfileMenuMessages.EMPTY_SLOGAN;

        getCurrentUser().setSlogan(null);
        Database.getUserByUsername(getCurrentUser().getUsername()).setSlogan(null);
        Database.saveUsers();
        if (Database.getStayLoggedInUser() != null) Database.setStayLoggedInUser(getCurrentUser());
        return ProfileMenuMessages.SUCCESS;
    }

    public String displaySlogan () {
        String slogan = getCurrentUser().getSlogan();
        if(slogan != null)
            return "your slogan is \"" + slogan + "\"";
        return "there is no slogan to show";
    }
    public String displayHighScore () {
        return "your HighScore is : " + Database.getCurrentUser().getHighScore();
    }
    public String displayRank () {
        return "Rank: " + getRankAmongPlayers() + " HighScore: " + Database.getCurrentUser().getHighScore();
    }

    public int getRankAmongPlayers() {
        Comparator<User> highScoreComparator = (user1, user2) -> user1.getHighScore().compareTo(user2.getHighScore());
        Comparator<User> nameComparator = (user1, user2) -> user1.getUsername().compareTo(user2.getUsername());
        ArrayList<User> copyUsers = (ArrayList<User>) Database.getUsers().stream().sorted(highScoreComparator.thenComparing(nameComparator)).collect(Collectors.toList());

        int rank = 0;
        int index = copyUsers.size();
        for (User currentUser : copyUsers) {
            if(currentUser.equals(Database.getCurrentUser()))
                rank = index;
            index--;
        }
        return rank;
    }

    public String displayProfile () {
        String result = "";
        String slogan = getCurrentUser().getSlogan();
        if(slogan == null)
            slogan = "";
        result += "username : " + getCurrentUser().getUsername() + '\n' +
                  "nickname : " + getCurrentUser().getNickname() + '\n' +
                  "email : " + getCurrentUser().getEmail() + '\n' +
                  "slogan : " + slogan + '\n';
        return result;
    }

    public String getUsername() {
        return getCurrentUser().getUsername();
    }

    public String getNickname() {
        return getCurrentUser().getNickname();
    }

    public String getEmail() {
        return getCurrentUser().getEmail();
    }

    public String getSlogan() {
        if (getCurrentUser().getSlogan() == null) return "Slogan is empty";
        return getCurrentUser().getSlogan();
    }

    public String getAvailableSlogans (int i) {
        if (i != -1) return Randoms.Slogans[i];
        return null;
    }

    public String getRandomPassword() {
        return Randoms.generateRandomPassword();
    }

    public String getRandomSlogan() {
        return Randoms.generateRandomSlogan();
    }

    public String getAvatarPath() {
        if (getCurrentUser().getAvatarPath() == null) {
            getCurrentUser().setAvatarPath(getCurrentUser().randomPathGenerator());
            Database.saveUsers();
        }
        return getCurrentUser().getAvatarPath();
    }
}
