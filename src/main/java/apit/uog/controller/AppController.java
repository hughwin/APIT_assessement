package main.java.apit.uog.controller;

import main.java.apit.uog.model.GameLogic;
import main.java.apit.uog.model.Player;
import main.java.apit.uog.server.ClientRunner;
import main.java.apit.uog.server.Server;
import main.java.apit.uog.view.AppView;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;


public class AppController {

    private AppView appView;
    private GameLogic gameLogic;
    private Socket server;
    private int PORT = 8888;
    private String LOCALHOST = "127.0.0.1";
    private String NAME;
    private Player player;
    private ObjectOutputStream objectOutputStream;

    public AppController() {
        gameLogic = new GameLogic();
        appView = new AppView(this);
    }


    public void startGame(String name) throws IOException {
        appView.setPageView("game");
        System.out.print("Starting client!");
        server = new Socket(LOCALHOST, PORT);
        objectOutputStream = new ObjectOutputStream(server.getOutputStream());
        objectOutputStream.writeObject(new Player(name));
    }


    public ArrayList<Player> getPlayers() {
        return gameLogic.getActivePlayer();
    }

}
