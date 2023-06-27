package Server.controller;

import Client.model.Response;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class CommandSender {
    private final DataInputStream dataInputStream;
    private final DataOutputStream dataOutputStream;

    public CommandSender(Socket socket) throws IOException {
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
    }

    public void sendCommand(Response response) {
        //TODO
    }
}
