import java.util.Vector;

public class GameLoop implements Runnable {

    public static final int TWENTY_ONE = 21; // constant
    private final GameController gameController;
    private volatile boolean running = true;
    private final Vector<Player> playersInRound;
    private int activePlayerIndex = 0;

    public GameLoop(GameController gameController) {
        this.gameController = gameController;
        playersInRound = new Vector<>(gameController.getGameState().getActivePlayers().values()); // Different vectors for players in round and ActivePlayers
        // designed as to not interrupt or cause issues for the current round.
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
        gameController.setActivePlayer(playersInRound.get(activePlayerIndex));
        gameController.sendGameState();
    }

    @Override
    public void run() {
        while (running) {
            gameController.setActivePlayer(playersInRound.get(activePlayerIndex));
            while (activePlayerIndex < playersInRound.size()) {
                playRound(playersInRound.get(activePlayerIndex));
                try {
                    Thread.sleep(350); // Stops the swing interface madly flickering.
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            int dealerScore = gameController.getGameState().getDealer().getDealerScore();

            if (dealerScore < 16) {
                gameController.getGameState().getDealer().takeExtraCard();
                // Adds an extra card to the dealer's hand if score is under 16.
            }


            for (Player player : playersInRound) {
                if (!player.isBust()) {
                    if (dealerScore > TWENTY_ONE && player.totalOfHand() < TWENTY_ONE) {
                        gameController.setWinner(player);
                    } else if (dealerScore == player.totalOfHand()) {
                        player.returnBet();
                        gameController.setWinner(player); // Not exactly "winning", but they do not lose their stake.
                    } else if (dealerScore < player.totalOfHand() || dealerScore > TWENTY_ONE) {
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


        if (player.totalOfHand() == TWENTY_ONE) {
            player.setWinner(true);
            activePlayerIndex++;
        }
        if (player.totalOfHand() > TWENTY_ONE) {
            gameController.playerExceeded21(player);
            activePlayerIndex++;
        }
        if (player.isStanding()) {
            player.setStanding(true);
            activePlayerIndex++;
        }
    }
}

