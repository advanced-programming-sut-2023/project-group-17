package Server.controller;

import Server.enums.Messages.MainMenuMessages;
import Server.model.*;

import java.util.ArrayList;

public class MainMenuController {
    User loggedInUser = Database.getCurrentUser();
    public MainMenuMessages startNewGame(String users, int turnsCount) {
        if (turnsCount <= 0) return MainMenuMessages.INVALID_NUMBER;
        String[] user = users.split(",");
        if (user.length > 8 || user.length < 1) return MainMenuMessages.INVALID_NUMBER_OF_USERS;
        for (int i = 0; i < user.length; i++) {
            if (user[i].startsWith("\"")) user[i] = user[i].substring(1);
            if (user[i].endsWith("\"")) user[i] = user[i].substring(0, user[i].length() - 1);
        }

//        for (String username : user) {
//            if (Database.getUserByUsername(username) == null) return MainMenuMessages.USERNAME_DOES_NOT_EXIST;
//        }
        empireColors.addColors();
        Database.setEmpireColors(empireColors.getColors());

        for (String username : user) {
            Database.getUsersInTheGame().add(Database.getUserByUsername(username));
        }

        for (int i = 0; i < Database.getUsersInTheGame().size(); i++) {
            Database.getUsersInTheGame().get(i).setEmpire(
                new Empire(Database.getUsersInTheGame().get(i), Database.getEmpireColors().get(i)));
        }
        Database.setTotalTurns(turnsCount * user.length);
//        Database.setCurrentUser(Database.getLoggedInUser());
        return MainMenuMessages.SUCCESS;
    }

    public void logout() {
        if (Database.getStayLoggedInUser() != null) Database.clearStayLoggedIn();
        Database.setLoggedInUser(null);
    }

    public String getLoggedInUserUsername() {
        return Database.getLoggedInUser().getUsername();
    }

    public String getCurrentUserUsername() {
        return Database.getCurrentUser().getUsername();
    }

    public void addUsers(ArrayList<String> list) {
        for (int i = 2; i < 8; i++) {
            list.add(String.valueOf(i));
        }
//        Database.loadUsers();
//        for (User user : Database.getUsers()) {
//            if (!user.getUsername().equals(Database.getLoggedInUser().getUsername()))
//                list.add(user.getUsername());
//        }
    }

    public MainMenuMessages createNewMap(int width, int length) {
        if(width <= 0) {
            return MainMenuMessages.INVALID_WIDTH;
        }

        if(length <= 0) {
            return MainMenuMessages.INVALID_LENGTH;
        }

        Map map = new Map(length, width);
        Database.getAllMaps().add(map);
        Database.setCurrentMapGame(map);
        return MainMenuMessages.SUCCESS;
    }

    public int createNewLobby(User admin, int capacity, int turn) {
        Lobby lobby = new Lobby(admin, capacity, turn);
        Database.getLobbies().add(lobby);
        Database.saveLobbies();
        return lobby.getCode();
    }

    public boolean isLobbyExist(int code) {
        Lobby lobby = Database.getLobbyWithCode(code);
        return lobby != null && lobby.canJoin();
    }

    public String getLobbyAdminUsernameByCode(int code) {
        return Database.getLobbyWithCode(code).getAdminUsername();
    }

    public int getCapacityByCode(int code) {
        return Database.getLobbyWithCode(code).getCapacity();
    }

    public int getTurnsByCode(int code) {
        return Database.getLobbyWithCode(code).getGameTurns();
    }

    public ArrayList<String> getPublicLobbies() {
        ArrayList<String> lobbies = new ArrayList<>();
        for (Lobby lobby : Database.getLobbies()) {
            if (!lobby.isPrivateLobby() && lobby.canJoin())
                lobbies.add("Admin : " + lobby.getAdminUsername() + "  code : " + lobby.getCode() + " capacity : " + lobby.getFilledCapacity() + "/" + lobby.getCapacity());
        }
        return lobbies;
    }

    public void joinLobby(User user, int code) {
        Lobby lobby = Database.getLobbyWithCode(code);
        lobby.getWaitingUsernames().add(user.getUsername());
        Database.saveLobbies();
    }

    public String getAvatarPath(String username) {
        if (Database.getUserByUsername(username).getAvatarPath() == null) {
            Database.getUserByUsername(username).setAvatarPath(Database.getUserByUsername(username).randomPathGenerator());
            Database.saveUsers();
        }
        return Database.getUserByUsername(username).getAvatarPath();
    }
}
