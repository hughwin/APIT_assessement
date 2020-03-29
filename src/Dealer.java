import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Dealer class is responsible for controlling the Cards (dealing them out / collecting them back in)
 */

public class Dealer implements Serializable {

    private final Deck deck;
    private ConcurrentHashMap<Integer, Player> activePlayers;
    private ArrayList<Card> hand;

    public Dealer(ConcurrentHashMap<Integer, Player> activePlayers) {
        this.activePlayers = activePlayers;
        this.hand = new ArrayList<>();
        deck = new Deck();
        deck.shuffle();
    }

    public Deck getDeck() {
        return deck;
    }

    public void dealCardsToPlayers() {
        for (int i = 0; i < 2; i++) { // Loops twice
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
        for (Card card : hand) {
            total += card.getValue();
        }
        return total;
    }

    public void returnCardsToDeck() {
        deck.addCardsBackToDeck(hand);
        hand.clear(); // Clears the dealer's hand.
    }

}



