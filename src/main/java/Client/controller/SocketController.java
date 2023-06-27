package Client.controller;

import Client.model.Request;
import Client.model.Response;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketController {
    private ListenerController listenerController;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public SocketController() {
        try {
            //TODO: host and port?
            Socket socket = new Socket("localhost", 13000);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            listenerController = new ListenerController();
            listenerController.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Response send(Request request) {
        //TODO
        return null;
    }
}
