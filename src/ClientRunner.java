import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


/**
 * The ClientRunner class handles the connecting clients,
 * and controls the input and output of data to each client
 */
public class ClientRunner implements Runnable {

    private final int ID;
    private final Socket s;
    private final Server parent;
    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;
    private Player player;


    /**
     *
     * @param s Socket
     * @param parent Server class instance that creates the ClientRunner
     * @param id unique id of the individual ClientRunner instance
     */
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

    /**
     * Writes to the outputStream the gameState object, which effectively is the
     * current status of the game in progress.
     * @param gameState - current gameState (representation) of the game.
     */
    public void updateGameState(GameState gameState) {
        try {
            outputStream.writeObject(gameState);
            outputStream.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends the unique id of the ClientRunner / Player back to the client.
     * By maintaining a sessionID in AppController, the game is able to extract
     * pertinent information from that instance from the returned GameState data.
     * @param id of the client.
     */
    public void updateLocalSessionId(int id) {
        try {
            outputStream.writeObject(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    /**
     * Implementation of the Runnable interface. Continuously listens to the
     * ClientRunner's inputStream for new data being sent to the client.
     */
    public void run() {
        try {
            Object input;
            while ((input = inputStream.readObject()) != null) {
                if (input instanceof Player) {
                    player = (Player) input;
                    System.out.println("Hello " + player.getName() + "!");
                    player.setID(ID); //Sets the id of the player to the id of the ClientRunner
                    parent.addPlayer(ID, player); // adds a new Player to the list of players
                }
                if (input instanceof String) {
                    String command = (String) input;
                    String[] commandArray = command.split(" "); // Splits the String into an based on spaces.

                    if (commandArray[0].equals("quit")) {
                        this.parent.removePlayer(ID);
                        break;
                    }

                    if (commandArray[0].equals("bet")) {
                        this.parent.placeBet(ID, Integer.parseInt(commandArray[1]));
                    }

                    if (commandArray[0].equals("hit")) {
                        this.parent.hit(ID);
                    }

                    if (commandArray[0].equals("stand")) {
                        this.parent.setPlayerStanding(ID);
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


