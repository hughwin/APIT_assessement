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
        while(running) {
            if (checkPlayersReady()) {
                gameState.startGame();
                terminate();
                gameState.getActivePlayers().forEach((key,value) -> gameState.setPlayerReady(key,false));
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

        if(list.size() < 2){return false;}

        // TODO: Add error message informing the player they can't play unless there is more than 2 players.

        for (Player player : list) {
            if (!player.isReady()) {
                return false;
            }
        }
        return true;
    }

}
