package main.java.apit.uog.model;

import java.util.ArrayList;
import java.util.List;

public class GameLoop implements Runnable {

    private volatile boolean running = true;
    private GameController gameController;
    private List<Player> playersInRound;
    private int activePlayerIndex = 0;

    public GameLoop(GameController gameController) {
        this.gameController = gameController;
        playersInRound = new ArrayList<>(gameController.getGameState().getActivePlayers().values());
    }

    public void terminate() {
        running = false;
    }


    @Override
    public void run() {
        gameController.setActivePlayer(playersInRound.get(activePlayerIndex));
        while (activePlayerIndex < playersInRound.size()) {
                playRound(playersInRound.get(activePlayerIndex));

                try{
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

        }
        }


    public void playRound(Player player) {

        gameController.setActivePlayer(player);


        if (player.totalOfHand() == 21) {
            player.setWinner(true);
            activePlayerIndex++;
        }
        if (player.totalOfHand() > 21) {
            gameController.playerExceeded21(player);
            activePlayerIndex++;
        }

    }
}

