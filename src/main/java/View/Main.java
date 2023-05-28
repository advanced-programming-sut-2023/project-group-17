package View;

import Controller.LoginMenuController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{
    public static Stage stage;
    public LoginMenuController controller = new LoginMenuController();
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Main.stage = stage;
        if (controller.checkStayLoggedIn()) new MainMenu().start(stage);
        else new LoginMenu().start(stage);
    }

}