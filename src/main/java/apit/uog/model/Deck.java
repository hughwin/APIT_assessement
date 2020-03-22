package main.java.apit.uog.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Deck implements Serializable {

    private ArrayList<Card> cards;


    public Deck() {
        cards = new ArrayList<>();
        for (Suit s : Suit.values()) {
            for (Rank r : Rank.values()) {
                Card current = new Card(s, r);
                cards.add(current);
            }
        }
    }

    protected void shuffle() {
        Collections.shuffle(cards);
    }

    protected Card getTopCard() { // should never be null
        Card cardToBeReturned = cards.get(0);
        cards.remove(0);
        return cardToBeReturned;
    }

    protected void addCardsBackToDeck(ArrayList<Card> returnedCards) {
        cards.addAll(returnedCards);
    }

    public enum Suit {
        SPADES,
        HEARTS,
        DIAMONDS,
        CLUBS
    }

    public enum Rank {
        TWO (2),
        THREE (3),
        FOUR (4),
        FIVE (5),
        SIX (6),
        SEVEN (7),
        EIGHT (8),
        NINE (9),
        TEN (10),
        JACK (10),
        QUEEN (10),
        KING (10),
        ACE (11);

        private final int value;

        private Rank(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }


}
