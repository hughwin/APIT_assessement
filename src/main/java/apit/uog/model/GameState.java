package main.java.apit.uog.model;

import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.ArrayList;

public class GameState implements Serializable {

    private final Dealer dealer;
    private final ArrayList<Player> activePlayers;
    private PropertyChangeSupport propertyChangeSupport;

    public GameState(){
        this.dealer = new Dealer();
        this.activePlayers = new ArrayList<Player>();
        propertyChangeSupport = new PropertyChangeSupport(activePlayers);
    }

    public void addPlayer(Player player) {
        propertyChangeSupport.firePropertyChange("activePlayers", activePlayers,  activePlayers.add(player));
    }

    public ArrayList<Player> getActivePlayers() {
        return activePlayers;
    }

    public PropertyChangeSupport getPropertyChangeSupport() {
        return propertyChangeSupport;
    }
}
