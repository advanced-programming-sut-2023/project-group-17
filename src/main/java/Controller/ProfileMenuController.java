package Controller;

import Model.Database;
import Model.User;
import Utils.CheckValidation;
import Utils.Randoms;
import View.Enums.Messages.ProfileMenuMessages;
import View.Enums.Messages.UtilsMessages;

import static Model.Database.*;

public class ProfileMenuController {
    public ProfileMenuMessages changeUsername(String username) {
        if (!username.matches("[A-Za-z0-9_]+"))
            return ProfileMenuMessages.INVALID_USERNAME;

        if (getUserByUsername(username) != null)
            return ProfileMenuMessages.USERNAME_EXISTS;

        if (getLoggedInUser().getUsername().equals(username))
            return ProfileMenuMessages.SAME_USERNAME;

        getLoggedInUser().setUsername(username);
        Database.saveUsers();
        return ProfileMenuMessages.SUCCESS;
    }

    public ProfileMenuMessages changeNickname(String nickname) {
        if (getLoggedInUser().getNickname().equals(nickname))
            return ProfileMenuMessages.SAME_NICKNAME;

        getLoggedInUser().setNickname(nickname);
        Database.saveUsers();
        return ProfileMenuMessages.SUCCESS;
    }

    public ProfileMenuMessages changePassword(String oldPassword, String newPassword) {
        if (!getLoggedInUser().getPassword().equals(User.SHA256Code(oldPassword)))
            return ProfileMenuMessages.INCORRECT_PASSWORD;

        if (getLoggedInUser().getPassword().equals(User.SHA256Code(newPassword)))
            return ProfileMenuMessages.SAME_PASSWORD;

        if (newPassword.equals("random"))
            return ProfileMenuMessages.RANDOM_PASSWORD;

        return ProfileMenuMessages.ENTER_PASSWORD_AGAIN;
    }

    public ProfileMenuMessages setNewPassword(String newPassword, String confirmationPassword) {
        if (!newPassword.equals(confirmationPassword))
            return ProfileMenuMessages.PASSWORDS_DO_NOT_MATCH;

        if (!CheckValidation.isPasswordStrong(newPassword).equals(UtilsMessages.PASSWORD_IS_STRONG)) {
            switch (CheckValidation.isPasswordStrong(newPassword)) {
                case SHORT_PASSWORD:
                    return ProfileMenuMessages.SHORT_PASSWORD;
                case PASSWORD_DOES_NOT_CONTAIN_LOWERCASE:
                    return ProfileMenuMessages.PASSWORD_DOES_NOT_CONTAIN_LOWERCASE;
                case PASSWORD_DOES_NOT_CONTAIN_INTEGER:
                    return ProfileMenuMessages.PASSWORD_DOES_NOT_CONTAIN_INTEGER;
                case PASSWORD_DOES_NOT_CONTAIN_UPPERCASE:
                    return ProfileMenuMessages.PASSWORD_DOES_NOT_CONTAIN_UPPERCASE;
                case PASSWORD_DOES_NOT_CONTAIN_SPECIFIC_CHARACTER:
                    return ProfileMenuMessages.PASSWORD_DOES_NOT_CONTAIN_SPECIFIC_CHARACTER;
                case PASSWORD_IS_STRONG:
                    return ProfileMenuMessages.PASSWORD_IS_STRONG;
            }
        }

        getLoggedInUser().setPassword(User.SHA256Code(newPassword));
        Database.saveUsers();
        return ProfileMenuMessages.SUCCESS;
    }

    public ProfileMenuMessages changeEmail (String email){
        if(emailExists(email))
            return ProfileMenuMessages.EMAIL_EXISTS;

        if(!email.matches("([A-Za-z0-9_.]+@[A-Za-z0-9_.]+\\.[A-Za-z0-9_.]+)"))
            return ProfileMenuMessages.INVALID_EMAIL;

        if(getLoggedInUser().getEmail().equals(email))
            return ProfileMenuMessages.SAME_EMAIL;

        getLoggedInUser().setEmail(email);
        Database.saveUsers();
        return ProfileMenuMessages.SUCCESS;
    }

    public ProfileMenuMessages changeSlogan (String slogan){
        //TODO: random slogan has bug
        if(getLoggedInUser().getSlogan().equals(slogan)) return ProfileMenuMessages.SAME_SLOGAN;
        if(slogan.equals("random")) return ProfileMenuMessages.RANDOM_SLOGAN;

        getLoggedInUser().setSlogan(slogan);
        Database.saveUsers();
        return ProfileMenuMessages.SUCCESS;
    }

    public ProfileMenuMessages removeSlogan() {
        if(getLoggedInUser().getSlogan() == null)
            return ProfileMenuMessages.EMPTY_SLOGAN;

        getLoggedInUser().setSlogan(null);
        Database.saveUsers();
        return ProfileMenuMessages.SUCCESS;
    }

    public String displaySlogan () {
        String slogan = getLoggedInUser().getSlogan();
        if(slogan != null)
            return "your slogan is \"" + slogan + "\"";
        return "there is no slogan to show";
    }
//TODO: display high score
    public String displayHighScore () {
        return null;
    }
//TODO: display rank
    public String displayRank () {
        return null;
    }

    public String displayProfile () {
        String result = "";
        String slogan = getLoggedInUser().getSlogan();
        if(slogan == null)
            slogan = "";
        result += "username : " + getLoggedInUser().getUsername() + '\n' +
                  "nickname : " + getLoggedInUser().getNickname() + '\n' +
                  "email : " + getLoggedInUser().getEmail() + '\n' +
                  "slogan : " + slogan + '\n';
        return result;
    }

    public String getRandomPassword() {
        return Randoms.generateRandomPassword();
    }

    public String getRandomSlogan() {
        return Randoms.generateRandomSlogan();
    }
}
