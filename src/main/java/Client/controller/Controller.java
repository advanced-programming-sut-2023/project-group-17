package Client.controller;

import Client.model.ClientDatabase;
import Client.model.Request;
import Client.model.Response;
import Client.view.LoginMenu;
import Model.Database;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Controller {
    private static final SocketController SOCKET_CONTROLLER = new SocketController();

    public static synchronized Object send(String method, Object... parameters) {
        Request request = new Request();
        request.setMethodName(method);
        for (Object parameter : parameters) {
            request.addParameter(parameter);
        }
        Response response = SOCKET_CONTROLLER.send(request);
        return response.getAnswer();
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
