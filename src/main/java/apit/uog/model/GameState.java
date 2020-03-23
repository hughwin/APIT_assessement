package main.java.apit.uog.model;

import java.io.Serializable;
import java.util.HashMap;

/**
 * This class's function is to act as an encapsulating class for Player classes in
 * activePlayers, and the Dealer class. This allows all the information about the state of the
 * game to be returned neatly to the clients.
 */
public class GameState implements Serializable {

    private final Dealer dealer;
    private final HashMap<Integer, Player> activePlayers;
    private boolean roundInProgress = false;
    private Player activePlayer;


    public GameState() {
        this.activePlayers = new HashMap<>();
        this.dealer = new Dealer(activePlayers);
    }

    public boolean isRoundInProgress() {
        return roundInProgress;
    }

    public void setRoundInProgress(Boolean inProgress) {
        roundInProgress = inProgress;
    }

    public HashMap<Integer, Player> getActivePlayers() {
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

}
