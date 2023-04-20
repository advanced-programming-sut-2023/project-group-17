package Controller;

import Model.User;
import View.Enums.Messages.LoginMenuMessages;

import java.util.Scanner;

import static Model.Database.*;

public class LoginMenuController {

    public LoginMenuMessages loginUser(String username, String password, boolean stayLoggedIn) {
        if(getUserByUsername(username)==null)
            return LoginMenuMessages.USERNAME_DOES_NOT_EXISTS;

        User user = getUserByUsername(username);

        if(user.getPassword().equals(password))
            return LoginMenuMessages.WRONG_PASSWORD;

        //TODO handle stay logged in
        setLoggedInUser(user);
        return LoginMenuMessages.SUCCESS;
    }

    public LoginMenuMessages forgetPassword(Scanner scanner , String username) {
        if(getUserByUsername(username)==null)
            return LoginMenuMessages.USERNAME_DOES_NOT_EXISTS;

        User user = getUserByUsername(username);
        System.out.println("please answer this question : " + user.getPasswordRecoveryQuestion());
        String answer = scanner.nextLine();
        if(!answer.equals(user.getPasswordRecoveryAnswer()))
            return LoginMenuMessages.WRONG_PASSWORD_RECOVERY_ANSWER;

        System.out.println("please enter your new password:");
        String newPassword = scanner.nextLine();
        //TODO password validation method

        user.setPassword(newPassword);
        return LoginMenuMessages.SUCCESS;
    }
}
