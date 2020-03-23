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
        gameController.setActivePlayer(playersInRound.get(0));
        while (!playersInRound.isEmpty()) {
                playRound(playersInRound.get(activePlayerIndex));
            System.out.println(playersInRound.get(activePlayerIndex).getName());
            }
        }


    public void playRound(Player player) {
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

