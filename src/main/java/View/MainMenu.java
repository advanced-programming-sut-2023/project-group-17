package View;

import Controller.MainMenuController;
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
                enterProfileMenu();
            else if ((matcher = MainMenuCommands.getMatcher(command, MainMenuCommands.LOGOUT)) != null)
                logout();
            else if ((matcher = MainMenuCommands.getMatcher(command, MainMenuCommands.START_NEW_GAME)) != null)
                startNewGame(matcher);
            else System.out.println("invalid command");
        }
    }


    private void enterProfileMenu() {
    }

    private void logout() {
    }
    private void startNewGame(Matcher matcher) {
    }
}
