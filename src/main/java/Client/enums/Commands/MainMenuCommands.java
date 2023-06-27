package Client.enums.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MainMenuCommands {
    LOGOUT("\\s*logout\\s*"),
    ENTER_PROFILE_MENU("\\s*enter\\s+profile\\s+menu\\s*"),
    START_NEW_GAME("\\s*start\\s+new\\s+game\\s+(?:(-t\\s+)(?<turnsNumber>\\d+)?(\\s*)()" +
            "|(-u\\s+)(?<users>.+)?(\\s*)()){2}\\4\\8"),
    SELECT_COORDINATES_HEADQUARTERS("\\s*(?:(-x\\s*)(?<x>\\d+)?(\\s*)()|(-y\\s*)(?<y>\\d+)?(\\s*)()){2}\\4\\8"),
    SHOW_CURRENT_MENU("\\s*show\\s+current\\s+menu\\s*")

    ;
    final String regex;
    private MainMenuCommands(String regex) {this.regex = regex;}

    public static Matcher getMatcher(String input, MainMenuCommands regex) {
        Matcher matcher = Pattern.compile(regex.regex).matcher(input);
        return matcher.matches() ? matcher : null;
    }
}
