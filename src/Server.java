import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

// Server class 
public class Server implements Runnable {

    private final int PORT;
    private final GameController gameController;
    private ServerSocket server;
    private Vector<ClientRunner> clients = new Vector<>(); // Thread safer. Could be changed to ArrayList
    private int id;
    private Thread readyThread;

    /**
     * Server listens for new connections from clients,
     * and the controls creation of ClientRunner instances to handle the connections.
     *
     * @param port that the server runs on.
     */
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

    public static void main(String[] args) {
        Thread t = new Thread(new Server(8888)); // Connection will immediately fail if server is already running.
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void addPlayer(int id, Player player) {
        clients.get(id).updateLocalSessionId(id);
        gameController.addPlayer(id, player);
    }

    public synchronized void removePlayer(int id) {
        clients.removeIf(clientRunner -> clientRunner.getID() == id);
        gameController.removePlayer(id);
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
                clientSocket = server.accept(); // Accepts new connections
                System.out.println("New client connected!");
                ClientRunner client = new ClientRunner(clientSocket, this, id);
                clients.add(client); // Adds clients to the Vector containing the clients
                id++; // Increments id by one.
                new Thread(client).start();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

}

