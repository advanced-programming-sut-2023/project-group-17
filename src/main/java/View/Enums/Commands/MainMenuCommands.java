package View.Enums.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MainMenuCommands {
    LOGOUT("\\s*logout\\s*"),
    ENTER_PROFILE_MENU("\\s*enter\\s+profile\\s+menu\\s*"),
    START_NEW_GAME("\\s*start\\s+new\\s+game\\s+-t\\s+(?<turnsNumber>\\d)\\s+-u\\s+(?<users>.+)")
    ;
    String regex;
    private MainMenuCommands(String regex) {this.regex = regex;}

    public static Matcher getMatcher(String input, MainMenuCommands regex) {
        Matcher matcher = Pattern.compile(regex.regex).matcher(input);
        return matcher.matches() ? matcher : null;
    }
}
