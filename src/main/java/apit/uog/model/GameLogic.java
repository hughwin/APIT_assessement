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
        Player a = new Player("Player1");
        Player b = new Player("Player2");
        activePlayer.add(a);
        activePlayer.add(b);
    }

    public ArrayList<Player> getActivePlayer() {

        for(Player p : activePlayer){
            System.out.println(p.toString());
        }

        return activePlayer;
    }

}
