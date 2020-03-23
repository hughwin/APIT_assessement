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
    private Thread readyThread;

    public Server(int port) {
        PORT = port;

        gameController = new GameController(this);
        readyThread = new Thread(gameController);
        readyThread.start();

        try {
            server = new ServerSocket(PORT); // New Server socket on PORT 8888 if not already in use.
        } catch (IOException e) {
            e.printStackTrace();
            gameController.terminate();
        }
    }

    public synchronized void addPlayer(int id, Player player) {
        gameController.addPlayer(id, player);
        clients.get(id).updateLocalSessionId(id);
    }

    public synchronized void removePlayer(int id) {
        gameController.removePlayer(id);
        clients.removeIf(clientRunner -> clientRunner.getID() == id);
    }

    public synchronized void setPlayerReady(int id) {
        gameController.setPlayerReady(id, true);
    }

    public synchronized void placeBet(int id, int betAmount) {
        gameController.placeBet(id, betAmount);
    }

    public synchronized void hit(int id) {
        gameController.hit(id);
    }

    public synchronized void setPlayerStanding(int id) {
        gameController.setPlayerStanding(id);
    }

    public synchronized void sendGameState(GameState gameState) {
        for (ClientRunner client : clients) {
            client.updateGameState(gameState);
        }
    }

    @SuppressWarnings("InfiniteLoopStatement")
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

