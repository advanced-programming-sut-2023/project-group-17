package Server.controller;

import Client.model.Request;
import Client.model.Response;
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
        } catch (IOException | NoSuchMethodException | InvocationTargetException | IllegalAccessException exception) {
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
        return null;
    }

    public void setCommandSender(CommandSender commandSender) {
        this.commandSender = commandSender;
    }
}
