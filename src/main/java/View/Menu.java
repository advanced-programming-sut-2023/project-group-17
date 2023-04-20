package View;

import java.util.Scanner;

public abstract class Menu {
    abstract void run(Scanner scanner);

    public static String handleDoubleQuote(String matcherGroup) {
        if(matcherGroup == null)
            return null;
        return (matcherGroup.startsWith("\"") && matcherGroup.endsWith("\"")) ?
                matcherGroup.substring(1 , matcherGroup.length() - 1) : matcherGroup;
    }

    public static boolean checkBlankField(String matcherGroup) {
        return matcherGroup==null;
    }

    public static void print(String string){
        System.out.println(string);
    }
}
