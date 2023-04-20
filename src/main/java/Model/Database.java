package Model;

import Model.Items.ArmorAndWeapon;

import java.util.ArrayList;

public class Database {
    private static final ArrayList<User> users = new ArrayList<>();
    private static final ArrayList<MapCell> currentMapGame = new ArrayList<>();
    private static final ArrayList<String> recoveryQuestions = new ArrayList<>();
    private static final ArrayList<User> usersInTheGame = new ArrayList<>();
    private static User loggedInUser = null;
    private static int turnsPassed = 0;
    private static int totalTurns = 0;

    static {
        recoveryQuestions.add("1. What is my father's name?");
        recoveryQuestions.add("2. What was my first pet's name?");
        recoveryQuestions.add("3. What is my mother's last name?");
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static ArrayList<User> getUesrsInTheGame(){
        return usersInTheGame;
    }

    public static User getUserInTheGameByUsername(String username){
        for (User user : getUesrsInTheGame()) {
            if(user.getUsername().equals(username))
                return user;
        }
        return null;
    }

    public static User getUserByUsername(String username) {
        for (User user : users) {
            if(user.getUsername().equals(username))
                return user;
        }
        return null;
    }

    public static boolean emailExists(String email) {
        for (User user : users) {
            if(user.getEmail().equals(email))
                return true;
        }
        return false;
    }

    public static String getQuestionByNumber(Integer questionNumber) {
        return recoveryQuestions.get(questionNumber - 1);
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
