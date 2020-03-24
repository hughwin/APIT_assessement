package main.java.apit.uog.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Dealer implements Serializable {

    private Deck deck;
    private HashMap<Integer, Player> activePlayers;
    private ArrayList<Card> hand;

    public Deck getDeck() {
        return deck;
    }

    public Dealer(HashMap<Integer, Player> activePlayers) {
        this.activePlayers = activePlayers;
        this.hand = new ArrayList<>();
        deck = new Deck();
        deck.shuffle();
    }

    public void dealCardsToPlayers() {
        for (int i = 0; i < 2; i++) {
            hand.add(deck.getTopCard());
            activePlayers.forEach((key, value) -> value.dealCard(deck.getTopCard()));
        }
    }

    public Card hit() {
        return deck.getTopCard();
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public int getDealerScore() {
        int total = 0;
        for(Card card : hand){
            total += card.getValue();
        }
        return total;
    }

    public void returnCardsToDeck(){
        deck.addCardsBackToDeck(hand);
    }

}



