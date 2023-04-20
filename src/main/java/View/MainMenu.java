package View;

import Controller.MainMenuController;
import Model.User;
import View.Enums.Commands.MainMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

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
            else System.out.println("invalid command");
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
            System.out.println("error : blank field");
            return;
        }
        int turnsNumber = Integer.parseInt(matcher.group("turnsNumber"));
        String users = Menu.handleDoubleQuote(matcher.group("users"));
        System.out.println(controller.startNewGame(users, turnsNumber));
    }
}
