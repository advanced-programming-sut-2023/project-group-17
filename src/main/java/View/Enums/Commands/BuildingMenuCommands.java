package View.Enums.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum BuildingMenuCommands {
    DROP_BUILDING("\\s*dropbuilding\\s+(?:(-x\\s*)(?<x>\\d+)?(\\s*)()|(-y\\s*)(?<y>\\d+)?(\\s*)()" +
            "|(-type\\s*)(?<type>\".+\"|\\S+)?(\\s*)()){3}\\4\\8\\12"),
    SELECT_BUILDING("\\s*select\\s+building\\s+(?:(-x\\s*)(?<x>\\d+)?(\\s*)()|" +
            "(-y\\s*)(?<y>\\d+)?(\\s*)()){2}\\4\\8"),
    CREATE_UNIT("\\s*createunit\\s+(?:(-t\\s*)(?<type>\".+\"|\\S+)?(\\s*)()|(-c\\s*)(?<count>\\d+)?(\\s*)()){2}\\4\\8"),
    REPAIR("\\s*repair\\s*"),
    BACK("\\s*back\\s*"),
    CREATE_ATTACK_TOOL("\\s*create\\s+attack\\s+tool\\s+(?:(-t\\s*)(?<type>\".+\"|\\S+)?(\\s*)()|" +
            "(-x\\s*)(?<x>\\d+)?(\\s*)()|(-y\\s*)(?<y>\\d+)?(\\s*)()){3}\\4\\8\\12"),
    DROP_WALL("\\s*drop\\s+wall\\s+(?:(-t\\s*)(?<thickness>\".+\"|\\S+)?(\\s*)()|" +
            "(-h\\s*)(?<height>\".+\"|\\S+)?(\\s*)()|(-x\\s*)(?<x>\\d+)?(\\s*)()|(-y\\s*)(?<y>\\d+)?(\\s*)())" +
            "{4}\\4\\8\\12\\16")
    ;
    final String regex;
    private BuildingMenuCommands(String regex) {this.regex = regex;}

    public static Matcher getMatcher(String input, BuildingMenuCommands regex) {
        Matcher matcher = Pattern.compile(regex.regex).matcher(input);
        return matcher.matches() ? matcher : null;
    }
}
