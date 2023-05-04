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

    public void run() {
        System.out.println("entered main menu successfully");
        Matcher matcher;
        String command;

        while (true) {
            command = scanner.nextLine();
            if ((matcher = MainMenuCommands.getMatcher(command, MainMenuCommands.ENTER_PROFILE_MENU)) != null)
                enterProfileMenu();

            else if ((matcher = MainMenuCommands.getMatcher(command, MainMenuCommands.LOGOUT)) != null) {
                if (logout())
                    return;
            }

            else if ((matcher = MainMenuCommands.getMatcher(command, MainMenuCommands.START_NEW_GAME)) != null)
                startNewGame(matcher);
            else System.out.println("Invalid Command");
        }
    }


    private void enterProfileMenu() {
        System.out.println("entered profile menu successfully");
        new ProfileMenu().run();
        System.out.println("entered main menu successfully");
    }

    private boolean logout() {
        System.out.println("Are you sure you want to logout?");
        String answer = scanner.nextLine().trim();
        if (answer.matches("yes")) {
            System.out.println("user logged out successfully");
            controller.logout();
            return true;
        }
        return false;
    }
    private void startNewGame(Matcher matcher) {
        if (Menu.checkBlankField(matcher.group("turnsNumber")) || Menu.checkBlankField(matcher.group("users"))) {
            System.out.println("Start new game failed : blank field");
            return;
        }
        int turnsNumber = Integer.parseInt(matcher.group("turnsNumber"));
        String users = matcher.group("users");
        switch (controller.startNewGame(users, turnsNumber)) {
            case SUCCESS:
                setHeadquarters();
                System.out.println("Entered new game");
                new GameMenu().run();
                break;
            case USERNAME_DOES_NOT_EXIST:
                System.out.println("Start new game failed : Username does not exist");
                break;
            case INVALID_NUMBER:
                System.out.println("Start new game failed : Invalid number");
                break;
        }
    }

    private void setHeadquarters() {

        for (int i = 0; i < controller.getNumberOfPlayers(); i++) {
        outer : while (true) {
                System.out.println(controller.getPlayerName(i) + " enter coordinate x and y for your headquarter");
                String input = scanner.nextLine();
                Matcher matcher;

                if ((matcher = MainMenuCommands.getMatcher(input, MainMenuCommands.SELECT_COORDINATES_HEADQUARTERS)) != null) {

                    if (Menu.checkBlankField(matcher.group("x")) || Menu.checkBlankField(matcher.group("y"))) {
                        System.out.println("Show details failed : Blanked field");
                        return;
                    }

                    int x = Integer.parseInt(matcher.group("x"));
                    int y = Integer.parseInt(matcher.group("y"));

                    switch (controller.setHeadquarters(i, x, y)) {
                        case X_OUT_OF_BOUNDS:
                            System.out.println("Set coordinate failed : Coordinate of x is out of bounds");
                            break;
                        case Y_OUT_OF_BOUNDS:
                            System.out.println("Set coordinate failed : Coordinate of y is out of bounds");
                            break;
                        case INAPPROPRIATE_TEXTURE:
                            System.out.println("Set coordinate failed : Inappropriate texture");
                            break;
                        case SUCCESS:
                            System.out.println("Successfully set");
                            break outer;
                    }
                } else System.out.println("Invalid Command");
            }
        }
    }
}
