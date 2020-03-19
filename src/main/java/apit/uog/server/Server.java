package main.java.apit.uog.server;// Java implementation of  Server side
// It contains two classes : Server and ClientHandler 
// Save file as Server.java 

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

// Server class 
public class Server implements Runnable {

    private class ClientRunner implements Runnable {
        private Socket s = null;
        private Server parent = null;
        private ObjectInputStream inputStream = null;
        private ObjectOutputStream outputStream = null;


        public ClientRunner(Socket s, Server parent){
            this.s = s;
            this.parent = parent;
            try {
                outputStream = new ObjectOutputStream(this.s.getOutputStream());
                inputStream = new ObjectInputStream((this.s.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            // receieve changes
            try {
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private ServerSocket server;
    private Vector<ClientRunner> clients = new Vector<ClientRunner>(); // Thread safe. Could be changed to ArrayList

    public Server() {
        try {
            server = new ServerSocket(8888); // New Server socket on PORT 8888
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true){
            Socket clientSocket = null;
            try{
                clientSocket = server.accept();
                System.out.println("New client connected!");
                ClientRunner client = new ClientRunner(clientSocket, this);
                clients.add(client);
                new Thread(client).start();
            }
            catch (IOException e){
                e.printStackTrace();
            }

        }

    }
}

