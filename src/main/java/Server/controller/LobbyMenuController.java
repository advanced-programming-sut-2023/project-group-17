package Server.controller;

import Server.model.Database;
import Server.model.Lobby;
import Server.model.User;

public class LobbyMenuController {
    public void exitFromLobby(User user, int lobbyCode) {
        Lobby lobby = Database.getLobbyWithCode(lobbyCode);
        lobby.getWaitingUsernames().remove(user.getUsername());
        if (lobby.getWaitingUsernames().size() == 0) {
            Database.getLobbies().remove(lobby);
            Database.saveLobbies();
        } else if (lobby.getAdminUsername().equals(user.getUsername())) {
            lobby.setNextAdmin();
        }
    }
}
