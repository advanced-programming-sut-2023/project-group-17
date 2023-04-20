package Controller;

import Model.User;
import Utils.CheckValidation;
import View.Enums.Messages.LoginMenuMessages;
import View.LoginMenu;
import jdk.jshell.execution.Util;

import java.util.Scanner;

import static Model.Database.*;
import static View.Menu.print;

public class LoginMenuController {

    public LoginMenuMessages loginUser(String username, String password, boolean stayLoggedIn) {
        if(getUserByUsername(username)==null)
            return LoginMenuMessages.USERNAME_DOES_NOT_EXISTS;

        User user = getUserByUsername(username);

        if(!user.getPassword().equals(password))
            return LoginMenuMessages.WRONG_PASSWORD;

        //TODO handle stay logged in
        setLoggedInUser(user);
        return LoginMenuMessages.SUCCESS;
    }

    public LoginMenuMessages forgetPassword(LoginMenu loginMenu, String username) {
        if(getUserByUsername(username)==null)
            return LoginMenuMessages.USERNAME_DOES_NOT_EXISTS;

        User user = getUserByUsername(username);
        print("please answer this question : " + user.getPasswordRecoveryQuestion());
        String answer = loginMenu.scan();
        if(!answer.equals(user.getPasswordRecoveryAnswer()))
            return LoginMenuMessages.WRONG_PASSWORD_RECOVERY_ANSWER;

        print("please enter your new password:");
        String newPassword = loginMenu.scan();

        if(!CheckValidation.isPasswordStrong(newPassword).equals(LoginMenuMessages.PASSWORD_IS_STRONG))
            return CheckValidation.isPasswordStrong(newPassword);

        user.setPassword(newPassword);
        return LoginMenuMessages.SUCCESS;
    }
}
