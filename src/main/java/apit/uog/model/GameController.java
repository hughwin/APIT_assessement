package main.java.apit.uog.model;

import main.java.apit.uog.server.Server;

import java.util.ArrayList;
import java.util.List;

public class GameController implements Runnable {


    private final GameState gameState;
    private Server server;
    private volatile boolean running = true;
    private GameLoop gameLoop;


    public GameController(Server server) {
        this.server = server;
        this.gameState = new GameState();
    }

    public GameState getGameState() {
        return gameState;
    }

    public void sendGameState() {
        server.sendGameState(gameState);
    }

    public void terminate() {
        running = false;
    }

    public void startGame() {
        gameState.getDealer().dealCardsToPlayers();
        gameState.setRoundInProgress(true);
        gameLoop = new GameLoop(this);
        Thread t2 = new Thread(gameLoop);
        t2.start();
        sendGameState();
    }


    public void addPlayer(int id, Player player) {
        gameState.getActivePlayers().put(id, player);
        sendGameState();
    }

    public void removePlayer(int id) {
        gameState.getActivePlayers().remove(id);
        gameLoop.removePlayerFromRound(id);
        sendGameState();
    }

    public void endRound() {
        gameState.getActivePlayers().forEach((key, player) -> {
            player.returnHandToDealer();
            player.setReady(false);
            player.setStanding(false);
            player.setWinner(false);
            player.setBust(false);
            gameState.setRoundOver(true);
            sendGameState();
            gameState.setRoundOver(false);
        });

    }


    public void setPlayerReady(int id, boolean ready) {
        gameState.getActivePlayers().get(id).setReady(ready);
        sendGameState();
    }

    public void placeBet(int id, int betAmount) {
        gameState.getActivePlayers().get(id).betOnRound(betAmount);
        sendGameState();
    }

    public void hit(int id) {
        gameState.getActivePlayers().get(id).dealCard(gameState.getDealer().hit());
        sendGameState();
    }

    public void setPlayerStanding(int id) {
        gameState.getActivePlayers().get(id).setStanding(true);
        sendGameState();
    }

    public void setWinner(Player player){
        player.setWinner(true);
        sendGameState();
    }

    public void playerExceeded21(Player player) {
        player.setBust(true);
        sendGameState();
    }


    public void setActivePlayer(Player player) {
        gameState.setActivePlayer(player);
        sendGameState();
    }


    @Override
    public void run() {
        while (running) {
            if (checkPlayersReady()) {
                startGame();
                gameState.getActivePlayers().forEach((key, value) -> setPlayerReady(key, false));
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
