package Server.enums.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ProfileMenuCommands {
    CHANGE_USERNAME("\\s*profile\\s+change\\s+-u\\s*(?<username>\".+\"|\\S+)?\\s*"),
    CHANGE_NICKNAME("\\s*profile\\s+change\\s+-n\\s*(?<nickname>\".+\"|\\S+)?\\s*"),
    CHANGE_PASSWORD("\\s*profile\\s+change\\s+password\\s+(?:(-o\\s*)(?<oldPass>\".+\"|\\S+)?(\\s*)()|(-n\\s*)(?<newPass>\".+\"|\\S+)?(\\s*)()){2}\\4\\8"),
    CHANGE_EMAIL("\\s*profile\\s+change\\s+-e\\s*(?<email>\".+\"|\\S+)?\\s*"),
    CHANGE_SLOGAN("\\s*profile\\s+change\\s+slogan\\s+-s\\s*(?<slogan>\".+\"|\\S+)?\\s*"),
    REMOVE_SLOGAN("\\s*profile\\s+remove\\s+slogan\\s*"),
    DISPLAY_HIGH_SCORE("\\s*profile\\s+display\\s+highscore\\s*"),
    DISPLAY_RANK("\\s*profile\\s+display\\s+rank\\s*"),
    DISPLAY_SLOGAN("\\s*profile\\s+display\\s+slogan\\s*"),
    DISPLAY_PROFILE("\\s*profile\\s+display\\s*"),
    BACK("\\s*back\\s*"),
    SHOW_CURRENT_MENU("\\s*show\\s+current\\s+menu\\s*")

    ;

    final String regex;

    private ProfileMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, ProfileMenuCommands regex) {
        Matcher matcher = Pattern.compile(regex.regex).matcher(input);
        if(matcher.matches())
            return matcher;
        return null;
    }

}
