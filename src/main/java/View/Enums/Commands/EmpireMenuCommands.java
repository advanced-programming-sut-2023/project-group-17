package View.Enums.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum EmpireMenuCommands {
    SHOW_POPULARITY_FACTORS("\\s*show\\s+popularity\\s+factors\\s*"),
    SHOW_POPULARITY("\\s*show\\s+popularity\\s*"),
    SHOW_FOOD_LIST("\\s*show\\s+food\\s+list\\s*"),
    SET_FOOD_RATE("\\s*food\\s+rate\\s+-r\\s+(?<rateNumber>\\d)\\s*"),
    SHOW_FOOD_RATE("\\s*food\\s+rate\\s+show\\s*"),
    SET_TAX_RATE("\\s*tax\\s+rate\\s+-r\\s+(?<rateNumber>\\d)\\s*"),
    SHOW_TAX_RATE("\\s*tax\\s+rate\\s+show\\s*"),
    SET_FEAR_RATE("\\s*fear\\s+rate\\s+-r\\s+(?<rateNumber>\\d)\\s*"),
    SHOW_FEAR_RATE("\\s*fear\\s+rate\\s+show\\s*"),
    BACK("\\s*back\\s*");
   String regex;
   private EmpireMenuCommands(String regex) {
       this.regex = regex;
   }

    public static Matcher getMatcher(String input, EmpireMenuCommands regex) {
        Matcher matcher = Pattern.compile(regex.regex).matcher(input);
        return matcher.matches() ? matcher : null;
    }
}
