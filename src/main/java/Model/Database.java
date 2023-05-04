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

import static Model.Buildings.GateHouseType.Type.*;


public class Database {
    private static ArrayList<User> users = new ArrayList<>();
    private static ArrayList<Building> buildings = new ArrayList<>();
    private static ArrayList<DefensiveBuilding> defensiveBuildings = new ArrayList<>();
    private static ArrayList<GateHouse> gateHouses = new ArrayList<>();
    private static ArrayList<MiningBuilding> miningBuildings = new ArrayList<>();
    private static ArrayList<OtherBuilding> otherBuildings = new ArrayList<>();
    private static ArrayList<ProductionBuilding> productionBuildings = new ArrayList<>();
    private static ArrayList<SoldierProduction> soldierProductions = new ArrayList<>();
    private static ArrayList<StorageBuilding> storageBuildings = new ArrayList<>();
    private static Map currentMapGame;
    private static ArrayList<Soldier> allSoldiers = new ArrayList<>();
    private static final ArrayList<User> usersInTheGame = new ArrayList<>();
    private static ArrayList<Map> allMaps = new ArrayList<>();
    private static User loggedInUser = null;
    private static int turnsPassed = 0;
    private static int totalTurns = 0;
    private static ArrayList<empireColors> empireColors;

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

