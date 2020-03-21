package main.java.apit.uog.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Dealer implements Serializable {

    private Deck deck;
    private HashMap<Integer, Player> activePlayers;

    public Dealer(HashMap<Integer, Player> activePlayers) {
        this.activePlayers = activePlayers;
        deck = new Deck();
        deck.shuffle();
    }

    public void dealCardsToPlayers() {
        for (int i = 0; i < 2; i++){
            activePlayers.forEach((key, value) -> {
                value.dealCard(deck.getTopCard());
            });
        }
    }

//    public void collectCards() {
//        for (Player p : players) {
//            ArrayList<Card> collectedCards = p.returnHandToDealer();
//            deck.addCardsBackToDeck(collectedCards);
//        }
//    }
}



