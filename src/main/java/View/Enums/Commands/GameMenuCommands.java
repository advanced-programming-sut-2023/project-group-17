package View.Enums.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameMenuCommands {
    CHOOSE_GAME_MAP("\\s*choose\\s+map\\s+(-i\\s*)(?<id>\\S+)?\\s*"),
    SHOW_MAP("\\s*show\\s+map\\s+(?:(-x\\s*)(?<x>\\d+)?(\\s*)()|(-y\\s*)(?<y>\\d+)?(\\s*)()){2}\\4\\8\\s*"),
    DEFINE_MAP_SIZE("\\s*define\\s+map\\s+size\\s+(?:(-w\\s*)(?<width>\\d+)?(\\s*)()|(-l\\s*)(?<length>\\d+)?(\\s*)()){2}\\4\\8\\s*"),
    ENTER_EMPIRE_MENU("\\s*enter\\s+empire\\s+menu\\s*"),
    ENTER_BUILDING_MENU("\\s*enter\\s+building\\s+menu\\s*"),
    ENTER_UNIT_MENU("\\s*enter\\s+unit\\s+menu\\s*"),
    ENTER_TRADE_MENU("\\s*enter\\s+trade\\s+menu\\s*"),
    ENTER_SHOP_MENU("\\s*enter\\s+shop\\s+menu\\s*"),
    ENTER_MAP_MENU("\\s*enter\\s+map\\s+menu\\s*"),
    NEXT_TURN("\\s*next\\s+turn\\s*"),
    SHOW_CURRENT_MENU("\\s*show\\s+current\\s+menu\\s*"),
    GET_MAP_ID("\\s*-i\\s+(?<id>\\d+)\\s*"),
    GET_WIDTH_AND_LENGTH("\\s*(?:(-w\\s*)(?<width>\\d+)?(\\s*)()|(-l\\s*)(?<length>\\d+)?(\\s*)()){2}\\4\\8\\s*");

    final String regex;
    private GameMenuCommands(String regex) { this.regex = regex; }

    public static Matcher getMatcher(String input , GameMenuCommands regex){
        Matcher matcher = Pattern.compile(regex.regex).matcher(input);
        return matcher.matches() ? matcher : null;
    }
}
