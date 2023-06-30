package Client.controller;

import Client.model.Chat;
import Client.model.Global;
import Client.model.Request;
import Client.model.Response;
import Server.model.Lobby;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketController {
    private ListenerController listenerController;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public SocketController() {
        try {
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
        String data = "";
        try {
            Gson gson = Global.gson;
            dataOutputStream.writeUTF(gson.toJson(request));
            dataOutputStream.flush();
            System.out.println("Waiting for response");
            Response response = gson.fromJson(dataInputStream.readUTF(), Response.class);
            System.out.println("response received");
            return response;
        } catch (Exception e) {
            System.out.println(data);
            e.printStackTrace();
            return send(request);
        }
    }

    public Object sendChat(Request request) {
        String data = "";
        try {
            Gson gson = Global.gson;
            dataOutputStream.writeUTF(gson.toJson(request));
            dataOutputStream.flush();
            System.out.println("Waiting for response");
//            System.out.println("read : " + dataInputStream.readUTF());
            Object response = null;
            if (request.getMethodName().equals("all chat")) {
                response = new Gson().fromJson(dataInputStream.readUTF(), new TypeToken<List<Chat>>() {
                }.getType());
            }
            else if (request.getMethodName().equals("update chat")) {
                response = new Gson().fromJson(dataInputStream.readUTF(), Chat.class);
            }
//            else if (request.getMethodName().equals("send message chat")) {
//                ;
//            }
//            else if (request.getMethodName().equals("save chat")) {
//                ;
//            }
            System.out.println("response received");
            return response;
        } catch (Exception e) {
            System.out.println(data);
            e.printStackTrace();
            return sendChat(request);
        }
    }
}
