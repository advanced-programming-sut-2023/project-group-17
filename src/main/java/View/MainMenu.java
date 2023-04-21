package View;

import Controller.MainMenuController;
import Model.User;
import View.Enums.Commands.MainMenuCommands;

import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import View.Enums.Messages.MainMenuMessages;


public class MainMenu extends Menu {

    private MainMenuController controller;

    public MainMenu() {
        this.controller = new MainMenuController();
    }

    public void run(Scanner scanner) {
        Matcher matcher;
        String command;

        while (true) {
            command = scanner.nextLine();
            if ((matcher = MainMenuCommands.getMatcher(command, MainMenuCommands.ENTER_PROFILE_MENU)) != null)
                enterProfileMenu(scanner);
            else if ((matcher = MainMenuCommands.getMatcher(command, MainMenuCommands.LOGOUT)) != null)
                if(logout(scanner))
                    return;
            else if ((matcher = MainMenuCommands.getMatcher(command, MainMenuCommands.START_NEW_GAME)) != null)
                startNewGame(matcher);
            else System.out.println("Invalid Command");
        }
    }


    private void enterProfileMenu(Scanner scanner) {
        new ProfileMenu().run(scanner);
    }

    private boolean logout(Scanner scanner) {
        System.out.println("Are you sure you want to logout?");
        String answer = scanner.nextLine().trim();
        return answer.matches("yes");
    }
    private void startNewGame(Matcher matcher) {
        if (Menu.checkBlankField(matcher.group("turnsNumber")) || Menu.checkBlankField(matcher.group("users"))) {
            System.out.println("Start new game failed : blank field");
            return;
        }
        int turnsNumber = Integer.parseInt(matcher.group("turnsNumber"));
        String users = Menu.handleDoubleQuote(matcher.group("users"));
        switch (controller.startNewGame(users, turnsNumber)) {
            case USERNAME_DOES_NOT_EXIST:
                System.out.println("Start new game failed : Username does not exist");
                break;
            case INVALID_NUMBER:
                System.out.println("Start new game failed : Invalid number");
                break;
        }
    }
}
