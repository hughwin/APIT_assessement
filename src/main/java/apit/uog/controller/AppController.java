package main.java.apit.uog.controller;

import main.java.apit.uog.model.GameLogic;
import main.java.apit.uog.model.Player;
import main.java.apit.uog.server.ClientRunner;
import main.java.apit.uog.server.Server;
import main.java.apit.uog.view.AppView;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;


public class AppController {

    private AppView appView;
    private GameLogic gameLogic;
    private Socket server;
    private int PORT = 8888;

    public AppController() {
        gameLogic = new GameLogic();
        appView = new AppView(this);
    }

    public void startGame(String name) throws IOException {
        appView.setPageView("game");
        try {
            Thread t = new Thread(new Server(PORT));
            t.start();
            System.out.print("Starting server and client!");
            server = new Socket("127.0.0.1",PORT);
        } catch (Exception e) {
            System.out.println("Starting new client!");
            server = new Socket("127.0.0.1",PORT);
        }


    }

    public ArrayList<Player> getPlayers(){
        return gameLogic.getActivePlayer();
    }

}
