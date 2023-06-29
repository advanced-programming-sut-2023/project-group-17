package Server.controller;

import Server.enums.Messages.LoginMenuMessages;
import Server.enums.Messages.SignupMenuMessages;
import Server.model.*;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class SocketHandler extends Thread{
    private CommandSender commandSender;
    private boolean isYourTurn = true;
    private boolean isPlayingGame = false;
    private User user = null;
    private ArrayList<SocketHandler> waitingInLobbyWithYou = new ArrayList<>();
    private final Socket socket;
    private final DataOutputStream dataOutputStream;
    private final DataInputStream dataInputStream;
    //TODO: Xstream?
    private String menu = "Login";
    //TODO: add contorllers
    private LoginMenuController loginMenuController = new LoginMenuController();
    private SignupMenuController signupMenuController;
    private MainMenuController mainMenuController;
    private ProfileMenuController profileMenuController;
    private ScoreBoardController scoreBoardController;
    private FriendshipMenuController friendshipMenuController;
    private LobbyMenuController lobbyMenuController;

    public SocketHandler(Socket socket) throws IOException {
        this.socket = socket;
        waitingInLobbyWithYou.add(this);
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        try {
            while (true) {
                Gson gson = Global.gson;
                String s = dataInputStream.readUTF();
                Request request = gson.fromJson(s, Request.class);
                Response response = handleRequest(request);
                dataOutputStream.writeUTF(gson.toJson(response));
                dataOutputStream.flush();
            }
        } catch (IOException exception) {
            //TODO: more exception
            if (user != null)
                user.setLastOnlineTime(LocalDateTime.now());
            System.out.println("User " + this.getId() + " Got Disconnected");
            ServerController.getInstance().removeSocket(this);
            // TODO : send updated list of users to online users
        }
    }

    private Response handleRequest(Request request) {
        String methodName = request.getMethodName();
        //TODO
        if (methodName.startsWith("change menu")) {
            changeMenu(methodName.substring(12));
            return new Response();
        }
        if (methodName.equals("all users")) {
            Response response = new Response();
            response.setAnswer(Database.getUsers());
            return response;
        }
        if (methodName.equals("random password")) {
            Response response = new Response();
            response.setAnswer(signupMenuController.getRandomPassword());
            return response;
        }
        if (methodName.equals("random slogan")) {
            Response response = new Response();
            response.setAnswer(signupMenuController.getRandomSlogan());
            return response;
        }
        if (methodName.equals("get security question")) {
            Response response = new Response();
            response.setAnswer(signupMenuController.getSecurityQuestions(((Double) request.getParameters().get(0)).intValue()));
            return response;
        }
        if (methodName.equals("create user")) {
            Response response = new Response();
            SignupMenuMessages signupMenuMessage = signupMenuController.createUser(
                    (String) request.getParameters().get(0), (String) request.getParameters().get(1),
                    (String) request.getParameters().get(1), (String) request.getParameters().get(2),
                    (String) request.getParameters().get(3), (String) request.getParameters().get(4));
            if (signupMenuMessage.equals(SignupMenuMessages.SUCCESS))
                signupMenuController.pickQuestion(((Double) request.getParameters().get(5)).intValue(),
                        (String) request.getParameters().get(6), (String) request.getParameters().get(6));
            response.setAnswer(signupMenuMessage.toString());
            return response;
        }
        if (methodName.equals("login")) {
            Response response = new Response();
            LoginMenuMessages loginMenuMessage = loginMenuController.loginUser((String) request.getParameters().get(0), (String) request.getParameters().get(1), (Boolean) request.getParameters().get(2));
            response.setAnswer(loginMenuMessage.toString());
            if (response.getAnswer().equals("SUCCESS")) {
                user = Database.getUserByUsername((String) request.getParameters().get(0));
                user.setLastOnlineTime(null);
                changeMenu("main");
            }
            return response;
        }
        if (methodName.equals("avatar path")) {
            Response response = new Response();
            String path = profileMenuController.getAvatarPath();
            response.setAnswer(path);
            return response;
        }
        if (methodName.equals("currentUsername")) {
            Response response = new Response();
            String username = profileMenuController.getUsername();
            response.setAnswer(username);
            return response;
        }
        if (methodName.equals("currentNickname")) {
            Response response = new Response();
            String nickname = profileMenuController.getNickname();
            response.setAnswer(nickname);
            return response;
        }
        if (methodName.equals("currentEmail")) {
            Response response = new Response();
            String email = profileMenuController.getEmail();
            response.setAnswer(email);
            return response;
        }
        if (methodName.equals("currentSlogan")) {
            Response response = new Response();
            String slogan = profileMenuController.getSlogan();
            response.setAnswer(slogan);
            return response;
        }
        if (methodName.equals("available slogans")) {
            Response response = new Response();
            int number = (Integer) request.getParameters().get(0);
            String slogan = profileMenuController.getAvailableSlogans(number);
            response.setAnswer(slogan);
            return response;
        }
        if (methodName.equals("change email")) {
            Response response = new Response();
            String newEmail = (String) request.getParameters().get(0);
            String answer = profileMenuController.changeEmail(newEmail).toString();
            response.setAnswer(answer);
            return response;
        }
        if (methodName.equals("change password")) {
            Response response = new Response();
            String oldPass = (String) request.getParameters().get(0);
            String newPass = (String) request.getParameters().get(1);
            String answer = profileMenuController.changePassword(oldPass, newPass).toString();
            response.setAnswer(answer);
            return response;
        }
        if (methodName.equals("change username")) {
            Response response = new Response();
            String newUsername = (String) request.getParameters().get(0);
            String answer = profileMenuController.changeUsername(newUsername).toString();
            response.setAnswer(answer);
            return response;
        }
        if (methodName.equals("change nickname")) {
            Response response = new Response();
            String newNickname = (String) request.getParameters().get(0);
            String answer = profileMenuController.changeNickname(newNickname).toString();
            response.setAnswer(answer);
            return response;
        }
        if (methodName.equals("change slogan")) {
            Response response = new Response();
            String newSlogan = (String) request.getParameters().get(0);
            String answer = profileMenuController.changeSlogan(newSlogan).toString();
            response.setAnswer(answer);
            return response;
        }
        if (methodName.equals("logout")) {
            user.setLastOnlineTime(LocalDateTime.now());
            user = null;
            Database.saveLobbies();
            changeMenu("login");
            return new Response();
        }
        if (methodName.equals("score board")) {
            Response response = new Response();
            String result = scoreBoardController.scoreBoard((Double) request.getParameters().get(0));
            response.setAnswer(result);
            return response;
        }

        if (methodName.equals("send request")) {
            friendshipMenuController.sendFriendRequest(user, (String) request.getParameters().get(0));
            return new Response();
        }
        if (methodName.equals("get user by index")) {
            Response response = new Response();
            if (((Double) request.getParameters().get(0)).intValue() == user.getFriendReqs().size()) {
                response.setAnswer("finish");
                return response;
            }
            response.setAnswer(friendshipMenuController.getUserRequestByIndex(((Double) request.getParameters().get(0)).intValue(), user));
            return response;
        }
        if (methodName.equals("can send request")) {
            Response response = new Response();
            User tmpUser = friendshipMenuController.getUserByUsername((String) request.getParameters().get(0));
            if (!friendshipMenuController.canSendRequest(user, tmpUser)) {
                return response;
            }
            response.setAnswer("Success");
            return response;
        }
        if (methodName.equals("number of requests")) {
            Response response = new Response();
            response.setAnswer(friendshipMenuController.getNumberOfFriendsRequests(user));
            return response;
        }
        if (methodName.equals("get friend request by index")) {
            Response response = new Response();
            response.setAnswer(friendshipMenuController.
                    getFriendsRequestByIndex(user, ((Double)request.getParameters().get(0)).intValue()));
            return response;
        }
        if (methodName.equals("accept invite")) {
            friendshipMenuController.acceptFriendRequest(user, (String) request.getParameters().get(0));
            return new Response();
        }
        if (methodName.equals("get friends")) {
            Response response = new Response();
            response.setAnswer(friendshipMenuController.getFriends(user));
            return response;
        }
        if (methodName.equals("create new lobby")) {
            Response response = new Response();
            response.setAnswer(mainMenuController.createNewLobby(user, ((Double)request.getParameters().get(0)).intValue(),
                    ((Double) request.getParameters().get(1)).intValue()));
            changeMenu("lobby");
            return response;
        }
        if (methodName.equals("get my user")) {
            Response response = new Response();
            response.setAnswer(user.getUsername());
            return response;
        }
        if (methodName.equals("exit lobby")) {
            lobbyMenuController.exitFromLobby(user, ((Double) request.getParameters().get(0)).intValue());
            changeMenu("main");
            return new Response();
        }
        return null;
    }

    private void changeMenu(String menuName) {
        menu = menuName;
        switch (menuName) {
            case "signUp":
                signupMenuController = new SignupMenuController();
                break;
            case "login":
                loginMenuController = new LoginMenuController();
                break;
            case "main":
                mainMenuController = new MainMenuController();
                break;
            case "profile":
                profileMenuController = new ProfileMenuController();
            case "scoreBoard":
                scoreBoardController = new ScoreBoardController();
                break;
            case "friendship":
                friendshipMenuController = new FriendshipMenuController();
                break;
            case "lobby":
                lobbyMenuController = new LobbyMenuController();
                break;
        }
    }

    public void setCommandSender(CommandSender commandSender) {
        this.commandSender = commandSender;
    }
}
