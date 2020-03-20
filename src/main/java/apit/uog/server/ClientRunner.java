package main.java.apit.uog.server;// Java implementation of  Server side
// It contains two classes : Server and ClientHandler 
// Save file as Server.java 

import main.java.apit.uog.model.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

// Server class 
public class ClientRunner implements Runnable {

    private Socket s = null;
    private Server parent = null;
    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;
    private Player player;


    public ClientRunner(Socket s, Server parent) {
        this.s = s;
        this.parent = parent;
        try {
            outputStream = new ObjectOutputStream(this.s.getOutputStream());
            inputStream = new ObjectInputStream((this.s.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Player player = new Player("Player 1");
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


