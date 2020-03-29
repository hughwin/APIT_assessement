import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class's function is to act as an containing class for Player classes in
 * activePlayers, the Dealer class, and the classes contained within the Dealer class (Card, Deck).
 * This allows all the information about the state of the game to be returned neatly to the clients.
 */
public class GameState implements Serializable {

    private final Dealer dealer;
    private ConcurrentHashMap<Integer, Player> activePlayers;
    private boolean roundInProgress = false;
    private boolean roundOver;
    private Player activePlayer;


    public GameState() {
        this.activePlayers = new ConcurrentHashMap<>();
        this.dealer = new Dealer(activePlayers);
    }

    public boolean isRoundInProgress() {
        return roundInProgress;
    }

    public void setRoundInProgress(Boolean inProgress) {
        roundInProgress = inProgress;
    }

    public ConcurrentHashMap<Integer, Player> getActivePlayers() {
        return activePlayers;
    }

    public Dealer getDealer() {
        return dealer;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(Player activePlayer) {
        this.activePlayer = activePlayer;
    }

    public boolean isRoundOver() {
        return roundOver;
    }

    public void setRoundOver(boolean roundOver) {
        this.roundOver = roundOver;
    }
}
