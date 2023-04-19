package View.Enums.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum BuildingMenuCommands {
    DROP_BUILDING(""),
    SELECT_BUILDING(""),
    CREATE_UNIT(""),
    REPAIR(""),
    BACK("")
    ;
    String regex;
    private BuildingMenuCommands(String regex) {this.regex = regex;}

    public static Matcher getMatcher(String input, BuildingMenuCommands regex) {
        Matcher matcher = Pattern.compile(regex.regex).matcher(input);
        return matcher.matches() ? matcher : null;
    }
}
