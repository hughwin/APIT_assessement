package main.java.apit.uog.controller;


import main.java.apit.uog.model.GameState;
import main.java.apit.uog.model.Player;
import main.java.apit.uog.view.AppView;
import main.java.apit.uog.view.PlayerView;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class AppController {

    private AppView appView;
    private Socket server;
    private int PORT = 8888;
    private int sessionID = -1;
    private String LOCALHOST = "127.0.0.1";
    private ObjectOutputStream objectOutputStream;
    private GameState gameState;
    private int numberOfPlayers;


    public AppController() {
        appView = new AppView(this);
    }


    public void startGame(String name) {
        try {
            appView.setPageView("game");
            System.out.println("Starting client!");
            server = new Socket(LOCALHOST, PORT);

            ReadWorker rw = new ReadWorker(server, this);
            rw.execute();

            objectOutputStream = new ObjectOutputStream(server.getOutputStream());
            objectOutputStream.writeObject(new Player(name));
            objectOutputStream.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void quitGame() {
        try {
            objectOutputStream.writeObject("quit");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stand() {
        try {
            objectOutputStream.writeObject("stand");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hit() {
        try {
            objectOutputStream.writeObject("hit");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void placeBet(String betAmount) {

        boolean isInt = true;

        try {
            Integer.parseInt(betAmount);
        } catch (NumberFormatException e) {
            isInt = false;
        }

        if (!isInt) {
            appView.getGamePage().badBetPlaced();
            return;
        }
        int betInt = Integer.parseInt(betAmount);
        if (betInt < 1 || betInt > gameState.getActivePlayers().get(sessionID).getBalance()) {
            appView.getGamePage().badBetPlaced();
        } else {
            try {
                objectOutputStream.writeObject("bet " + betAmount);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class ReadWorker extends SwingWorker<Void, Void> {

        private final AppController parent;
        private Socket socket;
        private ObjectInputStream inputStream = null;

        public ReadWorker(Socket socket, AppController parent) {
            this.socket = socket;
            this.parent = parent;
            try {
                inputStream = new ObjectInputStream(this.socket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void changePlayerView() {
            for (PlayerView playerView : appView.getGamePage().getPlayerViews()) {

                playerView.setBalanceLabelText(gameState.getActivePlayers().get(playerView.getPlayerID()).getBalance() + "");
                playerView.setCardsLabelText(gameState.getActivePlayers().get(playerView.getPlayerID()).getHand().toString());

                if (gameState.getActivePlayers().get(playerView.getPlayerID()).isReady()) {
                    playerView.setReadyLabelText("Ready!");
                } else {
                    playerView.setReadyLabelText("");
                }

                if (gameState.getActivePlayers().get(playerView.getPlayerID()).isStanding()) {
                    playerView.setReadyLabelText(gameState.getActivePlayers().get(playerView.getPlayerID()).getName() +
                            " is standing with a score of " + gameState.getActivePlayers().get(playerView.getPlayerID()).totalOfHand());
                }

                if (gameState.getActivePlayers().get(playerView.getPlayerID()).isBust()) {
                    playerView.setReadyLabelText(gameState.getActivePlayers().get(playerView.getPlayerID()).getName() +
                            " is bust with a score of " + gameState.getActivePlayers().get(playerView.getPlayerID()).totalOfHand());
                }

                if (gameState.getActivePlayers().get(playerView.getPlayerID()).isWinner()) {
                    playerView.setReadyLabelText(gameState.getActivePlayers().get(playerView.getPlayerID()).getName() +
                            " has won with a score of " + gameState.getActivePlayers().get(playerView.getPlayerID()).totalOfHand());
                }
                if (gameState.isRoundOver() && !gameState.getActivePlayers().get(playerView.getPlayerID()).isWinner()){
                    playerView.setReadyLabelText(gameState.getActivePlayers().get(playerView.getPlayerID()).getName() +
                            " has lost their stake with a score of " + gameState.getActivePlayers().get(playerView.getPlayerID()).totalOfHand());
                }

            }

        }

        public void setViewRoundOver(){
            appView.getGamePage().setDealerRoundOver(gameState.getDealer());
            appView.getGamePage().getBetBeforeRoundButton().setEnabled(true);
            appView.getGamePage().enableRoundInProgressButtons(false);

        }


        @Override
        protected Void doInBackground() throws Exception {
            System.out.println("Started swing worker!");
            Object input;
            while ((input = inputStream.readObject()) != null) {
                if (input instanceof Integer) {
                    sessionID = (int) input;
                } else {

                    gameState = (GameState) input;


                    // Create new player view if a player joins
                    if (gameState.getActivePlayers().size() != numberOfPlayers) {
                        numberOfPlayers = gameState.getActivePlayers().size();
                        appView.getGamePage().getOutPutPanel().removeAll();
                        gameState.getActivePlayers().forEach((key, player) -> appView.getGamePage().addPlayerToView(player));
                    }

                    // If the active player is not null, set it to the active player and show the player's score.
                    if (gameState.getActivePlayer() != null) {
                        appView.getGamePage().setScoreLabel(gameState.getActivePlayers().get(sessionID).totalOfHand());
                        appView.getGamePage().setPlayerTurnLabelText(gameState.getActivePlayer().getName());

                        // If it is the player's go, enable the round in progress buttons
                        if (gameState.getActivePlayer().getID() == sessionID) {
                            appView.getGamePage().enableRoundInProgressButtons(true);
                        }
                        else {
                            appView.getGamePage().enableRoundInProgressButtons(false);
                        }
                    }

                    // Show the dealer's hand if they have cards
                    if (!gameState.getDealer().getHand().isEmpty()) {

                        //Show only first card if game is in progress.
                        if (gameState.isRoundInProgress()) {
                            appView.getGamePage().setFirstCard(gameState.getDealer().getHand().get(0));
                            appView.getGamePage().setDealerScore(gameState.getDealer().getHand().get(0).getValue());
                        }
                        if (gameState.isRoundOver()) {
                            appView.getGamePage().setDealerRoundOver(gameState.getDealer());
                        }
                    }

                    // If the round is over, reset the game
                    if (gameState.isRoundOver()) {
                        setViewRoundOver();
                    } else {
                        changePlayerView();
                    }
                }
                // Refreshes the gamepage.
                appView.getGamePage().revalidateAndRepaint();
            }
            return null;

        }
    }

    public static void main(String[] args) {
        new AppController();
    }
}


