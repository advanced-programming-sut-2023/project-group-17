package Client.view;

import Client.ClientMain;
import Client.controller.Controller;
import Server.model.Database;
import Server.model.User;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ScoreBoardMenu extends Application {
    private static int scrollTimes = 0;
    public static Timeline timeline;
    public VBox vBox;

    @Override
    public void start(Stage stage) throws Exception {
        vBox = new VBox();
        Pane pane = new Pane(vBox);
        pane.setOnScrollStarted(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                showNextTen(event);
            }
        });
//        Pane pane = FXMLLoader.load(ProfileMenu.class.getResource("/fxml/Scoreboard.fxml"));
//        pane.getChildren().add(new ImageView(new Image(ProfileMenu.class.
//                getResource("/assets/Backgrounds/LoginBackground.PNG").toExternalForm())));
        Scene scene = new Scene(pane);
        ClientMain.stage.setFullScreen(false);
        initialize();
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                timeline.stop();
                ClientMain.stage.setFullScreen(true);
            }
        });
    }

    public void initialize() {
        generateText(0);
        createAutoUpdateTimeLine();
    }

    private void createAutoUpdateTimeLine() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(2), e -> generateText(scrollTimes)));
        ScoreBoardMenu.timeline = timeline;
        timeline.setCycleCount(-1);
        timeline.play();
    }

    public void showNextTen(ScrollEvent scrollEvent) {
        scrollTimes++;
        generateText(scrollTimes);
    }

    private void generateText(int scrollTimes) {
        vBox.getChildren().clear();
        ArrayList<String> usernames = (ArrayList<String>) Controller.send("score board users", scrollTimes);
        ArrayList<String> avatarPaths = (ArrayList<String>) Controller.send("score board paths", scrollTimes);
        ArrayList<Double> scores = (ArrayList<Double>) Controller.send("score board scores", scrollTimes);
        ArrayList<String> onlineTimes = (ArrayList<String>) Controller.send("score board times", scrollTimes);

        int j = 0;
        if (usernames.size() < scrollTimes * 10 + 10) j = usernames.size();
        else j = (int) (scrollTimes * 10 + 10);

        for (int i = (int) (scrollTimes * 10); i < j; i++) {
            ImageView imageView = new ImageView(new Image(avatarPaths.get(i), 70, 70, false, false));
            String onlineTime = onlineTimes.get(i);
            if (onlineTime.equals("null")) onlineTime = "online";
            HBox hBox = new HBox(imageView,
                    new Text(usernames.get(i)), new Text(scores.get(i).toString()), new Text(onlineTime));
            hBox.setSpacing(20);
            vBox.getChildren().add(hBox);
        }
    }
}
