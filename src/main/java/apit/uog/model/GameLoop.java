package main.java.apit.uog.model;

import java.util.ArrayList;
import java.util.List;

public class GameLoop implements Runnable {

    private volatile boolean running = true;
    private GameController gameController;
    private List<Player> playersInRound;

    public GameLoop(GameController gameController) {
        this.gameController = gameController;
        playersInRound = new ArrayList<>(gameController.getGameState().getActivePlayers().values());
    }

    public void terminate() { running = false; }


    @Override
    public void run() {
        while (true) {


            checkPlayersStanding();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void checkPlayersStanding() {
        System.out.println("Working!");

        for (Player player : playersInRound) {
            if(player.totalOfHand() > 21){
                gameController.playerExceeded21(player);
                playersInRound.remove(player);
            }

        }
    }
}
