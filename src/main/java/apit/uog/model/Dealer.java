package main.java.apit.uog.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Dealer class is responsible for controlling the Cards (dealing them out / collecting them back in)
 */

public class Dealer implements Serializable {

    private Deck deck;
    private HashMap<Integer, Player> activePlayers;
    private ArrayList<Card> hand;

    public Dealer(HashMap<Integer, Player> activePlayers) {
        this.activePlayers = activePlayers;
        this.hand = new ArrayList<>();
        deck = new Deck();
        deck.shuffle();
    }

    public Deck getDeck() {
        return deck;
    }

    public void dealCardsToPlayers() {
        for (int i = 0; i < 2; i++) { // Deals two cards each
            hand.add(deck.getTopCard()); // Deals a card to the dealer.
            activePlayers.forEach((key, value) -> value.dealCard(deck.getTopCard())); // Deals a card to each player
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
        hand.clear(); // Clears the dealer's hand.
    }

}



