import View.LoginMenu;

import java.util.Scanner;

import static View.Menu.scanner;

public class Main {
    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
        new LoginMenu().run(scanner);
    }
}