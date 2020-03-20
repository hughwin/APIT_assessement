package main.java.apit.uog.server;// Java implementation of  Server side
// It contains two classes : Server and ClientHandler 
// Save file as Server.java 

import main.java.apit.uog.model.GameLogic;
import main.java.apit.uog.model.Player;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

// Server class 
public class Server implements Runnable {

    private int PORT;
    private ServerSocket server;
    private Vector<ClientRunner> clients = new Vector<ClientRunner>(); // Thread safe. Could be changed to ArrayList

    public Server(int port) {
        PORT = port;
        try {
            server = new ServerSocket(PORT); // New Server socket on PORT 8888 if not already in use.
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

