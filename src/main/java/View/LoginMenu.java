package View;

import Controller.LoginMenuController;
import View.Enums.Commands.LoginMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu extends Menu {
    private LoginMenuController controller;

    public LoginMenu(){
         this.controller = new LoginMenuController();
    }

    public void run(Scanner scanner){
        String command;
        Matcher matcher = null;

        while (true){
            command = scanner.nextLine();
            if((matcher = LoginMenuCommands.getMatcher(command, LoginMenuCommands.USER_LOGIN)) != null){
                loginUser(matcher);
            }

            else if((matcher = LoginMenuCommands.getMatcher(command, LoginMenuCommands.FORGET_PASSWORD)) != null){
                forgetPassword(matcher);
            }

            else if((matcher = LoginMenuCommands.getMatcher(command , LoginMenuCommands.ENTER_SIGNUP_MENU)) != null){
                enterSignupMenu();
            }
        }
    }

    public void loginUser(Matcher matcher){

    }

    public void forgetPassword(Matcher matcher){

    }

    public void enterSignupMenu(){

    }
}
