package Client.enums.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ShopMenuCommands {
    SHOW_PRICE_LIST("\\s*show\\s+price\\s+list\\s*"),
    BUY_ITEM("\\s*buy\\s+(?:(-i\\s*)(?<name>\".+\"|\\S+)?(\\s*)()|(-a\\s*)(?<amount>\\d+)?(\\s*)()){2}\\4\\8"),
    SELL_ITEM("\\s*sell\\s+(?:(-i\\s*)(?<name>\".+\"|\\S+)?(\\s*)()|(-a\\s*)(?<amount>\\d+)?(\\s*)()){2}\\4\\8"),
    BACK("\\s*back\\s*"),
    SHOW_CURRENT_MENU("\\s*show\\s+current\\s+menu\\s*")

    ;

    final String regex;
    private ShopMenuCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, ShopMenuCommands regex) {
        Matcher matcher = Pattern.compile(regex.regex).matcher(input);
        if(matcher.matches())
            return matcher;
        return null;
    }

}
