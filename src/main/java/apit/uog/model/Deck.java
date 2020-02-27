package main.java.apit.uog.model;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    private ArrayList<Card> cards;


    public enum Suit {
        SPADES,
        HEARTS,
        DIAMONDS,
        CLUBS
    }

    public enum Rank {
        TWO,
        THREE,
        FOUR,
        FIVE,
        SIX,
        SEVEN,
        EIGHT,
        NINE,
        TEN,
        JACK,
        QUEEN,
        KING,
        ACE
    }

    public Deck(){
        cards = new ArrayList<>();
        for (Suit s : Suit.values()){
            for (Rank r : Rank.values()){
                Card current = new Card(s, r);
                cards.add(current);
            }
        }
    }

    protected void shuffle(){
        Collections.shuffle(cards);
    }

    protected Card getTopCard(){ // should never be null
        Card cardToBeReturned = cards.get(0);
        cards.remove(0);
        return cardToBeReturned;
    }


}
