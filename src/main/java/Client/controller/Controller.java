package Client.controller;

import Client.model.ClientDatabase;
import Client.view.LoginMenu;
import Model.Database;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Controller {
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
