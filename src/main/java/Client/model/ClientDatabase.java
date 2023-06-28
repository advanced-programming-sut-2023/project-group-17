package Client.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ClientDatabase {
    private static ArrayList<String> captcha = new ArrayList<>();

    public static void loadJson() {
        loadCaptcha();
    }

    public static void loadCaptcha() {
        try {
            String json = new String(Files.readAllBytes(Paths.get("src/main/resources/jsons/Captchas.json")));
//            String json = new String(Files.readAllBytes(Paths.get("D:/Programming/AP/StrongHold/project-group-17/src/main/resources/jsons/Captchas.json")));
            ArrayList<String> savedCaptcha;
            savedCaptcha = new Gson().fromJson(json, new TypeToken<List<String>>() {}.getType());
            if (savedCaptcha != null) setCaptcha(savedCaptcha);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setCaptcha(ArrayList<String> captcha) {
        ClientDatabase.captcha = captcha;
    }

    public static ArrayList<String> getCaptcha() {
        return captcha;
    }
}
