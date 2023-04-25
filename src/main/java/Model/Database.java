package Model;

import Model.Buildings.Building;
import Model.Buildings.BuildingType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static ArrayList<User> users = new ArrayList<>();
    private static ArrayList<Building> buildings = new ArrayList<>();
    private static Map currentMapGame;
//    private static final ArrayList<String> recoveryQuestions = new ArrayList<>();
    private static final ArrayList<User> usersInTheGame = new ArrayList<>();
    private static ArrayList<Map> allMaps = new ArrayList<>();
    private static User loggedInUser = null;
    private static int turnsPassed = 0;
    private static int totalTurns = 0;

    public static final String[] recoveryQuestions = {
        "What is my father's name?",
        "What was my first pet's name?",
        "What is my mother's last name?",
    };

    //todo: static class to add default maps

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static ArrayList<User> getUsersInTheGame(){
        return usersInTheGame;
    }

    public static User getUserInTheGameByUsername(String username){
        for (User user : getUsersInTheGame()) {
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
        saveUsers();
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

    public static void setUsers(ArrayList<User> users) {
        Database.users = users;
    }

    public static void setAllMaps(ArrayList<Map> allMaps) {
        Database.allMaps = allMaps;
    }

    public static ArrayList<Building> getBuildings() {
        return buildings;
    }

    public static void setBuildings(ArrayList<Building> buildings) {
        Database.buildings = buildings;
    }
    public static Building getBuildingDataByName(String name) {
        for (Building building : buildings)
            if (building.getBuildingName().equals(name)) return building;
        return null;
    }
    public static void saveUsers() {
        try {
            FileWriter fileWriter = new FileWriter("./src/main/resources/UserDatabase.json");
            String gson = new Gson().toJson(getUsers());
            fileWriter.write(gson);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void loadUsers() {
        try {
            String json = new String(Files.readAllBytes(Paths.get("./src/main/resources/UserDatabase.json")));
            ArrayList<User> savedUsers;
            savedUsers = new Gson().fromJson(json, new TypeToken<List<User>>() {}.getType());
            if (savedUsers != null) setUsers(savedUsers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void loadBuildings() {
        try {
            String json = new String(Files.readAllBytes(Paths.get("./src/main/resources/BuildingDatabase.json")));
            ArrayList<Building> savedBuildings;
            savedBuildings = new Gson().fromJson(json, new TypeToken<List<Building>>() {}.getType());
            if (savedBuildings != null) setBuildings(savedBuildings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
