package main.java.apit.uog;

import main.java.apit.uog.model.Dealer;
import main.java.apit.uog.model.Deck;
import main.java.apit.uog.view.AppView;

public class Main {
    public static void main(String[] args) {
        new AppView();
        Dealer d = new Dealer(5);
        d.dealCardsToPlayers();


    }
}
