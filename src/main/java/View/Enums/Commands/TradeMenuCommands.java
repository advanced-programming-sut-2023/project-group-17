package View.Enums.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum TradeMenuCommands {
    TRADE_REQUEST(""),
    TRADE_LIST(""),
    ACCEPT_TRADE(""),
    TRADE_HISTORY(""),
    BACK("");

    final String regex;
    private TradeMenuCommands(String regex) { this.regex = regex; }

    public static Matcher getMatcher(String input , TradeMenuCommands regex) {
        Matcher matcher = Pattern.compile(regex.regex).matcher(input);
        return matcher.matches() ? matcher : null;
    }
}
