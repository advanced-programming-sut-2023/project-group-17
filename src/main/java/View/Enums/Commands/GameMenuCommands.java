package View.Enums.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameMenuCommands {
    CHOOSE_GAME_MAP(""),
    SHOW_MAP(""),
    DEFINE_MAP_SIZE(""),
    ENTER_EMPIRE_MENU(""),
    ENTER_BUILDING_MENU(""),
    ENTER_UNIT_MENU(""),
    ENTER_TRADE_MENU(""),
    ENTER_SHOP_MENU("");

    final String regex;
    private GameMenuCommands(String regex) { this.regex = regex; }

    public static Matcher getMatcher(String input , GameMenuCommands regex){
        Matcher matcher = Pattern.compile(regex.regex).matcher(input);
        return matcher.matches() ? matcher : null;
    }
}
