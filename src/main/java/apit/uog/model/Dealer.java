package main.java.apit.uog.model;

import java.util.ArrayList;

public class Dealer {

    private ArrayList<Player> players;

    public Dealer(int numberOfPlayers){
        players = new ArrayList<>();
    }

    public void generatePlayers(int numberOfPlayers){
        for(int i = 0 ; i < numberOfPlayers ; i++){
            new Player("Player " + (i +1 ));
        }
    }

    public void dealCardsToPlayers(){
    }

}
