import java.util.ArrayList;
import java.util.List;


/**
 * As the game's logic is held server side, the GameController Class is responsible for manipulating the model
 * in response to instructions from the client, and then returning the updated information to the client. apart from sendGameState,
 * none of the methods are synchronized. This is because the methods that call them are synchronised in Server or are otherwise thread
 * safe in their implementation (using data structures that prevent concurrent modification etc).
 */
public class GameController implements Runnable {

    private final GameState gameState;
    private final Server server;
    private volatile boolean running = true;
    private GameLoop gameLoop;


    public GameController(Server server) {
        this.server = server;
        this.gameState = new GameState();
    }

    public GameState getGameState() {
        return gameState;
    }

    public synchronized void sendGameState() {
        server.sendGameState(gameState);
    }

    public void terminate() {
        running = false;
    }

    /**
     * Deals the cards and starts the game. The startGame method also creates and starts a thread (gameLoopThread) that
     * controls the game logic (whose turn it is etc.).
     */
    public void startGame() {
        gameState.getDealer().dealCardsToPlayers();
        gameState.setRoundOver(false);
        gameState.setRoundInProgress(true);
        gameLoop = new GameLoop(this);
        Thread gameLoopThread = new Thread(gameLoop);
        gameLoopThread.start();
        sendGameState();
    }


    public void addPlayer(int id, Player player) { // Can be called by more than one thread at the same time.
        gameState.getActivePlayers().put(id, player);           // Despite using a threadsafe data structure StackOverFlow said it was best practice to make
        sendGameState();                                        // this syncronized as well.
    }

    public void removePlayer(int id) {
        gameState.getActivePlayers().remove(id);
        gameLoop.removePlayerFromRound(id);
        sendGameState();
    }

    public void endRound() {
        gameState.setRoundOver(true);
        gameState.setActivePlayer(null);
        gameState.setRoundInProgress(false);
        System.err.println(gameState.isRoundOver());

        sendGameState();

        gameState.getActivePlayers().forEach((key, player) -> {  // Every player in active player is returned to their original state, effectively resetting the game for the next hand
            player.returnHandToDealer();
            player.setReady(false);
            player.setStanding(false);
            player.setWinner(false);
            player.setBust(false);
        });

        gameState.getDealer().returnCardsToDeck();
    }

    public void setPlayerReady(int id, boolean ready) {
        gameState.getActivePlayers().get(id).setReady(ready);
        sendGameState();
    }

    public void placeBet(int id, int betAmount) {
        gameState.getActivePlayers().get(id).betOnRound(betAmount);
        if(gameState.isRoundOver()){
            gameState.setRoundOver(false);
        }
        gameState.getActivePlayers().get(id).setReady(true);
        sendGameState();
    }

    public void hit(int id) {
        gameState.getActivePlayers().get(id).dealCard(gameState.getDealer().hit());
        sendGameState();
    }

    public void setPlayerStanding(int id) {
        gameState.getActivePlayers().get(id).setStanding(true);
        sendGameState();
    }

    public void setWinner(Player player) {
        player.setWinner(true);
        sendGameState();
    }

    public void playerExceeded21(Player player) {
        player.setBust(true);
        sendGameState();
    }


    public synchronized void setActivePlayer(Player player) {
        gameState.setActivePlayer(player);
        sendGameState();
    }


    @Override
    public void run() {
        while (running) {
            if (checkPlayersReady()) { // Iterates across all connected players to see whether they have place their bets and are ready to start a new round.
                startGame();
                gameState.getActivePlayers().forEach((key, value) -> setPlayerReady(key, false)); // Sets Players ready back to false.
            }
            try {
                Thread.sleep(500); // Checks every .5 second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Iterates across all connected players to see whether they have place their bets and are ready to start a new round.
     *
     * @return boolean. If all bets placed this is true, false otherwise.
     */
    public boolean checkPlayersReady() {
        List<Player> list = new ArrayList<>(gameState.getActivePlayers().values());
        if (list.size() < 2) {
            return false; // If there is only one player, the game will not start.
        }

        // TODO: Add error message informing the player they can't play unless there is more than 2 players.

        for (Player player : list) {
            if (!player.isReady()) {
                return false;
            }
        }
        return true;
    }

}
