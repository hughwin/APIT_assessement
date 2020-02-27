package main.java.apit.uog.model;

import java.util.ArrayList;

public class Player {

    private String name;
    private ArrayList<Card> hand;
    private  ArrayList<Card> placedCards;

    public Player(String name){
        this.name = name;
        this.hand = new ArrayList<>();
        this.placedCards = new ArrayList<>();
    }

    public void dealCard(Card c){
        this.hand.add(c);
    }

    public void resetHand(Card c){
        this.hand.clear();
    }

    public void placeCard(Card c){
        placedCards.add(c);
        hand.remove(hand.size()-1);
    }

    public String getName() {
        return name;
    }

    public String getHand() {
        return hand.toString();
    }
}
