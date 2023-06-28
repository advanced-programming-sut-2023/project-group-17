package Server.controller;

import Client.model.Request;
import Client.model.Response;
import Client.view.LoginMenu;
import Server.model.Global;
import Server.model.User;
import com.google.gson.Gson;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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
                //System.out.println("<<REQUEST>> : \n" + s); // TODO : delete this line
                Request request = gson.fromJson(s, Request.class);
                //System.out.println("New request from " + socket);
                Response response = handleRequest(request);
                // System.out.println("<<RESPONSE>> : \n" + gson.toJson(response)); // TODO : delete this line
                dataOutputStream.writeUTF(gson.toJson(response));
                dataOutputStream.flush();
            }
        } catch (IOException exception) {
            //TODO: more exception
            if (user != null)
                user.setLastOnlineTime(LocalDateTime.now());
            exception.printStackTrace();
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
            signupMenuController.createUser((String) request.getParameters().get(0), (String) request.getParameters().get(1),
            (String) request.getParameters().get(1), (String) request.getParameters().get(2), (String) request.getParameters().get(3),
            (String) request.getParameters().get(4));
            signupMenuController.pickQuestion(((Double) request.getParameters().get(5)).intValue(), (String) request.getParameters().get(6), (String) request.getParameters().get(6));
            response.setAnswer("1");
            return response;
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
        }
    }

    public void setCommandSender(CommandSender commandSender) {
        this.commandSender = commandSender;
    }
}
