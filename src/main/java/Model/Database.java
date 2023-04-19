package Model;

import Model.Items.ArmorAndWeapon;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class Database {
    private static final ArrayList<User> users = new ArrayList<>();
    private static final ArrayList<MapCell> currentMapGame = new ArrayList<>();
    private static User loggedInUser = null;
    private static int turnsPassed = 0;
    private static int totalTurns = 0;

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static ArrayList<MapCell> getCurrentMapGame() {
        return currentMapGame;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static MapCell getMapCellByCoordinates(int x, int y) {
        for (MapCell mapCell : currentMapGame) {
            if(mapCell.getX == x && mapCell.getY == y) return mapCell;
        }
        return null;
    }

    public static int getTurnsPassed() {
        return turnsPassed;
    }

    public static int getTotalTurns() {
        return totalTurns;
    }

    public static void addUser(User user){
        users.add(user);
    }

    public static void setLoggedInUser(User loggedInUser) {
        Database.loggedInUser = loggedInUser;
    }

    public static void setTotalTurns(int totalTurns) {
        turnsPassed = 0;
        Database.totalTurns = totalTurns;
    }

    public static void increaseTurnsPassed(){
        turnsPassed++;
    }
}
