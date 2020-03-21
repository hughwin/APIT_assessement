package main.java.apit.uog.model;

import main.java.apit.uog.server.Server;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class GameController implements PropertyChangeListener {


    private final GameState gameState;
    private Server server;


    public GameController(Server server) {
        this.server = server;
        this.gameState = new GameState();
        gameState.getPropertyChangeSupport().addPropertyChangeListener(this);
    }

    public GameState getGameState() {
        return gameState;
    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        server.sendGameState(gameState);
    }
}
