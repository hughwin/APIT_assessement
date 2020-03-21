package main.java.apit.uog.model;

import main.java.apit.uog.server.Server;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;


public class GameController implements PropertyChangeListener, Runnable {


    private final GameState gameState;
    private Server server;
    private volatile boolean running = true;


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

    public void terminate() {
        running = false;
    }


    @Override
    public void run() {
        System.out.println("Starting!");
        while(running) {
            if (checkPlayersReady()) {
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public boolean checkPlayersReady() {
        List<Player> list = new ArrayList<>(gameState.getActivePlayers().values());
        for (Player player : list) {
            System.out.println(player.isReady());
            if (!player.isReady()) {
                return false;
            }
        }
        return true;
    }

}
