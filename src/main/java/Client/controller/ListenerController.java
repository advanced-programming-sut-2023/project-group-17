package Client.controller;

import Client.model.Global;
import Client.model.Request;
import Client.model.Response;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ListenerController extends Thread{
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public ListenerController() {
        this.setDaemon(true);
        try {
            Socket socket = new Socket("localhost", 13000);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                Gson gson = Global.gson;
                dataOutputStream.writeUTF(gson.toJson(new Request()));
                dataOutputStream.flush();
                System.out.println("Waiting for command from server");
                Response response = gson.fromJson(dataInputStream.readUTF(), Response.class);
                System.out.println("command from server received");
                handle(response);
            }
        } catch (Exception e) {
            System.out.println("Server disconnected");
            System.exit(0);
        }
    }

    private void handle(Response response) {
        //TODO
    }
}
