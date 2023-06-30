package Client.view;

import Client.controller.Controller;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

import static Client.ClientMain.stage;

public class LobbyMenu extends Application {
    public Label adminUsernameLabel;
    public Label lobbyCodeLabel;
    public Label privateLobbyLabel;
    public Button exit;
    public ListView listView;
    public Text users;
    public Button chat;
    public Button publicButton;
    public Button enterGameButton;
    private String adminUsername;
    private int capacity;
    private int gameTurns;
    private ArrayList<String> waitingUsernames;
    private int lobbyCode;
    private boolean privateLobby;
    public Timeline updateListTimeLine;
    public Timeline enterGameTimeLine;
    public String myUsername;
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
        privateLobbyLabel.setTextFill(Color.WHITE); users.setFont(Font.font(20)); users.setFill(Color.WHITE);
        setListView();
        createUpdateListTimeLine(); createEnterGameTimeLine();
        this.myUsername = (String) Controller.send("get my user");
        enterGameButton.setDisable(true);
        publicButton.setVisible(adminUsername.equals(myUsername));
        enterGameButton.setVisible(adminUsername.equals(myUsername));
    }

    private void createEnterGameTimeLine() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.5), e -> enterGame()));
        this.enterGameTimeLine = timeline;
        timeline.setCycleCount(-1);
        timeline.play();
    }

    private void createUpdateListTimeLine() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.5), e -> refresh()));
        this.updateListTimeLine = timeline;
        timeline.setCycleCount(-1);
        timeline.play();
    }

    private void setListView() {
        ArrayList<String> friends = (ArrayList<String>) Controller.send("get users in lobby", lobbyCode);
        enterGameButton.setDisable(friends.size() < 2);
        ObservableList<String> items = FXCollections.observableArrayList(friends);
        listView.setItems(items);
        listView.setPrefHeight(Math.min(items.size() * 40, 100));
        listView.setCellFactory(lv -> {
            ListCell<String> cell = new ListCell<>();

            cell.setOnMouseClicked(event -> {
                if (! cell.isEmpty()) {
                    String item = cell.getItem();
                    System.out.println("Item clicked: " + item);
                    showProfile(item);
                }
            });

            cell.textProperty().bind(cell.itemProperty());

            return cell ;
        });
    }

    private void showProfile(String username) {
        HBox hBox = new HBox(); hBox.setAlignment(Pos.CENTER); hBox.setSpacing(5);
        hBox.setPrefHeight(200); hBox.setPrefWidth(350);
        hBox.setStyle("-fx-background-color: black;");
        Label usernameLabel = getLabel(); usernameLabel.setText(username);
        ImageView imageView = new ImageView(new
                Image((String) Controller.send("avatar path friend", username), 100 ,100, false, false));
        hBox.getChildren().addAll(usernameLabel, imageView);
        Popup popup = getPopup();
        popup.getContent().add(hBox);
        popup.show(stage);
        hidePopup(popup).play();
    }

    public Popup getPopup() {
        Popup popup = new Popup();

        //TODO: delete one of these comments
//        popup.setAnchorX(580); popup.setAnchorY(300);
        popup.setAnchorX(540); popup.setAnchorY(250);

        popup.centerOnScreen();
        popup.setOpacity(0.7);
        return popup;
    }

    public Label getLabel() {
        Label label = new Label();
        label.setTextFill(Color.WHITE);
        label.setMinWidth(200);
        label.setMinHeight(60);
//        label.setStyle("-fx-background-color: black;");
        label.setAlignment(Pos.CENTER);
        return label;
    }

    public Timeline hidePopup(Popup popup) {
        return new Timeline(new KeyFrame(Duration.seconds(1.5), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                popup.hide();
            }
        }));
    }

    public void exit(ActionEvent actionEvent) throws Exception {
        Controller.send("exit lobby", lobbyCode);
        updateListTimeLine.stop();
        enterGameTimeLine.stop();
        new MainMenu().start(stage);
    }

    public void enterChatMenu(MouseEvent mouseEvent) throws Exception{
        Controller.send("change menu chat");
        Lobby.setLobbyCode(lobbyCode);
        new ChatMenu().start(stage);
    }

    public void refresh() {
        adminUsernameLabel.setText("Admin : " + Controller.send("get lobby admin by code", lobbyCode));
        adminUsername = (String) Controller.send("get lobby admin by code", lobbyCode);
        publicButton.setVisible(adminUsername.equals(myUsername));
        enterGameButton.setVisible(adminUsername.equals(myUsername));
        setListView();
    }

    public void enterGame() {

    }

    public void publicOrPrivate(ActionEvent actionEvent) {
        privateLobby = !privateLobby;
        if (privateLobby) {
            privateLobbyLabel.setText("Private");
            publicButton.setText("Public");
        }
        else {
            privateLobbyLabel.setText("Public");
            publicButton.setText("Private");
        }
        Controller.send("change publicity", lobbyCode);
    }
}
