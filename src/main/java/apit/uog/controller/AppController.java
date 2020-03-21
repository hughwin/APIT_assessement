package main.java.apit.uog.controller;

import main.java.apit.uog.model.GameController;
import main.java.apit.uog.model.GameState;
import main.java.apit.uog.model.Player;
import main.java.apit.uog.view.AppView;
import main.java.apit.uog.view.GamePage;
import main.java.apit.uog.view.PlayerView;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;


public class AppController {

    private AppView appView;
    private Socket server;
    private int PORT = 8888;
    private String LOCALHOST = "127.0.0.1";
    private ObjectOutputStream objectOutputStream;

    private  GameState gameState;

    public AppController() {
        appView = new AppView(this);
    }


    public void startGame(String name) throws IOException {
        appView.setPageView("game");
        System.out.println("Starting client!");
        server = new Socket(LOCALHOST, PORT);
        objectOutputStream = new ObjectOutputStream(server.getOutputStream());
        objectOutputStream.writeObject(new Player(name));
        ReadWorker rw = new ReadWorker(server, this);
        rw.execute();
    }

    public GameState getGameState() {
        return gameState;
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
                gameState = (GameState) input;
                appView.getGamePage().removeAll();
                for (Player player : gameState.getActivePlayers()){
                    appView.getGamePage().addPlayerToView(player);
                }
                System.out.println("End of input");
            }
            return null;
        }
    }
}
