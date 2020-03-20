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
    }

    public void updatePlayers(Player playerInGame){
        try {
            outputStream.writeObject(playerInGame);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        // receive changes
        try {
            Object input = null;
            while((input = inputStream.readObject())!=null) {
                if (input instanceof Player) {
                    Player player = (Player)input;
                    System.out.println(player.getName() + " hello!");
                    this.parent.addPlayer(player);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


