package com.company.dymrin25_2;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClientHandlerRegistry {

    private final Map<String[], ClientHandler> handlers = new ConcurrentHashMap<>();

    public Map<String[], ClientHandler> getHandlers() {
        return handlers;
    }

    public void register(String[] clientInfo, ClientHandler clientHandler) {
        handlers.put(clientInfo, clientHandler);
    }

    public void deleteUser(String[] clientInfo, ClientHandler clientHandler) {
        handlers.remove(clientInfo, clientHandler);
    }
}
