package View;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ScoreBoardMenu extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = FXMLLoader.load(ProfileMenu.class.getResource("/fxml/Scoreboard.fxml"));
        pane.getChildren().add(new ImageView(new Image(ProfileMenu.class.
                getResource("/assets/Backgrounds/LoginBackground.PNG").toExternalForm())));
        Scene scene = new Scene(pane);
        Main.stage.setFullScreen(false);
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Main.stage.setFullScreen(true);
            }
        });
    }

    public void showNextTen(ScrollEvent swipeEvent) {
        System.out.println("hi");
    }
}
