package Client.view;

import Client.ClientMain;
import Client.controller.Controller;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ScoreBoardMenu extends Application {
    public TextArea textArea;
    private static int scrollTimes = 0;

    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = FXMLLoader.load(ProfileMenu.class.getResource("/fxml/Scoreboard.fxml"));
//        pane.getChildren().add(new ImageView(new Image(ProfileMenu.class.
//                getResource("/assets/Backgrounds/LoginBackground.PNG").toExternalForm())));
        Scene scene = new Scene(pane);
        ClientMain.stage.setFullScreen(false);
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                ClientMain.stage.setFullScreen(true);
            }
        });
    }

    @FXML
    public void initialize() {
        textArea.setText(generateText(0));
        createAutoUpdateTimeLine();
    }

    private void createAutoUpdateTimeLine() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(2), e -> textArea.setText(generateText(scrollTimes))));
        timeline.setCycleCount(-1);
        timeline.play();
    }

    public void showNextTen(ScrollEvent scrollEvent) {
        scrollTimes++;
        textArea.setText(generateText(scrollTimes));
    }

    private String generateText(int scrollTimes) {
        String res = (String) Controller.send("score board", scrollTimes);
        return res;
    }
}
