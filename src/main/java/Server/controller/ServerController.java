package Server.controller;

import java.io.IOException;
import java.util.ArrayList;

public class ServerController {
    private static ServerController serverController = null;

    private ServerController() {

    }

    public static ServerController getInstance() {
        if (serverController == null)
            serverController = new ServerController();
        return serverController;
    }

    private ArrayList<SocketHandler> socketHandlers = new ArrayList<>();
    private ArrayList<SocketHandler> socketHandlersPlaying = new ArrayList<>();

    public ArrayList<SocketHandler> getSocketHandlers() {
        return socketHandlers;
    }

    public ArrayList<SocketHandler> getSocketHandlersPlaying() {
        return socketHandlersPlaying;
    }

    public void run() throws IOException {
        //TODO
    }

    public void removeSocket(SocketHandler socketHandler) {
        socketHandlers.remove(socketHandler);
    }
}
