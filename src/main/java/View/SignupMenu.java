package View;

import Controller.SignupMenuCotroller;
import View.Enums.Commands.SignupMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class SignupMenu extends Menu{
    private SignupMenuCotroller controller;

    public SignupMenu() {
        controller = new SignupMenuCotroller();
    }

    @Override
    public void run(Scanner scanner) {
        Matcher matcher;
        String command;

        while(true) {
            command = scanner.nextLine();
            if((matcher = SignupMenuCommands.getMatcher(command, SignupMenuCommands.CREATE_USER)) != null)
                createUser(matcher);
            else if((matcher = SignupMenuCommands.getMatcher(command, SignupMenuCommands.PICK_QUESTION)) != null)
                pickQuestion(matcher);
            else if((matcher = SignupMenuCommands.getMatcher(command, SignupMenuCommands.ENTER_LOGIN_MENU)) != null)
                enterLoginMenu();
            else System.out.println("Invalid Command");
        }
    }

    private void enterLoginMenu() {

    }

    private void createUser(Matcher matcher) {

    }

    private void pickQuestion(Matcher matcher) {

    }

}
