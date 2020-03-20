package main.java.apit.uog.model;

import main.java.apit.uog.server.Server;

import java.io.Serializable;
import java.util.ArrayList;

public class GameLogic implements Serializable {

    private int round;
    private boolean roundComplete;
    private ArrayList<Player> activePlayer;
    private ArrayList<Player> eliminatedPlayers;
    private Server server;


    public GameLogic(Server server) {
        this.server = server;
        this.activePlayer = new ArrayList<>();
    }

    public ArrayList<Player> getActivePlayer() {
        return activePlayer;
    }

    public void addPlayer(Player player) {
        activePlayer.add(player);
        server.updatePlayers(getActivePlayer());
    }

}
