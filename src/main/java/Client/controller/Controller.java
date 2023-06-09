package Client.controller;

import Client.model.*;
import Client.view.LoginMenu;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Controller {
    private static final SocketController SOCKET_CONTROLLER = new SocketController();

    public static synchronized Object send(String method, Object... parameters) {
        Request request = new Request();
        request.setMethodName(method);
        for (Object parameter : parameters) {
            request.addParameter(parameter);
        }
        System.out.println(request.getMethodName());
        Response response = SOCKET_CONTROLLER.send(request);
        return response.getAnswer();
    }

    public static synchronized Object sendChat(String method, Object... parameters) {
        Request request = new Request();
        request.setMethodName(method);
        for (Object parameter : parameters) {
            request.addParameter(parameter);
        }
        System.out.println(request.getMethodName());
        return SOCKET_CONTROLLER.sendChat(request);
    }

    public static ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        int numberOfUsers = ((Double)Controller.send("getNumberOfUser")).intValue();
        for (int i = 0; i < numberOfUsers; i++) {
            Request request = new Request();
            request.setMethodName("getUserByIndex");
            request.addParameter(i);
            users.add(User.fromJson((String) SOCKET_CONTROLLER.send(request).getAnswer()));
        }
        return users;
    }

    public static User getUser(String username) {
        Request request = new Request();
        request.setMethodName("getUser");
        request.addParameter(username);
        Response response = SOCKET_CONTROLLER.send(request);
        return User.fromJson((String) response.getAnswer());
    }

    public static User getMyUser() {
        Request request = new Request();
        request.setMethodName("getMyUser");
        Response response = SOCKET_CONTROLLER.send(request);
        return User.fromJson((String) response.getAnswer());
    }

    public void run(Stage primaryStage) throws Exception {
        new LoginMenu().start(primaryStage);
    }

    public void loadJson() {
        ClientDatabase.loadJson();
    }

    public ArrayList<String> getCaptchas() {
        return ClientDatabase.getCaptcha();
    }
}
