package main.java.apit.uog.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {

    private String name;
    private ArrayList<Card> hand;
    private ArrayList<Card> placedCards;
    private int balance;
    private boolean ready = false;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.placedCards = new ArrayList<>();
        this.balance = 100; // High stakes!
    }

    public void dealCard(Card c) {
        this.hand.add(c);
    }

    public ArrayList<Card> returnHandToDealer() {
        ArrayList<Card> cardsToBeReturned = (ArrayList<Card>) hand.clone();
        this.hand.clear();
        return cardsToBeReturned;
    }

    public void placeCard(Card c) {
        placedCards.add(c);
        hand.remove(hand.size() - 1);
    }

    public void betOnRound(int bet) {
        // TODO: Finish this method off.
    }

    public String getName() {
        return name;
    }

    public String getHand() {
        return hand.toString();
    }

    public int getBalance() {
        return balance;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public boolean isReady() {
        return ready;
    }
}
