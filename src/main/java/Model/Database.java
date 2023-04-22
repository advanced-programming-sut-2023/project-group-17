package Model;

import Model.Items.ArmorAndWeapon;

import java.util.ArrayList;

public class Database {
    private static final ArrayList<User> users = new ArrayList<>();
    private static Map currentMapGame;
//    private static final ArrayList<String> recoveryQuestions = new ArrayList<>();
    private static final ArrayList<User> usersInTheGame = new ArrayList<>();
    private static final ArrayList<Map> allMaps = new ArrayList<>();
    private static User loggedInUser = null;
    private static int turnsPassed = 0;
    private static int totalTurns = 0;

    static final String[] recoveryQuestions = {
        "1. What is my father's name?",
        "2. What was my first pet's name?",
        "3. What is my mother's last name?",
    };

    //todo: static class to add default maps

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
        return recoveryQuestions[questionNumber - 1];
    }

    public static Map getCurrentMapGame() {
        return currentMapGame;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
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

    public static ArrayList<Map> getAllMaps() {
        return allMaps;
    }

    public static void addMap(Map map) {
        allMaps.add(map);
    }

    public static void setCurrentMapGame(Map map) {
        currentMapGame = map;
    }
}
