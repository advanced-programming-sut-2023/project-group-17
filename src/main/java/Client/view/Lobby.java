package Client.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Lobby {
    private static String adminUsername;
    private static int capacity;
    private static int gameTurns;
    private static int lobbyCode;
    public Lobby(String adminUsername, int capacity, int gameTurns, int lobbyCode) {
        Lobby.adminUsername = adminUsername;
        Lobby.capacity = capacity;
        Lobby.gameTurns = gameTurns;
        Lobby.lobbyCode = lobbyCode;
    }

    public static String getAdminUsername() {
        return adminUsername;
    }

    public static int getCapacity() {
        return capacity;
    }

    public static int getGameTurns() {
        return gameTurns;
    }

    public static int getLobbyCode() {
        return lobbyCode;
    }

    public static void setLobbyCode(int lobbyCode) {
        Lobby.lobbyCode = lobbyCode;
    }
}
