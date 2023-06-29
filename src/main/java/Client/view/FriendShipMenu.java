package Client.view;

import Client.ClientMain;
import Client.controller.Controller;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Objects;

public class FriendShipMenu extends Application {
    private static Pane pane;

    public TextField usernameTextField;
    public Button sendRequestButton;
    public Text userNotFound;
    public ScrollPane friendshipRequests;
    public Button backButton;
    public Text usernameRequest;
    public Button acceptButton;
    public Button declineButton;
    public Button nextButton;
    public ListView<String> listView = new ListView<>();
    public int counter = 0;

    @Override
    public void start(Stage stage) throws Exception {
        //TODO: show profile
        Pane pane = FXMLLoader.load(LoginMenu.class.getResource("/fxml/FriendshipMenu.fxml"));
        pane.setBackground(new Background(new BackgroundImage(new Image(LoginMenu.class.getResource(
                "/assets/Backgrounds/mainBackground.jpg").toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(1.0, 1.0, true, true, false, false))));
        ArrayList<String> friends = (ArrayList<String>) Controller.send("get friends");
        ObservableList<String> items = FXCollections.observableArrayList(friends);
        listView.setItems(items);
        if (items.size() != 0) {
            listView.setPrefHeight(Math.min(items.size() * 40, 100));
            pane.getChildren().add(listView);
        }
        FriendShipMenu.pane = pane;

        stage.getScene().setRoot(pane);
        stage.setFullScreen(true);
        stage.show();
    }

    @FXML
    public void initialize() {
        sendRequestButton.setDisable(true);
        setScrollPane();
        setInviteRequests();
        usernameTextField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            String result = (String) Controller.send("can send request", newValue);
            sendRequestButton.setDisable(result == null);
        });
    }

    private void setListView() {
        ArrayList<String> friends = (ArrayList<String>) Controller.send("get friends");
        ObservableList<String> items = FXCollections.observableArrayList(friends);
        listView.setItems(items);
        listView.setPrefHeight(Math.min(items.size() * 40, 100));
        FriendShipMenu.pane.getChildren().add(listView);
    }

    private void setInviteRequests() {
        int numberOfRequests = ((Double) Controller.send("number of requests")).intValue();
        if (numberOfRequests <= 1) {
            nextButton.setDisable(true);
            if (numberOfRequests == 0) {
                acceptButton.setDisable(true);
                declineButton.setDisable(true);
                usernameRequest.setText("No one");
                return;
            }
        }
        usernameRequest.setText((String) Controller.send("get friend request by index", counter));
    }

    private void setScrollPane() {
        VBox vBox = new VBox(); vBox.setAlignment(Pos.CENTER); vBox.setSpacing(5);
        int counter = 0;
        while (true) {
            String user = (String) Controller.send("get user by index", counter);
            if (Objects.equals(user, "finish")) break;
            Text text = new Text(user); text.setFont(Font.font(18));
            vBox.getChildren().add(text);
            counter++;
        }
        friendshipRequests.setContent(vBox);
    }

    public void sendRequest(ActionEvent actionEvent) {
        Controller.send("send request", usernameTextField.getText());
        setScrollPane();
    }

    public void back(ActionEvent actionEvent) throws Exception {
        Controller.send("change menu main");
        new MainMenu().start(ClientMain.stage);
    }

    public void acceptRequest(ActionEvent actionEvent) {
        Controller.send("accept invite", usernameRequest.getText());
        setInviteRequests();
        setListView();
    }

    public void declineRequest(ActionEvent actionEvent) {
        Controller.send("decline invite", usernameRequest.getText());
        setInviteRequests();
    }

    public void nextRequest(ActionEvent actionEvent) {
        counter++;
        setInviteRequests();
    }
}
