package main.java.apit.uog.server;// Java implementation of  Server side
// It contains two classes : Server and ClientHandler 
// Save file as Server.java 

import main.java.apit.uog.model.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

// Server class 
public class ClientRunner implements Runnable {

    private Socket s;
    private Server parent;
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
    }

    public void updatePlayers(ArrayList<Player> players) {
        try {
            outputStream.writeObject(players);
            outputStream.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        // receive changes
        try {
            Object input;
            while ((input = inputStream.readObject()) != null) {
                if (input instanceof Player) {
                    Player player = (Player) input;
                    System.out.println(player.getName() + " hello!");
                    this.parent.addPlayer(player);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


