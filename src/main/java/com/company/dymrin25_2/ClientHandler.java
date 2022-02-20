package com.company.dymrin25_2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.Date;

public class ClientHandler {

    private ServerEngine serverEngine;
    private ClientHandlerMessager messager;
    private String name;
    private String[] clientInfo;

    public ClientHandler(ServerEngine serverEngine, Socket socket, int clientNumber) {
        try {
            System.out.printf("[SERVER | %s] Client handler starting...%n", new Date());

            this.serverEngine = serverEngine;
            messager = new ClientHandlerMessager(
                    new DataInputStream(socket.getInputStream()),
                    new DataOutputStream(socket.getOutputStream())
            );

            doAuthentication(socket, clientNumber);
            doListen();
            System.out.printf("[SERVER | %s] [%s] has disconnected from the server.%n", new Date(), name);
            serverEngine.broadcastMessage(String.format("[%s] has disconnected from the server.", name));
            serverEngine.deleteUser(clientInfo, this);
        } catch (IOException e) {
            throw new RuntimeException("SWW during the client connection.", e);
        }
    }

    public ClientHandlerMessager getMessager() {
        return messager;
    }

    private void doAuthentication(Socket socket, int clientNumber) {
        System.out.printf("[SERVER | %s] Attempt of client connection ...%n", new Date());
        messager.sendOutboundMessage("Please enter -start to connect to the server");
        String credentials = messager.listenInboundMessage();
        System.out.printf("[SERVER | %s] Authentication payload%n", new Date());
        while (!credentials.contains("-start")) {
            messager.sendOutboundMessage("You entered wrong command, please enter again correct command: -start");
            System.out.printf("[SERVER | %s] Authentication failed: client entered data by wrong format: %s%n", new Date(), credentials);
            credentials = messager.listenInboundMessage();
        }
        if (credentials.contains("start")) {
            name = String.format("Client № %d", clientNumber);
            clientInfo = new String[]{name, socket.toString(), new Date().toString()};
            System.out.println(Arrays.toString(clientInfo));

            serverEngine.broadcastMessage(String.format("[%s] connected to the server.", name));
            serverEngine.register(clientInfo, this);
            messager.sendOutboundMessage("You successfully connected to the server.");

            System.out.printf("[SERVER | %s] Client with name [%s] has connected successfully.%n", new Date(), name);
            return;

        }
        System.out.printf("[SERVER | %s] Client connection was not successful.%n", new Date());
    }

    private void doListen() {
        System.out.printf("[SERVER | %s] Client with name [%s] has started to listen inbound messages.%n", new Date(), name);

        while (true) {
            String inboundMessage = messager.listenInboundMessage();
            System.out.printf("[SERVER | %s] Client with name [%s] initiates message [%s] broadcast.%n", new Date(), name, messager);
            if (inboundMessage.equals("-exit")) {
                break;
            }

            messager.sendOutboundMessage("Echo: " + inboundMessage);

        }
    }
}
