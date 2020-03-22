package main.java.apit.uog.server;// Java implementation of  Server side
// It contains two classes : Server and ClientHandler 

import main.java.apit.uog.model.GameState;
import main.java.apit.uog.model.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


// Server class 
public class ClientRunner implements Runnable {

    private final int ID;
    private Socket s;
    private Server parent;
    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;
    private Player player;


    public ClientRunner(Socket s, Server parent, int id) {
        this.s = s;
        this.parent = parent;
        this.ID = id;
        try {
            outputStream = new ObjectOutputStream(this.s.getOutputStream());
            inputStream = new ObjectInputStream((this.s.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateGameState(GameState gameState) {
        try {
            outputStream.writeObject(gameState);
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
                    player = (Player) input;
                    System.out.println("Hello " + player.getName() + "!");
                    player.setID(ID);
                    this.parent.addPlayer(ID, player);
                }
                if (input instanceof String) {
                    String command = (String) input;
                    String[] commandArray = command.split(" ");

                    if (commandArray[0].equals("quit")) {
                        this.parent.removePlayer(ID);
                    }

                    if (commandArray[0].equals("bet")) {
                        this.parent.placeBet(ID, Integer.parseInt(commandArray[1]));
                        this.parent.setPlayerReady(ID);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getID() {
        return ID;
    }
}


