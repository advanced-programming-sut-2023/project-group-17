package Server.controller;

import Server.model.Database;
import Server.model.Lobby;
import Server.model.User;

import java.util.ArrayList;

public class LobbyMenuController {
    public void exitFromLobby(User user, int lobbyCode) {
        Lobby lobby = Database.getLobbyWithCode(lobbyCode);
        lobby.getWaitingUsernames().remove(user.getUsername());
        if (lobby.getWaitingUsernames().size() == 0) {
            Database.getLobbies().remove(lobby);
        } else if (lobby.getAdminUsername().equals(user.getUsername())) {
            lobby.setNextAdmin();
        }
        user.setLobby(null);
        Database.saveLobbies();
    }

    public ArrayList<String> getLobbyUsers(int code) {
        Lobby lobby = Database.getLobbyWithCode(code);
        return new ArrayList<>(lobby.getWaitingUsernames());
    }

    public void changePublicity(int code) {
        Lobby lobby = Database.getLobbyWithCode(code);
        lobby.setPrivateLobby(!lobby.isPrivateLobby());
    }

    public void setStartGame(int code) {
        Lobby lobby = Database.getLobbyWithCode(code);
        lobby.setGameStarted(true);
    }
}
