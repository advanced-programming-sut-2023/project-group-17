package Client.view;

import Client.controller.Controller;
import Server.model.User;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

import static Client.ClientMain.stage;

public class LobbyMenu extends Application {
    public Label adminUsernameLabel;
    public Label lobbyCodeLabel;
    public Label privateLobbyLabel;
    public Button exit;
    private String adminUsername;
    private int capacity;
    private int gameTurns;
    private ArrayList<String> waitingUsernames;
    private int lobbyCode;
    private boolean privateLobby;
    public LobbyMenu() {
        this.adminUsername = Lobby.getAdminUsername();
        this.capacity = Lobby.getCapacity();
        this.waitingUsernames = new ArrayList<>();
        waitingUsernames.add(adminUsername);
        this.gameTurns = Lobby.getGameTurns();
        this.lobbyCode = Lobby.getLobbyCode();
        this.privateLobby = false;
    }
    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = FXMLLoader.load(LoginMenu.class.getResource("/fxml/LobbyMenu.fxml"));
        pane.setBackground(new Background(new BackgroundImage(new Image(LoginMenu.class.getResource(
                "/assets/Backgrounds/mainBackground.jpg").toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(1.0, 1.0, true, true, false, false))));

        stage.getScene().setRoot(pane);
        stage.setFullScreen(true);
        stage.show();
    }

    @FXML
    public void initialize() {
        adminUsernameLabel.setText("Admin : " + adminUsername); adminUsernameLabel.setFont(Font.font(20));
        lobbyCodeLabel.setText("Lobby code : " + lobbyCode); lobbyCodeLabel.setFont(Font.font(20));
        privateLobbyLabel.setText("Public"); privateLobbyLabel.setFont(Font.font(20));
        adminUsernameLabel.setTextFill(Color.WHITE); lobbyCodeLabel.setTextFill(Color.WHITE);
        privateLobbyLabel.setTextFill(Color.WHITE);
    }

    public void exit(ActionEvent actionEvent) throws Exception {
        Controller.send("exit lobby", lobbyCode);
        new MainMenu().start(stage);
    }
}
