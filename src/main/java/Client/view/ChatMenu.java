package Client.view;

import Client.controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import static Client.ClientMain.stage;

public class ChatMenu extends Application {
    public Button roomButton;
    public Button publicButton;
    public Button privateButton;
    public Button backButton;
    public int lobbyCode;
    public ChatMenu() {
        this.lobbyCode = Lobby.getLobbyCode();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = FXMLLoader.load(getClass().getResource("/fxml/chat.fxml"));
//        pane.setBackground(new Background(new BackgroundImage(new Image(LoginMenu.class.getResource(
//                "/assets/Backgrounds/mainBackground.jpg").toExternalForm()),
//                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
//                new BackgroundSize(1.0, 1.0, true, true, false, false))));

        stage.getScene().setRoot(pane);
        stage.setFullScreen(true);
        stage.show();
    }

    public void enterLobbyMenu(MouseEvent mouseEvent) throws Exception {
        //TODO
        String usernameAdmin = (String) Controller.send("get lobby admin by code", lobbyCode);
        int capacity = ((Double) Controller.send("get capacity by code", lobbyCode)).intValue();
        int gameTurns = ((Double) Controller.send("get turns by code", lobbyCode)).intValue();
        Lobby lobby = new Lobby(usernameAdmin, capacity, gameTurns, lobbyCode);
        new LobbyMenu().start(stage);
    }
}
