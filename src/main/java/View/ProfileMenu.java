package View;

import Controller.ProfileMenuController;

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
