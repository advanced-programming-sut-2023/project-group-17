package View;

import Controller.BuildingMenuController;

import java.util.Scanner;
import java.util.regex.Matcher;

public class BuildingMenu {

    BuildingMenuController controller;
    public BuildingMenu() {
        controller = new BuildingMenuController();
    }
    void run(Scanner scanner) {
        Matcher matcher;
        String command;
        while (true) {
            command = scanner.nextLine();
        }
    }

}
