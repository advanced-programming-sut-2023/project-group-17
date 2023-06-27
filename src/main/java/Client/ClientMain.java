package Client;

import Client.controller.Controller;
import Client.view.LoginMenu;
import Client.view.MainMenu;
import Server.controller.LoginMenuController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class ClientMain extends Application {
    public static Stage stage;
    public Controller controller = new Controller();
//    public LoginMenuController controller = new LoginMenuController();
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = new Pane();
        stage.setScene(new Scene(pane));
        ClientMain.stage = stage;
        controller.loadJson();
//        controller.loadCaptcha();
//        if (controller.checkStayLoggedIn()) new MainMenu().start(stage);
//        else new LoginMenu().start(stage);
        new LoginMenu().start(stage);
    }

}
