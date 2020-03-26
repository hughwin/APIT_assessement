package main.java.apit.uog.model;

import java.io.Serializable;

/**
 * Representation of the cards used in the game. Each card has a suit and a rank.
 */

public class Card implements Serializable {

    private Deck.Suit suit;
    private Deck.Rank rank;

    public Card(Deck.Suit suit, Deck.Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }

    public int getValue() {
        return rank.getValue();
    }

}
