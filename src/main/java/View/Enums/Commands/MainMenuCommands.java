package View.Enums.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MainMenuCommands {
    LOGOUT(""),
    ENTER_PROFILE_MENU(""),
    START_NEW_GAME("")
    ;
    String regex;
    private MainMenuCommands(String regex) {this.regex = regex;}

    public static Matcher getMatcher(String input, MainMenuCommands regex) {
        Matcher matcher = Pattern.compile(regex.regex).matcher(input);
        return matcher.matches() ? matcher : null;
    }
}
