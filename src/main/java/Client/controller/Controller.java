package Client.controller;

import Client.view.LoginMenu;
import javafx.stage.Stage;

public class Controller {
    public void run(Stage primaryStage) throws Exception {
        new LoginMenu().start(primaryStage);
    }
}
