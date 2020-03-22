package main.java.apit.uog.model;

import java.util.ArrayList;
import java.util.List;

public class StandCheck implements Runnable {

    private volatile boolean running = true;
    private GameState gameState;

    public StandCheck(GameState gameState) {
        this.gameState = gameState;
    }

    public void terminate() { running = false; }


    @Override
    public void run() {

        if (checkPlayersStanding()) {
            terminate();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public boolean checkPlayersStanding() {
        List<Player> list = new ArrayList<>(gameState.getActivePlayers().values());

        if (list.size() < 2) {
            return false;
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
