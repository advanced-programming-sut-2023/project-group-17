package View.Enums.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MapMenuCommands {
    SHOW_MAP(""),
    MOVE_MAP("\\s*map\\s+(?:(?<up>(-up\\s*)(?<moveUp>\\d)?(\\s*))?()|(?<down>(-down\\s*)(?<moveDown>\\d)?(\\s*))?()|" +
            "(?<right>(-right\\s*)(?<moveRight>\\d)?(\\s*))?()|(?<left>(-left\\s*)(?<moveLeft>\\d)?(\\s*))?()){1,}\\5\\10\\15\\20"),
    SHOW_DETAILS("\\s*show\\s+details\\s+(?:(-x\\s*)(?<x>\\d)?(\\s*)()|(-y\\s*)(?<y>\\d)?(\\s*)()){2}\\4\\8"),
    SET_TEXTURE_ONE_BLOCK("\\s*settexture\\s+(?:(-x\\s*)(?<x>\\d)?(\\s*)()|(-y\\s*)(?<y>\\d)?(\\s*)()|" +
            "(-type\\s*)(?<type>\\S+)?(\\s*)()){3}\\4\\8\\12"),
    SET_TEXTURE_MULTIPLE_BLOCKS("\\s*settexture\\s+(?:(-x1\\s*)(?<x1>\\d)?(\\s*)()|(-y1\\s*)(?<y1>\\d)?(\\s*)()|" +
            "(-x2\\s*)(?<x2>\\d)?(\\s*)()|(-y2\\s*)(?<y2>\\d)?(\\s*)()|(-type\\s*)(?<type>\\S+)?(\\s*)()){5}\\4\\8\\12\\16\\20"),
    CLEAR_BLOCK("\\s*clear\\s+block\\s+(?:(-x\\s*)(?<x>\\d)?(\\s*)()|(-y\\s*)(?<y>\\d)?(\\s*)()){2}\\4\\8"),
    DROP_ROCK("\\s*droprock\\s+(?:(-x\\s*)(?<x>\\d)?(\\s*)()|(-y\\s*)(?<y>\\d)?(\\s*)()|" +
            "(-direction\\s*)(\\S+)?(\\s*)()){3}\\4\\8\\12"),
    DROP_TREE("\\s*droptree\\s+(?:(-x\\s*)(?<x>\\d)?(\\s*)()|(-y\\s*)(?<y>\\d)?(\\s*)()|" +
            "(-type\\s*)(\\S+)?(\\s*)()){3}\\4\\8\\12"),
    DROP_BUILDING("\\s*dropbuilding\\s+(?:(-x\\s*)(?<x>\\d)?(\\s*)()|(-y\\s*)(?<y>\\d)?(\\s*)()|" +
            "(-type\\s*)(\\S+)?(\\s*)()){3}\\4\\8\\12"),
    DROP_UNIT("\\s*dropunit\\s+(?:(-x\\s*)(?<x>\\d)?(\\s*)()|(-y\\s*)(?<y>\\d)?(\\s*)()|" +
            "(-type\\s*)(\\S+)?(\\s*)()|(-c\\s*)(?<count>\\d)(\\s*)()){4}\\4\\8\\12\\16"),
    EXIT("\\s*exit\\s*");
    final String regex;
    private MapMenuCommands(String regex) {
        this.regex = regex;
    }
    public static Matcher getMatcher(String input, MapMenuCommands regex) {
        Matcher matcher = Pattern.compile(regex.regex).matcher(input);
        return matcher.matches() ? matcher : null;
    }
}
