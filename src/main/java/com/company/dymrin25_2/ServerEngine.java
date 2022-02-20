package com.company.dymrin25_2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerEngine {
    private ClientHandlerRegistry clientHandlerRegistry;
    private int clientNumber;

    public void start() {
        try {
            clientHandlerRegistry = new ClientHandlerRegistry();
            ServerSocket socket = new ServerSocket(8888);
            while (socket.isBound()) {
                Socket accept = socket.accept();
                clientNumber++;
                new Thread(() -> new ClientHandler(this, accept, clientNumber)).start();
            }
        } catch (IOException e) {
            throw new RuntimeException("SWW during server start-up.", e);
        }
    }

    public void register(String[] clientInfo, ClientHandler clientHandler) {
        clientHandlerRegistry.register(clientInfo, clientHandler);
    }

    public void broadcastMessage(String message) {
        for (ClientHandler clientHandler : clientHandlerRegistry.getHandlers().values()) {
            clientHandler.getMessager().sendOutboundMessage(message);
        }
    }

    public void deleteUser(String[] clientInfo, ClientHandler clientHandler) {
        clientHandlerRegistry.deleteUser(clientInfo, clientHandler);
    }
}
