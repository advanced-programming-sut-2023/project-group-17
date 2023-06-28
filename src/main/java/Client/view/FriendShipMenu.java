package Client.view;

import Client.ClientMain;
import Client.controller.Controller;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Objects;

public class FriendShipMenu extends Application {

    public TextField usernameTextField;
    public Button sendRequestButton;
    public Text userNotFound;
    public ScrollPane friendshipRequests;
    public Button backButton;

    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = FXMLLoader.load(LoginMenu.class.getResource("/fxml/FriendshipMenu.fxml"));
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
        sendRequestButton.setDisable(true);
        setScrollPane();
        usernameTextField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            String result = (String) Controller.send("user exist", newValue);
            sendRequestButton.setDisable(result == null);
        });
    }

    private void setScrollPane() {
        VBox vBox = new VBox(); vBox.setAlignment(Pos.CENTER); vBox.setSpacing(5);
        int counter = 0;
        while (true) {
            String user = (String) Controller.send("get user by index", counter);
            if (Objects.equals(user, "finish")) break;
            System.out.println(user);
            Text text = new Text(user); text.setFont(Font.font(10));
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
}
