package main.java.apit.uog.model;

import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.HashMap;

import static java.lang.System.*;

public class GameState implements Serializable {

    private final Dealer dealer;
    private final HashMap<Integer, Player> activePlayers;
    private PropertyChangeSupport propertyChangeSupport;

    public GameState() {
        this.dealer = new Dealer();
        this.activePlayers = new HashMap<>();
        propertyChangeSupport = new PropertyChangeSupport(activePlayers);
    }

    public void addPlayer(int id, Player player) {
        propertyChangeSupport.firePropertyChange("activePlayers", activePlayers, activePlayers.put(id, player));
    }

    public void removePlayer(int id) {
        propertyChangeSupport.firePropertyChange("activePlayers", activePlayers, activePlayers.remove(id));
        activePlayers.forEach((key, value) -> out.println(key + " " + value.getName()));
    }

    public void setPlayerReady(int id){
        activePlayers.get(id).setReady(true);
        propertyChangeSupport.firePropertyChange("player", false, true);
    }

    public HashMap<Integer, Player> getActivePlayers() {
        return activePlayers;
    }

    public PropertyChangeSupport getPropertyChangeSupport() {
        return propertyChangeSupport;
    }
}
