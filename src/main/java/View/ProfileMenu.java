package View;

import Controller.ProfileMenuController;
import View.Enums.Commands.ProfileMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu extends Menu {

    private ProfileMenuController controller;

    public ProfileMenu() {
        controller = new ProfileMenuController();
    }

    @Override
    public void run(Scanner scanner) {
        Matcher matcher;
        String command;

        while(true) {
            command = scanner.nextLine();
            if((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.CHANGE_USERNAME)) != null)
                changeUsername(matcher);
            else if((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.CHANGE_NICKNAME)) != null)
                changeNickname(matcher);
            else if((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.CHANGE_PASSWORD)) != null)
                changePassword(matcher);
            else if((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.CHANGE_EMAIL)) != null)
                changeEmail(matcher);
            else if((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.CHANGE_SLOGAN)) != null)
                changeSlogan(matcher);
            else if((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.REMOVE_SLOGAN)) != null)
                removeSlogan(matcher);
            else if((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.DISPLAY_HIGH_SCORE)) != null)
                displayHighScore(matcher);
            else if((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.DISPLAY_PROFILE)) != null)
                displayProfile(matcher);
            else if((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.DISPLAY_SLOGAN)) != null)
                displaySlogan(matcher);
            else if((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.DISPLAY_RANK)) != null)
                displayRank(matcher);
            else System.out.println("invalid command");

        }

    }

    private void changeUsername(Matcher matcher) {

    }

    private void changeNickname(Matcher matcher) {

    }

    private void changePassword(Matcher matcher) {

    }

    private void changeEmail(Matcher matcher) {

    }

    private void changeSlogan(Matcher matcher) {

    }

    private void removeSlogan(Matcher matcher) {

    }

    private void displayHighScore(Matcher matcher) {

    }

    private void displayRank(Matcher matcher) {

    }

    private void displaySlogan(Matcher matcher) {

    }

    private void displayProfile(Matcher matcher) {

    }
}
