package main.java.apit.uog.model;

import java.io.Serializable;

public class Card implements Serializable {

    private Deck.Suit suit;
    private Deck.Rank rank;

    public Card(Deck.Suit suit, Deck.Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "Card{" +
                "suit=" + suit +
                ", rank=" + rank +
                '}';
    }
}
