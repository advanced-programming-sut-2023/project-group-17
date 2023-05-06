package View;

import java.util.Scanner;

public abstract class Menu {
    public static Scanner scanner = new Scanner(System.in);
    abstract void run() throws InterruptedException;

    public static String handleDoubleQuote(String matcherGroup) {
        if(matcherGroup == null)
            return null;
        return (matcherGroup.startsWith("\"") && matcherGroup.endsWith("\"")) ?
                matcherGroup.substring(1 , matcherGroup.length() - 1) : matcherGroup;
    }

    public static boolean checkBlankField(String matcherGroup) {
        return matcherGroup==null;
    }

}
