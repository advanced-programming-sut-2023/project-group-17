package Model.MapGeneration;

import Model.Database;
import Model.Map;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapOrganizer {
    private static String pathGenerator(String id) {
        return "src/main/resources/Maps/" + id + ".json";
    }
    public static void saveMap(String id) {
        try {
            System.out.println(pathGenerator(id));
            FileWriter fileWriter = new FileWriter(pathGenerator(id));
//            FileWriter fileWriter = new FileWriter("D:/Programming/AP/StrongHold/project-group-17/src/main/resources/jsons/UserDatabase.json");
            Map m = Database.getCurrentMapGame();
            String gson = new Gson().toJson(m);
            fileWriter.write(gson);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadMap(String id) {
        try {
            String json = new String(Files.readAllBytes(Paths.get(pathGenerator(id))));
//            String json = new String(Files.readAllBytes(Paths.get("D:/Programming/AP/StrongHold/project-group-17/src/main/resources/Maps/" + id + ".json")));
            Map savedMap;
            savedMap = new Gson().fromJson(json, new TypeToken<Map>() {}.getType());
            if (savedMap != null) Database.setCurrentMapGame(savedMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getMapId() {
        ArrayList<String> list = new ArrayList<>();
        File path = new File("src/main/resources/Maps");
//        File path = new File("D:/Programming/AP/StrongHold/project-group-17/src/main/resources/Maps");
        Pattern pattern = Pattern.compile("(?<mapId>\\d+).json");
        for (String fileName : Objects.requireNonNull(path.list())) {
            Matcher matcher = pattern.matcher(fileName);
            matcher.find();
            String id = matcher.group("mapId");
            if (matcher.matches())
                list.add(id);
        }
        Database.setMapId(list);
    }

    public static boolean idExists(String id) {
        for (String mapId : Database.getMapId()) {
            if (mapId.equals(id)) return true;
        }
        return false;
    }
}
