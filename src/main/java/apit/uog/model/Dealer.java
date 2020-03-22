package main.java.apit.uog.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Dealer implements Serializable {

    private Deck deck;
    private HashMap<Integer, Player> activePlayers;
    private ArrayList<Card> hand;
    private Card firstCard;
    private Card secondCard;

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
        firstCard = hand.get(0);
        secondCard = hand.get(1);
    }

    public Card hit(){
        return deck.getTopCard();
    }

    public synchronized ArrayList<Card> getHand() {
        System.out.println(hand.toString());
        return hand;
    }

    //    public void collectCards() {
//        for (Player p : players) {
//            ArrayList<Card> collectedCards = p.returnHandToDealer();
//            deck.addCardsBackToDeck(collectedCards);
//        }
//    }
}



