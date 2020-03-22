package main.java.apit.uog.model;

import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.HashMap;

import static java.lang.System.out;

public class GameState implements Serializable {

    private final Dealer dealer;
    private final HashMap<Integer, Player> activePlayers;
    private boolean roundInProgress = false;

    public GameState() {
        this.activePlayers = new HashMap<>();
        this.dealer = new Dealer(activePlayers);
    }

    public boolean isRoundInProgress() {
        return roundInProgress;
    }

    public HashMap<Integer, Player> getActivePlayers() {
        return activePlayers;
    }

    public Dealer getDealer() {
        return dealer;
    }
}