    public static void saveUsers() {
        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/UserDatabase.json");
//            FileWriter fileWriter = new FileWriter("D:/Programming/AP/StrongHold/project-group-17/src/main/resources/UserDatabase.json");

            String gson = new Gson().toJson(getUsers());
            fileWriter.write(gson);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void loadUsers() {
        try {
            String json = new String(Files.readAllBytes(Paths.get("src/main/resources/UserDatabase.json")));
//            String json = new String(Files.readAllBytes(Paths.get("D:/Programming/AP/StrongHold/project-group-17/src/main/resources/UserDatabase.json")));

            ArrayList<User> savedUsers;
            savedUsers = new Gson().fromJson(json, new TypeToken<List<User>>() {}.getType());
            if (savedUsers != null) setUsers(savedUsers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void loadBuildings() {
        try {
            String json = new String(Files.readAllBytes(Paths.get("src/main/resources/BuildingDatabase.json")));
//            String json = new String(Files.readAllBytes(Paths.get("D:/Programming/AP/StrongHold/project-group-17/src/main/resources/BuildingDatabase.json")));
            ArrayList<Building> savedBuildings;
            savedBuildings = new Gson().fromJson(json, new TypeToken<List<Building>>() {}.getType());
            if (savedBuildings != null) setBuildings(savedBuildings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setStayLoggedInUser(User user) {
        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/StayLoggedInUser.json");
//            FileWriter fileWriter = new FileWriter("D:/Programming/AP/StrongHold/project-group-17/src/main/resources/StayLoggedInUser.json");
            String gson = new Gson().toJson(user);
            fileWriter.write(gson);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void clearStayLoggedIn() {
        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/StayLoggedInUser.json");
//            FileWriter fileWriter = new FileWriter("D:/Programming/AP/StrongHold/project-group-17/src/main/resources/StayLoggedInUser.json");
            fileWriter.write("");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static User getStayLoggedInUser() {
        try {
            String json = new String(Files.readAllBytes(Paths.get("src/main/resources/StayLoggedInUser.json")));
//            String json = new String(Files.readAllBytes(Paths.get("D:/Programming/AP/StrongHold/project-group-17/src/main/resources/StayLoggedInUser.json")));

            User stayLoggedInUser;
            stayLoggedInUser = new Gson().fromJson(json, new TypeToken<User>() {}.getType());
            if (stayLoggedInUser != null) return stayLoggedInUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void loadUnits() {
        try {
            String json = new String(Files.readAllBytes(Paths.get("src/main/resources/SoldiersDatabase.json")));
//            String json = new String(Files.readAllBytes(Paths.get("D:/Programming/AP/StrongHold/project-group-17/src/main/resources/SoldiersDatabase.json")));
            ArrayList<Soldier> savedSoldiers;
            savedSoldiers = new Gson().fromJson(json, new TypeToken<List<Soldier>>() {}.getType());
            if (savedSoldiers != null) setAllSoldiers(savedSoldiers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveBuildingsTypes() {
        defensiveBuildings.add(new DefensiveBuilding(null, Direction.directions.NORTH, Database.getBuildingDataByName("lookout tower"), 0, 0, DefensiveBuildingType.DefensiveType.LOOKOUT_TOWER));
        defensiveBuildings.add(new DefensiveBuilding(null, Direction.directions.NORTH, Database.getBuildingDataByName("perimeter tower"), 0, 0, DefensiveBuildingType.DefensiveType.PERIMETER_TOWER));
        defensiveBuildings.add(new DefensiveBuilding(null, Direction.directions.NORTH, Database.getBuildingDataByName("defence turret"), 0, 0, DefensiveBuildingType.DefensiveType.DEFENSE_TURRET));
        defensiveBuildings.add(new DefensiveBuilding(null, Direction.directions.NORTH, Database.getBuildingDataByName("square tower"), 0, 0, DefensiveBuildingType.DefensiveType.SQUARE_TOWER));
        defensiveBuildings.add(new DefensiveBuilding(null, Direction.directions.NORTH, Database.getBuildingDataByName("round tower"), 0, 0, DefensiveBuildingType.DefensiveType.ROUND_TOWER));
        gateHouses.add(new GateHouse(null, Database.getBuildingDataByName("small stone gatehouse"), 0, 0, SMALL_STONE_GATE_HOUSE));
        gateHouses.add(new GateHouse(null, Database.getBuildingDataByName("large stone gatehouse"), 0, 0, LARGE_STONE_GATE_HOUSE));
        gateHouses.add(new GateHouse(null, Database.getBuildingDataByName("hovel"), 0, 0, HOVEL));
        miningBuildings.add(new MiningBuilding(null, Database.getBuildingDataByName("oil smelter"), 0, 0, MiningBuildingType.MiningType.OIL_SMELTER));
        miningBuildings.add(new MiningBuilding(null, Database.getBuildingDataByName("apple orchard"), 0, 0, MiningBuildingType.MiningType.APPLE_ORCHARD));
        miningBuildings.add(new MiningBuilding(null, Database.getBuildingDataByName("hops farmer"), 0, 0, MiningBuildingType.MiningType.HOPS_FARMER));
        miningBuildings.add(new MiningBuilding(null, Database.getBuildingDataByName("hunter post"), 0, 0, MiningBuildingType.MiningType.HUNTER_POST));
        miningBuildings.add(new MiningBuilding(null, Database.getBuildingDataByName("wheat farmer"), 0, 0, MiningBuildingType.MiningType.WHEAT_FARMER));
        miningBuildings.add(new MiningBuilding(null, Database.getBuildingDataByName("iron mine"), 0, 0, MiningBuildingType.MiningType.IRON_MINE));
        miningBuildings.add(new MiningBuilding(null, Database.getBuildingDataByName("pitch rig"), 0, 0, MiningBuildingType.MiningType.PITCH_RIG));
        miningBuildings.add(new MiningBuilding(null, Database.getBuildingDataByName("quarry"), 0, 0, MiningBuildingType.MiningType.QUARRY));
        miningBuildings.add(new MiningBuilding(null, Database.getBuildingDataByName("woodcutter"), 0, 0, MiningBuildingType.MiningType.WOODCUTTER));
        otherBuildings.add(new OtherBuilding(null, Database.getBuildingDataByName("cathedral"), 0, 0, OtherBuildingType.OtherType.CATHEDRAL));
        otherBuildings.add(new OtherBuilding(null, Database.getBuildingDataByName("church"), 0, 0, OtherBuildingType.OtherType.CHURCH));
        otherBuildings.add(new OtherBuilding(null, Database.getBuildingDataByName("ox tether"), 0, 0, OtherBuildingType.OtherType.OX_THEATER));
        otherBuildings.add(new OtherBuilding(null, Database.getBuildingDataByName("market"), 0, 0, OtherBuildingType.OtherType.MARKET));
        otherBuildings.add(new OtherBuilding(null, Database.getBuildingDataByName("drawbridge"), 0, 0, OtherBuildingType.OtherType.DRAWBRIDGE));
        productionBuildings.add(new ProductionBuilding(null, Database.getBuildingDataByName("bakery"), 0, 0, ProductionBuildingType.ProductionType.BAKERY));
        productionBuildings.add(new ProductionBuilding(null, Database.getBuildingDataByName("brewer"), 0, 0, ProductionBuildingType.ProductionType.BREWER));
        productionBuildings.add(new ProductionBuilding(null, Database.getBuildingDataByName("mill"), 0, 0, ProductionBuildingType.ProductionType.MILL));
        productionBuildings.add(new ProductionBuilding(null, Database.getBuildingDataByName("armourer"), 0, 0, ProductionBuildingType.ProductionType.ARMOURER));
        productionBuildings.add(new ProductionBuilding(null, Database.getBuildingDataByName("blacksmith"), 0, 0, ProductionBuildingType.ProductionType.BLACKSMITH));
        productionBuildings.add(new ProductionBuilding(null, Database.getBuildingDataByName("fletcher"), 0, 0, ProductionBuildingType.ProductionType.FLETCHER));
        productionBuildings.add(new ProductionBuilding(null, Database.getBuildingDataByName("poleturner"), 0, 0, ProductionBuildingType.ProductionType.POLETURNER));
        soldierProductions.add(new SoldierProduction(null, Database.getBuildingDataByName("barracks"), 0, 0, SoldierProductionType.SoldierType.BARRACKS));
        soldierProductions.add(new SoldierProduction(null, Database.getBuildingDataByName("mercenary post"), 0, 0, SoldierProductionType.SoldierType.MERCENARY_POST));
        soldierProductions.add(new SoldierProduction(null, Database.getBuildingDataByName("engineer guild"), 0, 0, SoldierProductionType.SoldierType.ENGINEER_GUILD));
        storageBuildings.add(new StorageBuilding(null, Database.getBuildingDataByName("armory"), 0, 0, StorageBuildingType.StorageType.ARMORY));
        storageBuildings.add(new StorageBuilding(null, Database.getBuildingDataByName("caged war dogs"), 0, 0, StorageBuildingType.StorageType.CAGED_WAR_DOGS));
        storageBuildings.add(new StorageBuilding(null, Database.getBuildingDataByName("granary"), 0, 0, StorageBuildingType.StorageType.GRANARY));
        storageBuildings.add(new StorageBuilding(null, Database.getBuildingDataByName("stockpile"), 0, 0, StorageBuildingType.StorageType.STOCKPILE));
    }
    public static void addDefensiveBuilding() {
        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/DefensiveBuildings.json");
            String gson = new Gson().toJson(getDefensiveBuildings());
            fileWriter.write(gson);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addGatehouseBuilding() {
        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/GatehouseBuildings.json");
            String gson = new Gson().toJson(getGateHouses());
            fileWriter.write(gson);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addMiningBuilding() {
        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/MiningBuildings.json");
            String gson = new Gson().toJson(getMiningBuildings());
            fileWriter.write(gson);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addOtherBuilding() {
        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/OtherBuildings.json");
            String gson = new Gson().toJson(getOtherBuildings());
            fileWriter.write(gson);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addProductionBuilding() {
        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/ProductionBuildings.json");
            String gson = new Gson().toJson(getProductionBuildings());
            fileWriter.write(gson);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addSoldierBuilding() {
        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/SoldierBuildings.json");
            String gson = new Gson().toJson(getSoldierProductions());
            fileWriter.write(gson);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addStorageBuilding() {
        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/DefensiveBuildings.json");
            String gson = new Gson().toJson(getStorageBuildings());
            fileWriter.write(gson);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
