package View.Enums.Commands;

import Controller.ProfileMenuController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ProfileMenuCommands {
    CHANGE_USERNAME(""),
    CHANGE_NICKNAME(""),
    CHANGE_PASSWORD(""),
    CHANGE_EMAIL(""),
    CHANGE_SLOGAN(""),
    REMOVE_SLOGAN(""),
    DISPLAY_HIGH_SCORE(""),
    DISPLAY_RANK(""),
    DISPLAY_SLOGAN(""),
    DISPLAY_PROFILE(""),
    BACK("")
    ;

    String regex;

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
