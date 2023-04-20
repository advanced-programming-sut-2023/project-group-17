package View.Enums.Commands;

import Controller.LoginMenuController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LoginMenuCommands {
    USER_LOGIN("\\s*user\\s+login\\s+(?:(-u\\s*)(?<username>(\".+\")|(\\S+))?(\\s*)()" +
            "|(-p\\s*)(?<password>(\".+\")|(\\S+))?(\\s*)()){2}\\6\\12(?<stayLoggedIn>--stay-logged-in)?\\s*"),
    FORGET_PASSWORD("\\s*forgot\\s+my\\s+password\\s+(-u\\s*(?<username>(\".+\")|(\\S+))?)\\s*"),
    ENTER_SIGNUP_MENU("\\s*enter\\s+signup\\s+menu\\s*");

    final String regex;
    private LoginMenuCommands(String regex) { this.regex = regex; }

    public static Matcher getMatcher(String input , LoginMenuCommands regex){
        Matcher matcher = Pattern.compile(regex.regex).matcher(input);
        return matcher.matches() ? matcher : null;
    }
}
