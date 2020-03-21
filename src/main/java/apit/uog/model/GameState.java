package main.java.apit.uog.model;

import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.HashMap;

import static java.lang.System.*;

public class GameState implements Serializable {

    private final Dealer dealer;
    private final HashMap<Integer, Player> activePlayers;
    private PropertyChangeSupport propertyChangeSupport;
    private boolean roundInProgress = false;

    public GameState() {
        this.activePlayers = new HashMap<>();
        this.dealer = new Dealer(activePlayers);
        propertyChangeSupport = new PropertyChangeSupport(activePlayers);
    }

    public void startGame(){
        dealer.dealCardsToPlayers();
        roundInProgress = true;
        propertyChangeSupport.firePropertyChange("activePlayers", null, activePlayers);

    }

    public void addPlayer(int id, Player player) {
        propertyChangeSupport.firePropertyChange("activePlayers", activePlayers, activePlayers.put(id, player));
    }

    public void removePlayer(int id) {
        propertyChangeSupport.firePropertyChange("activePlayers", activePlayers, activePlayers.remove(id));
        activePlayers.forEach((key, value) -> out.println(key + " " + value.getName()));
    }

    public void setPlayerReady(int id, boolean ready){
        activePlayers.get(id).setReady(ready);
        propertyChangeSupport.firePropertyChange("player", false, true);
    }

    public boolean isRoundInProgress() {
        return roundInProgress;
    }

    public HashMap<Integer, Player> getActivePlayers() {
        return activePlayers;
    }

    public PropertyChangeSupport getPropertyChangeSupport() {
        return propertyChangeSupport;
    }

    public Dealer getDealer() {
        return dealer;
    }
}
