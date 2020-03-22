package main.java.apit.uog.controller;


import main.java.apit.uog.model.GameState;
import main.java.apit.uog.model.Player;
import main.java.apit.uog.view.AppView;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class AppController {

    private AppView appView;
    private Socket server;
    private int PORT = 8888;
    private int sessionID;
    private String LOCALHOST = "127.0.0.1";
    private ObjectOutputStream objectOutputStream;

    private GameState gameState;


    public AppController() {
        appView = new AppView(this);
    }


    public void startGame(String name) {
        try {
            appView.setPageView("game");
            System.out.println("Starting client!");
            server = new Socket(LOCALHOST, PORT);
            objectOutputStream = new ObjectOutputStream(server.getOutputStream());
            objectOutputStream.writeObject(new Player(name));
            objectOutputStream.reset();
            ReadWorker rw = new ReadWorker(server, this);
            rw.execute();
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

        @Override
        protected Void doInBackground() throws Exception {
            System.out.println("Started swing worker!");
            Object input;
            while ((input = inputStream.readObject()) != null) {

                if (Integer.class.isInstance(input)) {
                    sessionID = (int) input;
                } else {

                    gameState = (GameState) input;
                    appView.getGamePage().getOutPutPanel().removeAll();

                    if (!gameState.getDealer().getHand().isEmpty()) {
                        appView.getGamePage().setDealerArea(gameState.getDealer().getHand());
                    }
                    if (gameState.getActivePlayers().get(sessionID).isStanding()) {
                        appView.getGamePage().enableRoundInProgressButtons(false);
                    } else {
                        if (gameState.isRoundInProgress()) {
                            appView.getGamePage().enableRoundInProgressButtons(true);
                        }
                        gameState.getActivePlayers().forEach((key, value) -> appView.getGamePage().addPlayerToView(value));
                    }

                }
            }
            return null;
        }
    }
}
