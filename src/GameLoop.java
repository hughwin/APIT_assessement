import java.util.Vector;

public class GameLoop implements Runnable {

    private final GameController gameController;
    private volatile boolean running = true;
    private Vector<Player> playersInRound;
    private int activePlayerIndex = 0;

    public GameLoop(GameController gameController) {
        this.gameController = gameController;
        playersInRound = new Vector<>(gameController.getGameState().getActivePlayers().values()); // Allows new clients to join whilst the game is in progress
        // and not interrupt or cause issues for the current round.
    }

    public void terminate() {
        running = false;
    } // Not used, but included for completion.

    public void removePlayerFromRound(int id) {
        Player toBeRemoved = null;
        for (Player player : playersInRound) {
            if (player.getID() == id) {
                toBeRemoved = player;
            }
        }
        playersInRound.remove(toBeRemoved);
        gameController.setActivePlayer(playersInRound.get(activePlayerIndex));
        gameController.sendGameState();
    }

    @Override
    public void run() {
        while (running) {
            gameController.setActivePlayer(playersInRound.get(activePlayerIndex));
            while (activePlayerIndex < playersInRound.size() || playersInRound.size() < 2) {
                playRound(playersInRound.get(activePlayerIndex));
                try {
                    Thread.sleep(350); // Stops the swing interface madly flickering.
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            int dealerScore = gameController.getGameState().getDealer().getDealerScore();

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
            terminate();
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
        if (player.isStanding()) {
            player.setStanding(true);
            activePlayerIndex++;
        }
    }
}

