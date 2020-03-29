import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {

    private final String name;
    private ArrayList<Card> hand;
    private int balance;
    private int bet;
    private int ID;
    private boolean ready = false;
    private boolean standing = false;
    private boolean bust = false;
    private boolean winner = false;

    /**
     * The Player class contains the object representation of each individual Player. The class has many
     * variables to indicate what the current state of the Player is in the game.
     */
    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.balance = 100; // Sets the initial balance to 100. High stakes!
    }

    public void dealCard(Card c) {
        hand.add(c);
    }

    public ArrayList<Card> returnHandToDealer() {
        ArrayList<Card> cardsToBeReturned = (ArrayList<Card>) hand.clone();
        hand.clear();
        return cardsToBeReturned;
    }

    public void betOnRound(int playerBet) {
        bet = playerBet;
        balance -= playerBet;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public int getBalance() {
        return balance;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public boolean isStanding() {
        return standing;
    }

    public void setStanding(boolean standing) {
        this.standing = standing;
    }

    public int totalOfHand() {
        int total = 0;
        for (Card card : hand) total += card.getValue();
        if (total > 21) {
            for (Card card : hand) {
                if (card.getValue() == Deck.Rank.ACE.getValue())
                    total -= 10; // If the value of the hand is > 21, the aces in the hand are worth 1.
            }
        }
        return total;
    }

    public boolean isBust() {
        return bust;
    }

    public void setBust(boolean bust) {
        this.bust = bust;
        bet = 0;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
        balance += (bet * 2);
        bet = 0;
    }

    public void returnBet(){
        balance += bet;
    }
}
