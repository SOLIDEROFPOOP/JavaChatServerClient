package com.muratprojects;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server implements Runnable{
    private ArrayList<ConnectionHandler> connectionsList;
    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(9999);
            Socket client = serverSocket.accept();
            ConnectionHandler handler = new ConnectionHandler(client);
            connectionsList.add(handler);

        } catch (IOException e) {
            // TODO: logging nado dobavit
        }
    }
    public void broadcast(String message){
        for (ConnectionHandler ch : connectionsList){
            if (ch != null){
                ch.sendMessage(message);
            }
        }
    }
    class ConnectionHandler implements Runnable{


        private Socket client;
        private BufferedReader in;
        private PrintWriter out;

        private String nickName;
        public ConnectionHandler(Socket client){
            this.client = client;
        }
        @Override
        public void run() {
            try {
                out = new PrintWriter(client.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                out.println("Please enter nickname: ");
                String temp = in.readLine();
                if (!temp.isEmpty() && temp != null){
                    nickName = temp;
                    System.out.println(nickName + "connected!");
                    broadcast(nickName + " joined the chat!");
                }
                String message;
                while ((message = in.readLine()) != null){
                    if (message.startsWith("/nick")){
                        
                        // TODO: handle changing nickname
                    } else if (message.startsWith("/quit")) {
                        // TODO: quit
                    } else {
                        broadcast(nickName + ": " + message);
                    }
                }
            } catch (IOException e){
                // TODO: yeah need to implement
            }
        }
        public void sendMessage(String message){
            out.println(message);
        }
    }
}
