package View.Enums.Commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum TradeMenuCommands {
    TRADE_REQUEST("\\s*trade\\s+(?:(-t\\s*)(?<resourceType>\\S+)?(\\s*)()|(-a\\s*)(?<resourceAmount>\\d+)?(\\s*)()|" +
            "(-p\\s*)(?<price>\\d+)?(\\s*)()|(-m\\s*)(?<message>(\".+\")|(\\S+))?(\\s*)()){4}\\4\\8\\12\\18"),
    TRADE_LIST("\\s*trade\\s+list\\s*"),
    ACCEPT_TRADE("\\s*trade\\s+accept\\s+(?:(-i\\s*)(?<id>\\S+)?(\\s*)()|" +
            "(-m\\s*)(?<message>(\".+\")|(\\S+))?(\\s*)()){2}\\4\\10"),
    TRADE_HISTORY("\\s*trade\\s+history\\s*"),
    BACK("\\s*back\\s*");

    final String regex;
    private TradeMenuCommands(String regex) { this.regex = regex; }

    public static Matcher getMatcher(String input , TradeMenuCommands regex) {
        Matcher matcher = Pattern.compile(regex.regex).matcher(input);
        return matcher.matches() ? matcher : null;
    }
}
