package View;

import Controller.ProfileMenuController;
import View.Enums.Commands.ProfileMenuCommands;
import java.util.Scanner;
import java.util.regex.Matcher;

import static View.Enums.Messages.ProfileMenuMessages.*;

public class ProfileMenu extends Menu {

    private final ProfileMenuController controller;

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
                removeSlogan();
            else if((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.DISPLAY_HIGH_SCORE)) != null)
                displayHighScore();
            else if((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.DISPLAY_PROFILE)) != null)
                displayProfile();
            else if((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.DISPLAY_SLOGAN)) != null)
                displaySlogan();
            else if((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.DISPLAY_RANK)) != null)
                displayRank();
            else if((matcher = ProfileMenuCommands.getMatcher(command, ProfileMenuCommands.BACK)) != null)
                return;
            else System.out.println("Invalid Command");

        }

    }

    private void changeUsername(Matcher matcher) {
        if(checkBlankField(matcher.group("username")))
            System.out.println("username change failed : blank field");

        String username = handleDoubleQuote(matcher.group("username"));

        switch (controller.changeUsername(username)) {
            case SUCCESS:
                System.out.println("username changed successfully");
                break;
            case INVALID_USERNAME:
                System.out.println("username change failed : invalid username format");
                break;
            case USERNAME_EXISTS:
                System.out.println("username change failed : username " + username + " already exists");
                break;
            case SAME_USERNAME:
                System.out.println("username change failed : your new username cannot be the same as your current username");
                break;
        }
    }

    private void changeNickname(Matcher matcher) {
        if(checkBlankField(matcher.group("nickname")))
            System.out.println("profile change failed : blank field");

        String nickname = handleDoubleQuote(matcher.group("nickname"));

        switch (controller.changeNickname(nickname)) {
            case SUCCESS:
                System.out.println("nickname changed successfully");
                break;
            case SAME_NICKNAME:
                System.out.println("nickname change failed : your new nickname cannot be the same as your current nickname");
                break;
        }
    }

    private void changePassword(Matcher matcher) {
        if(checkBlankField(matcher.group("oldPass")) || checkBlankField(matcher.group("newPass")))
            System.out.println("profile change failed : blank field");

        String oldPassword = handleDoubleQuote(matcher.group("oldPass"));
        String newPassword = handleDoubleQuote(matcher.group("newPass"));

        switch (controller.changePassword(oldPassword, newPassword)) {
            case SUCCESS:
                System.out.println("password changed successfully");
                break;
            case INCORRECT_PASSWORD:
                System.out.println("password change failed : current password is incorrect");
                break;
            case SAME_PASSWORD:
                System.out.println("password change failed : your new password cannot be the same as your current password");
                break;
            case SHORT_PASSWORD:
                System.out.println("password change failed : password must have at least 6 characters");
                break;
            case PASSWORD_DOES_NOT_CONTAIN_LOWERCASE:
                System.out.println("password change failed : password must have at least 1 lowercase character");
                break;
            case PASSWORD_DOES_NOT_CONTAIN_UPPERCASE:
                System.out.println("password change failed : password must have at least 1 uppercase character");
                break;
            case PASSWORD_DOES_NOT_CONTAIN_INTEGER:
                System.out.println("password change failed : password must have at least 1 integer");
                break;
            case PASSWORD_DOES_NOT_CONTAIN_SPECIFIC_CHARACTER:
                System.out.println("password change failed : password must have at least 1 special character");
                break;
            case PASSWORDS_DO_NOT_MATCH:
                System.out.println("password change failed : passwords do not match");
                break;
        }
    }

    private void changeEmail(Matcher matcher) {
        if(checkBlankField(matcher.group("email")))
            System.out.println("email change failed : blank field");

        String email = handleDoubleQuote(matcher.group("email"));

        switch (controller.changeEmail(email)) {
            case SUCCESS:
                System.out.println("email changed successfully");
                break;
            case INVALID_EMAIL:
                System.out.println("email change failed : invalid email format");
                break;
            case EMAIL_EXISTS:
                System.out.println("email change failed : email already exists");
                break;
            case SAME_EMAIL:
                System.out.println("email change failed : your new email cannot be the same as your current email");
                break;
        }

    }

    private void changeSlogan(Matcher matcher) {
        if(checkBlankField(matcher.group("slogan")))
            System.out.println("slogan change failed : blank field");

        String slogan = handleDoubleQuote(matcher.group("slogan"));

        switch (controller.changeSlogan(slogan)) {
            case SUCCESS:
                System.out.println("slogan changed successfully");
                break;
            case SAME_SLOGAN:
                System.out.println("slogan change failed : your new slogan cannot be the same as your current slogan");
                break;
        }
    }

    private void removeSlogan() {
        switch (controller.removeSlogan()) {
            case SUCCESS:
                System.out.println("slogan removed successfully");
                break;
            case EMPTY_SLOGAN:
                System.out.println("slogan change failed : there is no slogan to remove");
                break;
        }
    }

    private void displaySlogan() {
        System.out.println(controller.displaySlogan());
    }

    private void displayHighScore() {
        System.out.println(controller.displayHighScore());
    }

    private void displayRank() {
        System.out.println(controller.displayRank());
    }

    private void displayProfile() {
        System.out.print(controller.displayProfile());
    }
}
