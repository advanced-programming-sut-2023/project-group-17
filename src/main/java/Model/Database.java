package Model;

import Model.Buildings.*;
import Model.People.Soldier;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class Database {
    private static ArrayList<String> mapId = new ArrayList<>();
    private static ArrayList<User> users = new ArrayList<>();
    private static ArrayList<Building> buildings = new ArrayList<>();
    private static ArrayList<DefensiveBuilding> defensiveBuildings = new ArrayList<>();
    private static ArrayList<GateHouse> gateHouses = new ArrayList<>();
    private static ArrayList<MiningBuilding> miningBuildings = new ArrayList<>();
    private static ArrayList<OtherBuilding> otherBuildings = new ArrayList<>();
    private static ArrayList<ProductionBuilding> productionBuildings = new ArrayList<>();
    private static ArrayList<SoldierProduction> soldierProductions = new ArrayList<>();
    private static ArrayList<StorageBuilding> storageBuildings = new ArrayList<>();
    private static ArrayList<AttackToolsAndMethods> attackToolsAndMethods = new ArrayList<>();
    private static Map currentMapGame;
    private static ArrayList<Soldier> allSoldiers = new ArrayList<>();
    private static final ArrayList<User> usersInTheGame = new ArrayList<>();
    private static ArrayList<Map> allMaps = new ArrayList<>();
    private static User currentUser = null;
    private static int turnsPassed = 0;
    private static int totalTurns = 0;
    private static ArrayList<empireColors> empireColors;
    private static User loggedInUser;
    public static final String[] recoveryQuestions = {
        "What is my father's name?",
        "What was my first pet's name?",
        "What is my mother's last name?",
    };


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

    public static User getCurrentUser() {
        return currentUser;
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

    public static void setCurrentUser(User currentUser) {
        Database.currentUser = currentUser;
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

    public static ArrayList<Soldier> getAllSoldiers() {
        return allSoldiers;
    }

    public static void setAllSoldiers(ArrayList<Soldier> allSoldiers) {
        Database.allSoldiers = allSoldiers;
    }

    public static Soldier getSoldierDataByName(String name) {
        for (Soldier soldier : allSoldiers)
            if (soldier.getName().equals(name)) return soldier;
        return null;
    }

    public static ArrayList<Model.empireColors> getEmpireColors() {
        return empireColors;
    }

    public static void setEmpireColors(ArrayList<Model.empireColors> empireColors) {
        Database.empireColors = empireColors;
    }

    public static ArrayList<DefensiveBuilding> getDefensiveBuildings() {
        return defensiveBuildings;
    }

    public static void setDefensiveBuildings(ArrayList<DefensiveBuilding> defensiveBuildings) {
        Database.defensiveBuildings = defensiveBuildings;
    }

    public static ArrayList<GateHouse> getGateHouses() {
        return gateHouses;
    }

    public static void setGateHouses(ArrayList<GateHouse> gateHouses) {
        Database.gateHouses = gateHouses;
    }

    public static ArrayList<MiningBuilding> getMiningBuildings() {
        return miningBuildings;
    }

    public static void setMiningBuildings(ArrayList<MiningBuilding> miningBuildings) {
        Database.miningBuildings = miningBuildings;
    }

    public static ArrayList<OtherBuilding> getOtherBuildings() {
        return otherBuildings;
    }

    public static void setOtherBuildings(ArrayList<OtherBuilding> otherBuildings) {
        Database.otherBuildings = otherBuildings;
    }

    public static ArrayList<ProductionBuilding> getProductionBuildings() {
        return productionBuildings;
    }

    public static void setProductionBuildings(ArrayList<ProductionBuilding> productionBuildings) {
        Database.productionBuildings = productionBuildings;
    }

    public static ArrayList<SoldierProduction> getSoldierProductions() {
        return soldierProductions;
    }

    public static void setSoldierProductions(ArrayList<SoldierProduction> soldierProductions) {
        Database.soldierProductions = soldierProductions;
    }

    public static ArrayList<StorageBuilding> getStorageBuildings() {
        return storageBuildings;
    }

    public static void setStorageBuildings(ArrayList<StorageBuilding> storageBuildings) {
        Database.storageBuildings = storageBuildings;
    }

    public static DefensiveBuilding getDefensiveBuildingDataByName(String name) {
        for (DefensiveBuilding defensiveBuilding : defensiveBuildings) {
            if (defensiveBuilding.getBuildingName().equals(name)) return defensiveBuilding;
        }
        return null;
    }

    public static GateHouse getGatehouseBuildingDataByName(String name) {
        for (GateHouse gateHouse : gateHouses) {
            if (gateHouse.getBuildingName().equals(name)) return gateHouse;
        }
        return null;
    }

    public static MiningBuilding getMiningBuildingDataByName(String name) {
        for (MiningBuilding miningBuilding : miningBuildings) {
            if (miningBuilding.getBuildingName().equals(name)) return miningBuilding;
        }
        return null;
    }

    public static OtherBuilding getOtherBuildingDataByName(String name) {
        for (OtherBuilding otherBuilding : otherBuildings) {
            if (otherBuilding.getBuildingName().equals(name)) return otherBuilding;
        }
        return null;
    }

    public static ProductionBuilding getProductionBuildingDataByName(String name) {
        for (ProductionBuilding productionBuilding : productionBuildings) {
            if (productionBuilding.getBuildingName().equals(name)) return productionBuilding;
        }
        return null;
    }

    public static SoldierProduction getSoldierProductionDataByName(String name) {
        for (SoldierProduction soldierProduction : soldierProductions) {
            if (soldierProduction.getBuildingName().equals(name)) return soldierProduction;
        }
        return null;
    }

    public static StorageBuilding getStorageBuildingDataByName(String name) {
        for (StorageBuilding storageBuilding : storageBuildings) {
            if (storageBuilding.getBuildingName().equals(name)) return storageBuilding;
        }
        return null;
    }

    public static ArrayList<AttackToolsAndMethods> getAttackToolsAndMethods() {
        return attackToolsAndMethods;
    }

    public static void setAttackToolsAndMethods(ArrayList<AttackToolsAndMethods> attackToolsAndMethods) {
        Database.attackToolsAndMethods = attackToolsAndMethods;
    }

    public static AttackToolsAndMethods getAttackToolsDataByName(String name) {
        for (AttackToolsAndMethods attackToolsAndMethod : attackToolsAndMethods) {
            if (attackToolsAndMethod.getName().equals(name)) return attackToolsAndMethod;
        }
        return null;
    }

    public static ArrayList<String> getMapId() {
        return mapId;
    }

    public static void setMapId(ArrayList<String> mapId) {
        Database.mapId = mapId;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(User loggedInUser) {
        Database.loggedInUser = loggedInUser;
    }

    public static void saveUsers() {
        try {
//            FileWriter fileWriter = new FileWriter("src/main/resources/jsons/UserDatabase.json");
            FileWriter fileWriter = new FileWriter("D:/Programming/AP/StrongHold/project-group-17/src/main/resources/jsons/UserDatabase.json");

            String gson = new Gson().toJson(getUsers());
            fileWriter.write(gson);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void loadUsers() {
        try {
//            String json = new String(Files.readAllBytes(Paths.get("src/main/resources/jsons/UserDatabase.json")));
            String json = new String(Files.readAllBytes(Paths.get("D:/Programming/AP/StrongHold/project-group-17/src/main/resources/jsons/UserDatabase.json")));

            ArrayList<User> savedUsers;
            savedUsers = new Gson().fromJson(json, new TypeToken<List<User>>() {}.getType());
            if (savedUsers != null) setUsers(savedUsers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void loadBuildings() {
        loadStorageBuilding();
        loadDefensiveBuilding();
        loadGatehouseBuilding();
        loadMiningBuilding();
        loadOtherBuilding();
        loadProductionBuilding();
        loadSoldierBuilding();
        try {
//            String json = new String(Files.readAllBytes(Paths.get("src/main/resources/jsons/BuildingDatabase.json")));
            String json = new String(Files.readAllBytes(Paths.get("D:/Programming/AP/StrongHold/project-group-17/src/main/resources/jsons/BuildingDatabase.json")));
            ArrayList<Building> savedBuildings;
            savedBuildings = new Gson().fromJson(json, new TypeToken<List<Building>>() {}.getType());
            if (savedBuildings != null) setBuildings(savedBuildings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setStayLoggedInUser(User user) {
        try {
//            FileWriter fileWriter = new FileWriter("src/main/resources/jsons/StayLoggedInUser.json");
            FileWriter fileWriter = new FileWriter("D:/Programming/AP/StrongHold/project-group-17/src/main/resources/jsons/StayLoggedInUser.json");
            String gson = new Gson().toJson(user);
            fileWriter.write(gson);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void clearStayLoggedIn() {
        try {
//            FileWriter fileWriter = new FileWriter("src/main/resources/jsons/StayLoggedInUser.json");
            FileWriter fileWriter = new FileWriter("D:/Programming/AP/StrongHold/project-group-17/src/main/resources/jsons/StayLoggedInUser.json");
            fileWriter.write("");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getStayLoggedInUser() {
        try {
//            String json = new String(Files.readAllBytes(Paths.get("src/main/resources/jsons/StayLoggedInUser.json")));
            String json = new String(Files.readAllBytes(Paths.get("D:/Programming/AP/StrongHold/project-group-17/src/main/resources/jsons/StayLoggedInUser.json")));

            User stayLoggedInUser;
            stayLoggedInUser = new Gson().fromJson(json, new TypeToken<User>() {}.getType());
            if (stayLoggedInUser != null) return stayLoggedInUser.getUsername();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void loadUnits() {
        try {
//            String json = new String(Files.readAllBytes(Paths.get("src/main/resources/jsons/SoldiersDatabase.json")));
            String json = new String(Files.readAllBytes(Paths.get("D:/Programming/AP/StrongHold/project-group-17/src/main/resources/jsons/SoldiersDatabase.json")));
            ArrayList<Soldier> savedSoldiers;
            savedSoldiers = new Gson().fromJson(json, new TypeToken<List<Soldier>>() {}.getType());
            if (savedSoldiers != null) setAllSoldiers(savedSoldiers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadDefensiveBuilding() {
        try {
//            String json = new String(Files.readAllBytes(Paths.get("src/main/resources/jsons/DefensiveBuildings.json")));
            String json = new String(Files.readAllBytes(Paths.get("D:/Programming/AP/StrongHold/project-group-17/src/main/resources/jsons/DefensiveBuildings.json")));
            ArrayList<DefensiveBuilding> savedBuildings;
            savedBuildings = new Gson().fromJson(json, new TypeToken<List<DefensiveBuilding>>() {}.getType());
            if (savedBuildings != null) setDefensiveBuildings(savedBuildings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadGatehouseBuilding() {
        try {
//            String json = new String(Files.readAllBytes(Paths.get("src/main/resources/jsons/GatehouseBuildings.json")));
            String json = new String(Files.readAllBytes(Paths.get("D:/Programming/AP/StrongHold/project-group-17/src/main/resources/jsons/GatehouseBuildings.json")));
            ArrayList<GateHouse> savedBuildings;
            savedBuildings = new Gson().fromJson(json, new TypeToken<List<GateHouse>>() {}.getType());
            if (savedBuildings != null) setGateHouses(savedBuildings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadMiningBuilding() {
        try {
//            String json = new String(Files.readAllBytes(Paths.get("src/main/resources/jsons/MiningBuildings.json")));
            String json = new String(Files.readAllBytes(Paths.get("D:/Programming/AP/StrongHold/project-group-17/src/main/resources/jsons/MiningBuildings.json")));
            ArrayList<MiningBuilding> savedBuildings;
            savedBuildings = new Gson().fromJson(json, new TypeToken<List<MiningBuilding>>() {}.getType());
            if (savedBuildings != null) setMiningBuildings(savedBuildings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadOtherBuilding() {
        try {
//            String json = new String(Files.readAllBytes(Paths.get("src/main/resources/jsons/OtherBuildings.json")));
            String json = new String(Files.readAllBytes(Paths.get("D:/Programming/AP/StrongHold/project-group-17/src/main/resources/jsons/OtherBuildings.json")));
            ArrayList<OtherBuilding> savedBuildings;
            savedBuildings = new Gson().fromJson(json, new TypeToken<List<OtherBuilding>>() {}.getType());
            if (savedBuildings != null) setOtherBuildings(savedBuildings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadProductionBuilding() {
        try {
//            String json = new String(Files.readAllBytes(Paths.get("src/main/resources/jsons/ProductionBuildings.json")));
            String json = new String(Files.readAllBytes(Paths.get("D:/Programming/AP/StrongHold/project-group-17/src/main/resources/jsons/ProductionBuildings.json")));
            ArrayList<ProductionBuilding> savedBuildings;
            savedBuildings = new Gson().fromJson(json, new TypeToken<List<ProductionBuilding>>() {}.getType());
            if (savedBuildings != null) setProductionBuildings(savedBuildings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadSoldierBuilding() {
        try {
//            String json = new String(Files.readAllBytes(Paths.get("src/main/resources/jsons/SoldierBuildings.json")));
            String json = new String(Files.readAllBytes(Paths.get("D:/Programming/AP/StrongHold/project-group-17/src/main/resources/jsons/SoldierBuildings.json")));
            ArrayList<SoldierProduction> savedBuildings;
            savedBuildings = new Gson().fromJson(json, new TypeToken<List<SoldierProduction>>() {}.getType());
            if (savedBuildings != null) setSoldierProductions(savedBuildings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadStorageBuilding() {
        try {
//            String json = new String(Files.readAllBytes(Paths.get("src/main/resources/jsons/StorageBuildings.json")));
            String json = new String(Files.readAllBytes(Paths.get("D:/Programming/AP/StrongHold/project-group-17/src/main/resources/jsons/StorageBuildings.json")));
            ArrayList<StorageBuilding> savedBuildings;
            savedBuildings = new Gson().fromJson(json, new TypeToken<List<StorageBuilding>>() {}.getType());
            if (savedBuildings != null) setStorageBuildings(savedBuildings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadAttackToolsAndMethods() {
        try {
//            String json = new String(Files.readAllBytes(Paths.get("src/main/resources/jsons/AttackToolsAndMethodsDatabase.json")));
            String json = new String(Files.readAllBytes(Paths.get("D:/Programming/AP/StrongHold/project-group-17/src/main/resources/jsons/AttackToolsAndMethodsDatabase.json")));
            ArrayList<AttackToolsAndMethods> savedBuildings;
            savedBuildings = new Gson().fromJson(json, new TypeToken<List<AttackToolsAndMethods>>() {}.getType());
            if (savedBuildings != null) setAttackToolsAndMethods(savedBuildings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
