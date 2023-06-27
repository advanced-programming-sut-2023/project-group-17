package Server.controller;

import Client.model.Request;
import Client.model.Response;
import Server.model.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
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
        //TODO
    }

    private Response handleRequest(Request request) {
        //TODO
        return null;
    }
}
