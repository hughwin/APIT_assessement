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

    public void removePlayerFromRound(int id) {
        Player toBeRemoved = null;
        for (Player player : playersInRound) {
            if (player.getID() == id) {
                toBeRemoved = player;
            }
        }
        playersInRound.remove(toBeRemoved);
    }

    @Override
    public void run() {
        gameController.setActivePlayer(playersInRound.get(activePlayerIndex));
        while (activePlayerIndex < playersInRound.size() || playersInRound.size() < 2) {
            playRound(playersInRound.get(activePlayerIndex));

            try {
                Thread.sleep(300); // Stops the swing interface madly flickering.
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int dealerScore = gameController.getGameState().getDealer().getDealerScore();
        System.out.println("Dealer score: " + dealerScore);

        for (Player player : playersInRound) {
            if (!player.isBust()) {
                if (dealerScore > 21 && player.totalOfHand() < 21) {
                    gameController.setWinner(player);
                } else if (dealerScore < player.totalOfHand()) {
                    gameController.setWinner(player);
                }
            }

        }

        gameController.endRound();

        try {
            Thread.sleep(5000); // Allows the results to be shown for 5 seconds before the game moves on.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        terminate();
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
        if (player.isStanding()) {
            player.setStanding(true);
            activePlayerIndex++;
        }

    }
}

