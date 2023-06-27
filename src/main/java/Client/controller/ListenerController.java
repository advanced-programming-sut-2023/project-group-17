package Client.controller;

import Client.model.Response;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ListenerController extends Thread{
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public ListenerController() {
        this.setDaemon(true);
        try {
            //TODO: host and port?
            Socket socket = new Socket("localhost", 13000);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        //TODO
    }

    private void handle(Response response) {
        //TODO
    }
}
