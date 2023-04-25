package View.Enums.Commands;

import View.SignupMenu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum SignupMenuCommands {
    CREATE_USER("\\s*user\\s+create\\s+(?:(-u\\s*)(?<username>\".+\"|\\S+)?(\\s*)()|" +
                    "(-p\\s*)(?<password>\".+\"|\\S+)?(\\s*)((-c\\s*)(?<confirmation>\".+\"|\\S+)?(\\s*))?()|" +
                    "(-e\\s*)(?<email>\".+\"|\\S+)?(\\s*)()|(-n\\s*)(?<nickname>\".+\"|\\S+)?(\\s*)()|" +
                    "(?<sloganDash>-s\\s*)(?<slogan>\".+\"|\\S+)?(\\s*)()){4,}\\4\\12\\16\\20"),
    PICK_QUESTION("\\s*question\\s+pick\\s+(?:(-q\\s*)(?<questionNumber>\\d+)?(\\s*)()|" +
                    "(-a\\s*)(?<answer>\".+\"|\\S+)?(\\s*)()|" +
                    "(-c\\s*)(?<confirmation>\".+\"|\\S+)?(\\s*)()){3}\\4\\8\\12"),
    ENTER_LOGIN_MENU("\\s*enter\\s+login\\s+menu\\s*")
    ;

    final String regex;

    private SignupMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, SignupMenuCommands regex) {
        Matcher matcher = Pattern.compile(regex.regex).matcher(input);
        if(matcher.matches())
            return matcher;
        return null;
    }


}
