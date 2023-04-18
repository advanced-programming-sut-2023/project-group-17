package View.Enums.Commands;

import Controller.LoginMenuController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LoginMenuCommands {
    USER_LOGIN(""),
    FORGET_PASSWORD(""),
    ENTER_SIGNUP_MENU("");

    final String regex;
    private LoginMenuCommands(String regex){
        this.regex = regex;
    }

    public Matcher getMatcher(String input , LoginMenuCommands regex){
        Matcher matcher = Pattern.compile(regex.regex).matcher(input);
        return matcher.matches() ? matcher : null;
    }
}
