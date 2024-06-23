package com.muratprojects;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable{
    private ArrayList<ConnectionHandler> connectionsList;
    private ExecutorService pool;
    private ServerSocket server;
    private boolean done;
    public Server(){
        connectionsList = new ArrayList<>();
        done = false;
    }
    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(9999);
            pool = Executors.newCachedThreadPool();
            while (!done){
                Socket client = serverSocket.accept();
                ConnectionHandler handler = new ConnectionHandler(client);
                connectionsList.add(handler);
                pool.execute(handler);
            }
        } catch (IOException e) {
            try {
                shutDown();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    public void broadcast(String message){
        for (ConnectionHandler ch : connectionsList){
            if (ch != null){
                ch.sendMessage(message);
            }
        }
    }
    public void shutDown() throws IOException {
        done = true;
        for (ConnectionHandler ch : connectionsList){
            ch.shutDown();
        }
        if (!server.isClosed()){
            server.close();
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
                    System.out.println(nickName + " connected!");
                    broadcast(nickName + " joined the chat!");
                }
                String message;
                while ((message = in.readLine()) != null){
                    if (message.startsWith("/nick")){
                        String[] messageSplit = message.split(" ", 2);
                        if (messageSplit.length == 2){
                            broadcast(nickName + " renamed himself to:" + messageSplit[1]);
                            System.out.println(nickName + "renamed themself to: " + messageSplit[1]);
                            nickName = messageSplit[1];
                            out.println("Successfully changed nickname to: " + nickName);
                        } else {
                            out.println("no nickname :|");
                        }
                        // TODO: handle changing nickname
                    } else if (message.startsWith("/quit")) {
                        broadcast(nickName +  "left the chat");
                        System.out.println(nickName + " left the chat");
                        shutDown();
                    } else {
                        broadcast(nickName + ": " + message);
                    }
                }
            } catch (Exception e){
                shutDown();
            }
        }
        public void sendMessage(String message){
            out.println(message);
        }

        public void shutDown() {
            try {
                done = true;
                pool.shutdown();
                in.close();
                out.close();
                if (!client.isClosed()){
                    client.close();;
                }
            } catch (IOException e){
                // TODO: you know what to do
            }
        }
    }
    public static void main(String[] args){
        Server server = new Server();
        server.run();
    }
}
