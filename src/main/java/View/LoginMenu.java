package View;

import Controller.LoginMenuController;
import View.Enums.Commands.LoginMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu extends Menu {
    private LoginMenuController controller;
    private Scanner scanner;

    public LoginMenu(){
         this.controller = new LoginMenuController();
    }

    public void run(Scanner scanner){
        this.scanner = scanner;
        String command;
        Matcher matcher = null;

        while (true){
            command = scanner.nextLine();
            if((matcher = LoginMenuCommands.getMatcher(command, LoginMenuCommands.USER_LOGIN)) != null)
                loginUser(matcher);
            else if((matcher = LoginMenuCommands.getMatcher(command, LoginMenuCommands.FORGET_PASSWORD)) != null)
                forgetPassword(scanner , matcher);
            else if((matcher = LoginMenuCommands.getMatcher(command, LoginMenuCommands.ENTER_SIGNUP_MENU)) != null)
                enterSignupMenu();
            else System.out.println("Invalid Command");
        }
    }

    private void loginUser(Matcher matcher) {
        if(checkBlankField(matcher.group("username")) || checkBlankField(matcher.group("password"))) {
            System.out.println("error : blank field");
            return;
        }

        String username = handleDoubleQuote(matcher.group("username"));
        String password = handleDoubleQuote(matcher.group("password"));
        boolean stayLoggedIn = matcher.group("stayLoggedIn") != null;

        switch (controller.loginUser(username , password , stayLoggedIn)){
            case SUCCESS:
                System.out.println("user " + username + " logged in successfully");
                break;
            case WRONG_PASSWORD:
                System.out.println("error : password is not correct");
                break;
            case USERNAME_DOES_NOT_EXISTS:
                System.out.println("error : username does not exist");
                break;
        }
    }

    private void forgetPassword(Scanner scanner , Matcher matcher) {
        if(checkBlankField(matcher.group("username"))) {
            System.out.println("error : blank field");
            return;
        }

        String username = handleDoubleQuote(matcher.group("username"));

        switch (controller.forgetPassword(this , username)) {
            case WRONG_PASSWORD_RECOVERY_ANSWER:
                System.out.println("error : your answer is not correct");
                break;
            case USERNAME_DOES_NOT_EXISTS:
                System.out.println("error : username does not exist");
                break;
            case SUCCESS:
                System.out.println("password changed successfully");
                break;
        }
    }

    private void enterSignupMenu() {
        System.out.println("entered signup menu");
        new SignupMenu().run(scanner);
    }

    public void print(String string){
        System.out.println(string);
    }

    public String scan(){
        return scanner.nextLine();
    }
}
