package Client.view;

import Client.ClientMain;
import Client.controller.Controller;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static Client.ClientMain.stage;

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
        FriendShipMenu.pane = pane;

        stage.getScene().setRoot(pane);
        stage.setFullScreen(true);
        stage.show();
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
        listView.setCellFactory(lv -> {
            ListCell<String> cell = new ListCell<>();

            cell.setOnMouseClicked(event -> {
                if (! cell.isEmpty()) {
                    String item = cell.getItem();
                    System.out.println("Item clicked: " + item);
                }
            });

            cell.textProperty().bind(cell.itemProperty());

            return cell ;
        });
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
        new MainMenu().start(stage);
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
