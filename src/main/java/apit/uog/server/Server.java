package main.java.apit.uog.server;

import main.java.apit.uog.model.GameController;
import main.java.apit.uog.model.GameState;
import main.java.apit.uog.model.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

// Server class 
public class Server implements Runnable {

    private int PORT;
    private ServerSocket server;
    private Vector<ClientRunner> clients = new Vector<>(); // Thread safe. Could be changed to ArrayList
    private GameController gameController;
    private int id;

    public Server(int port) {
        PORT = port;
        gameController = new GameController(this);
        try {
            server = new ServerSocket(PORT); // New Server socket on PORT 8888 if not already in use.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void addPlayer(int id, Player player) {
        gameController.getGameState().addPlayer(id, player);
    }

    public synchronized void removePlayer(int id){ gameController.getGameState().removePlayer(id); }

    public synchronized void removeClient(int clientId){
        for (ClientRunner clientRunner : clients){
            if (clientRunner.getID() == clientId){
                clients.remove(clientRunner);
            }
        }
    }

    public void sendGameState(GameState gameState) {
        for (ClientRunner client : clients) {
            client.updateGameState(gameState);
        }
    }


    @Override
    public void run() {
        while (true) {
            Socket clientSocket;
            try {
                clientSocket = server.accept();
                System.out.println("New client connected!");
                ClientRunner client = new ClientRunner(clientSocket, this, id);
                clients.add(client);
                id++;
                new Thread(client).start();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

}

