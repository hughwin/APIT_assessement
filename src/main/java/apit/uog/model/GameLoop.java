package main.java.apit.uog.model;

import java.util.ArrayList;
import java.util.List;

public class GameLoop implements Runnable {

    private volatile boolean running = true;
    private GameController gameController;

    public GameLoop(GameController gameController) {
        this.gameController = gameController;
    }

    public void terminate() { running = false; }


    @Override
    public void run() {
        while (true) {
            if (checkPlayersStanding()) {
                terminate();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public boolean checkPlayersStanding() {
        List<Player> list = new ArrayList<>(gameController.getGameState().getActivePlayers().values());
        System.out.println("Working!");

        for (Player player : list) {
            if(player.totalOfHand() > 21){
            }
            if (!player.isStanding()) {
                return false;
            }
        }
        return true;
    }
}
