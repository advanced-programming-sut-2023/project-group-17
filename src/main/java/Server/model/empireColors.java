package Server.model;

import java.util.ArrayList;
import java.util.Arrays;

public enum empireColors {
    RED,
    BLUE,
    GREEN,
    YELLOW,
    BLACK,
    WHITE,
    PURPLE,
    BROWN;
    private static ArrayList<empireColors> colors = new ArrayList<>();

    public static ArrayList<empireColors> getColors() {
        return colors;
    }

    public static void addColors() {
        colors.addAll(Arrays.asList(empireColors.values()));
    }
}
