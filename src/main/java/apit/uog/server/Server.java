package main.java.apit.uog.server;

import main.java.apit.uog.model.GameLogic;
import main.java.apit.uog.model.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

// Server class 
public class Server implements Runnable {

    private int PORT;
    private ServerSocket server;
    private Vector<ClientRunner> clients = new Vector<>(); // Thread safe. Could be changed to ArrayList
    private GameLogic gameLogic;

    public Server(int port) {
        PORT = port;
        gameLogic = new GameLogic(this);
        try {
            server = new ServerSocket(PORT); // New Server socket on PORT 8888 if not already in use.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addPlayer(Player player) {
        gameLogic.addPlayer(player);
    }

    public void updatePlayers(ArrayList<Player> players) {
        for (ClientRunner client : clients) {
            client.updatePlayers(players);
        }
    }

    public ArrayList<Player> getActivePlayers() {
        return gameLogic.getActivePlayer();
    }

    @Override
    public void run() {
        while (true) {
            Socket clientSocket;
            try {
                clientSocket = server.accept();
                System.out.println("New client connected!");
                ClientRunner client = new ClientRunner(clientSocket, this);
                clients.add(client);
                new Thread(client).start();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}

