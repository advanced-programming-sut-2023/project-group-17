package View.Enums.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum EmpireMenuCommands {
    SHOW_POPULARITY_FACTORS(""),
    SHOW_POPULARITY(""),
    SHOW_FOOD_LIST(""),
    SET_FOOD_RATE(""),
    SHOW_FOOD_RATE(""),
    SET_TAX_RATE(""),
    SHOW_TAX_RATE(""),
    SET_FEAR_RATE(""),
    SHOW_FEAR_RATE(""),
    BACK("");
   String regex;
   private EmpireMenuCommands(String regex) {
       this.regex = regex;
   }

    public static Matcher getMatcher(String input, EmpireMenuCommands regex) {
        Matcher matcher = Pattern.compile(regex.regex).matcher(input);
        return matcher.matches() ? matcher : null;
    }
}
