package main.java.apit.uog.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Dealer implements Serializable {

    private ArrayList<Player> players;
    private Deck deck;

    public Dealer() {
        players = new ArrayList<>();
        deck = new Deck();
        deck.shuffle();
    }


    public void dealCardsToPlayers() {
        for (int i = 0; i < 2; i++) {
            for (Player p : players) {
                p.dealCard(deck.getTopCard());
                System.out.println(p.getHand());
            }
        }
    }

    public void collectCards() {
        for (Player p : players) {
            ArrayList<Card> collectedCards = p.returnHandToDealer();
            deck.addCardsBackToDeck(collectedCards);
        }
    }
}



