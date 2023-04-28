package Controller;

import Model.Database;
import Model.User;
import Utils.CheckValidation;
import View.Enums.Messages.LoginMenuMessages;
import View.Enums.Messages.UtilsMessages;
import static Model.Database.*;
import static View.Menu.print;
import static View.Menu.scan;

public class LoginMenuController {

    public LoginMenuMessages loginUser(String username, String password, boolean stayLoggedIn) {
        Database.loadUsers();
        if(getUserByUsername(username) == null)
            return LoginMenuMessages.USERNAME_DOES_NOT_EXISTS;

        User user = getUserByUsername(username);

        if(!user.getPassword().equals(User.SHA256Code(password)))
            return LoginMenuMessages.WRONG_PASSWORD;

        if (stayLoggedIn) Database.setStayLoggedInUser(user);
        setLoggedInUser(user);
        Database.loadUnits();
        Database.loadBuildings();
        return LoginMenuMessages.SUCCESS;
    }

    public LoginMenuMessages forgetPassword(String username) {
        if(getUserByUsername(username) == null)
            return LoginMenuMessages.USERNAME_DOES_NOT_EXISTS;

        User user = getUserByUsername(username);
        print("please answer this question : " + user.getPasswordRecoveryQuestion());
        String answer = scan();
        if(!answer.equals(user.getPasswordRecoveryAnswer()))
            return LoginMenuMessages.WRONG_PASSWORD_RECOVERY_ANSWER;

        print("please enter your new password:");
        String newPassword = scan();

        if(!CheckValidation.isPasswordStrong(newPassword).equals(UtilsMessages.PASSWORD_IS_STRONG)) {
            switch (CheckValidation.isPasswordStrong(newPassword)) {
                case SHORT_PASSWORD:
                    return LoginMenuMessages.SHORT_PASSWORD;
                case PASSWORD_DOES_NOT_CONTAIN_LOWERCASE:
                    return LoginMenuMessages.PASSWORD_DOES_NOT_CONTAIN_LOWERCASE;
                case PASSWORD_DOES_NOT_CONTAIN_INTEGER:
                    return LoginMenuMessages.PASSWORD_DOES_NOT_CONTAIN_INTEGER;
                case PASSWORD_DOES_NOT_CONTAIN_UPPERCASE:
                    return LoginMenuMessages.PASSWORD_DOES_NOT_CONTAIN_UPPERCASE;
                case PASSWORD_DOES_NOT_CONTAIN_SPECIFIC_CHARACTER:
                    return LoginMenuMessages.PASSWORD_DOES_NOT_CONTAIN_SPECIFIC_CHARACTER;
                case PASSWORD_IS_STRONG:
                    return LoginMenuMessages.PASSWORD_IS_STRONG;
            }
        }

        user.setPassword(User.SHA256Code(newPassword));
        Database.saveUsers();
        return LoginMenuMessages.SUCCESS;
    }

    public boolean checkStayLoggedIn() {
        if (Database.getStayLoggedInUser() != null) {
            Database.setLoggedInUser(Database.getStayLoggedInUser());
            return true;
        }
        return false;
    }
}
