package Server.model;

import java.util.ArrayList;

public class Lobby {
    private String adminUsername;
    private final int capacity;
    private final int gameTurns;
    private ArrayList<String> waitingUsernames;
    private final int code;
    private boolean privateLobby;
    public Lobby(User admin, int capacity, int gameTurns) {
        this.adminUsername = admin.getUsername();
        this.capacity = capacity;
        this.waitingUsernames = new ArrayList<>();
        waitingUsernames.add(admin.getUsername());
        this.gameTurns = gameTurns;
        this.code = getLobbyCode();
        this.privateLobby = false;
    }

    private int getLobbyCode() {
        int code = (int) (Math.random() * 1000);
        for (Lobby lobby : Database.getLobbies()) {
            if (lobby != this && code == lobby.getCode()) {
                code = getLobbyCode();
                break;
            }
        }
        return code;
    }

    public String getAdminUsername() {
        return adminUsername;
    }

    public int getCapacity() {
        return capacity;
    }

    public ArrayList<String> getWaitingUsernames() {
        return waitingUsernames;
    }

    public int getFilledCapacity() {
        return waitingUsernames.size();
    }

    public int getGameTurns() {
        return gameTurns;
    }

    public int getCode() {
        return code;
    }

    public boolean isPrivateLobby() {
        return privateLobby;
    }

    public void setPrivateLobby(boolean privateLobby) {
        this.privateLobby = privateLobby;
    }

    public void setNextAdmin() {
        adminUsername = waitingUsernames.get(0);
    }
}
