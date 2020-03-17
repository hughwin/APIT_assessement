package main.java.apit.uog.model;

import java.util.ArrayList;

public class Dealer {

    private ArrayList<Player> players;
    private Deck deck;
    private int NUMBER_OF_PLAYERS;

    public Dealer(int numberOfPlayers) {
        players = new ArrayList<>();
        generatePlayers(numberOfPlayers);
        deck = new Deck();
        deck.shuffle();
    }


    public void generatePlayers(int numberOfPlayers) {
        for (int i = 0; i < numberOfPlayers; i++) {
            players.add(new Player("Player " + (i + 1)));

        }
    }

    public void dealCardsToPlayers() {
        for (int i = 0; i < 2; i++) {
            for (Player p : players) {
                p.dealCard(deck.getTopCard());
                System.out.println(p.getHand());
            }
        }
    }

    public void collectCards() {
        for (Player p : players) {
            ArrayList<Card> collectedCards = p.returnHandToDealer();
            deck.addCardsBackToDeck(collectedCards);
        }
    }
}



