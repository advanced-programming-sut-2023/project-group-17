package Server.controller;

import Server.model.Response;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static Server.model.Global.gson;

public class CommandSender {
    private final DataInputStream dataInputStream;
    private final DataOutputStream dataOutputStream;

    public CommandSender(Socket socket) throws IOException {
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
    }

    public void sendCommand(Response response) {
        try {
            String s = dataInputStream.readUTF();
            dataOutputStream.writeUTF(gson.toJson(response));
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
