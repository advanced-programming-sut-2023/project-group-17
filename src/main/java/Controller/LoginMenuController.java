package Controller;

import Model.User;
import Utils.CheckValidation;
import View.Enums.Messages.SignupAndLoginMenuMessages;
import View.LoginMenu;
import java.util.Scanner;

import static Model.Database.*;
import static View.Menu.print;

public class LoginMenuController {

    public SignupAndLoginMenuMessages loginUser(String username, String password, boolean stayLoggedIn) {
        if(getUserByUsername(username) == null)
            return SignupAndLoginMenuMessages.USERNAME_DOES_NOT_EXISTS;

        User user = getUserByUsername(username);

        if(!user.getPassword().equals(password))
            return SignupAndLoginMenuMessages.WRONG_PASSWORD;

        //TODO: handle stay logged in
        setLoggedInUser(user);
        return SignupAndLoginMenuMessages.SUCCESS;
    }

    public SignupAndLoginMenuMessages forgetPassword(LoginMenu loginMenu, String username) {
        if(getUserByUsername(username) == null)
            return SignupAndLoginMenuMessages.USERNAME_DOES_NOT_EXISTS;

        User user = getUserByUsername(username);
        print("please answer this question : " + user.getPasswordRecoveryQuestion());
        String answer = loginMenu.scan();
        if(!answer.equals(user.getPasswordRecoveryAnswer()))
            return SignupAndLoginMenuMessages.WRONG_PASSWORD_RECOVERY_ANSWER;

        print("please enter your new password:");
        String newPassword = loginMenu.scan();

        if(!CheckValidation.isPasswordStrong(newPassword).equals(SignupAndLoginMenuMessages.PASSWORD_IS_STRONG))
            return (LoginMenuMessages) CheckValidation.isPasswordStrong(newPassword);

        user.setPassword(newPassword);
        return SignupAndLoginMenuMessages.SUCCESS;
    }
}
