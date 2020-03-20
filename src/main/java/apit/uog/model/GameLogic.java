package main.java.apit.uog.model;

import java.io.Serializable;
import java.util.ArrayList;

public class GameLogic implements Serializable {

    private int round;
    private boolean roundComplete;
    private ArrayList<Player> activePlayer;
    private ArrayList<Player> eliminatedPlayers;

    public GameLogic() {
        this.activePlayer = new ArrayList<>();
    }

    public ArrayList<Player> getActivePlayer() {

        for(Player p : activePlayer){
            System.out.println(p.toString());
        }

        return activePlayer;
    }

    public void addPlayer(Player player){
        activePlayer.add(player);
    }

}
