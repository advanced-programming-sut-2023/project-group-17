package View.Enums.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MapMenuCommands {
    SHOW_MAP(""),
    MOVE_MAP(""),
    SHOW_DETAILS(""),
    SET_TEXTURE(""),
    CLEAR_BLOCK(""),
    DROP_ROCK(""),
    DROP_TREE(""),
    DROP_BUILDING(""),
    DROP_UNIT(""),
    EXIT("");
    String regex;
    private MapMenuCommands(String regex) {
        this.regex = regex;
    }
    public static Matcher getMatcher(String input, MapMenuCommands regex) {
        Matcher matcher = Pattern.compile(regex.regex).matcher(input);
        return matcher.matches() ? matcher : null;
    }
}
