package View.Enums.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum UnitMenuCommands {
    SELECT_UNIT("\\s*select\\s+unit\\s+(?:(-x\\s*)(?<x>\\d+)?(\\s*)()|(-y\\s*)(?<y>\\d+)?(\\s*)()){2}\\4\\8"),
    MOVE_UNIT_TO("\\s*move\\s+unit\\s+to\\s+(?:(-x\\s*)(?<x>\\d+)?(\\s*)()|(-y\\s*)(?<y>\\d+)?(\\s*)()){2}\\4\\8"),
    PATROL_UNIT("\\s*patrol\\s+unit\\s+(?:(-x1\\s*)(?<x1>\\d+)?(\\s*)()|(-y1\\s*)(?<y1>\\d+)?(\\s*)()|" +
                    "(-x2\\s*)(?<x2>\\d+)?(\\s*)()|(-y2\\s*)(?<y2>\\d+)?(\\s*)()){4}\\4\\8\\12\\16"),
    SET_UNIT_MODE("\\s*set\\s+(?:(-x\\s*)(?<x>\\d+)?(\\s*)()|(-y\\s*)(?<y>\\d+)?(\\s*)()|" +
                    "(-s\\s*)(?<status>standing|defensive|offensive)?(\\s*)()){3}\\4\\8\\12"),
    ATTACK_ENEMY("\\s*attack\\s+-e\\s*((?<x>\\d+)\\s+(?<y>\\d+))?\\s*"),
    AIR_ATTACK("\\s*attack\\s+(?:(-x\\s*)(?<x>\\d+)?(\\s*)()|(-y\\s*)(?<y>\\d+)?(\\s*)()){2}\\4\\8"),
    POUR_OIL("\\s*pour\\s+oil\\s+-d\\s*(?<direction>\\S+)?\\s*"),
    DIG_TUNNEL("\\s*dig\\s+tunnel\\s+(?:(-x\\s*)(?<x>\\d+)?(\\s*)()|(-y\\s*)(?<y>\\d+)?(\\s*)()){2}\\4\\8"),
    BUILD_SURROUNDING_EQUIPMENT("\\s*build\\s+(?:(-q\\s*)(?<equipment>\".+\"|\\S+)?(\\s*)()|" +
            "(-x\\s*)(?<x>\\d+)?(\\s*)()|(-y\\s*)(?<y>\\d+)?(\\s*)()){3}\\4\\8\\12"),
    DISBAND_UNIT("\\s*disband\\s+unit\\s*"),
    DIG_MOAT("\\s*dig\\s+moat\\s+(?:(-x\\s*)(?<x>\\d+)?(\\s*)()|(-y\\s*)(?<y>\\d+)?(\\s*)()){2}\\4\\8"),
    BURN_OIL("\\s*burn\\s+oil\\s*"),
    FILL_MOAT("\\s*fill\\s+moat\\s+(?:(-x\\s*)(?<x>\\d+)?(\\s*)()|(-y\\s*)(?<y>\\d+)?(\\s*)()){2}\\4\\8"),
    BACK("\\s*back\\s*"),
    SHOW_CURRENT_MENU("\\s*show\\s+current\\s+menu\\s*")
    ;

    String regex;
    private UnitMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, UnitMenuCommands regex) {
        Matcher matcher = Pattern.compile(regex.regex).matcher(input);
        if (matcher.matches())
            return matcher;
        return null;
    }

}
