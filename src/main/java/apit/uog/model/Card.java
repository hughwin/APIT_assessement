package main.java.apit.uog.model;

public class Card {

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
