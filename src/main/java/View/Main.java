package View;

import Controller.LoginMenuController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application{
    public static Stage stage;
    public LoginMenuController controller = new LoginMenuController();
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = new Pane();
        stage.setScene(new Scene(pane));
        Main.stage = stage;
        controller.loadCaptcha();
        if (controller.checkStayLoggedIn()) new MainMenu().start(stage);
        else new LoginMenu().start(stage);
    }
}