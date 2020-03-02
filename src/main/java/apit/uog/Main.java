package main.java.apit.uog;

import main.java.apit.uog.model.GameLogic;
import main.java.apit.uog.server.HostAvailability;
import main.java.apit.uog.server.Server;

import java.io.IOException;


public class Main {
    public static void main(String[] args) {

        if(!HostAvailability.hostAvailabilityCheck()){
            Thread t = new Thread(new Server());
            t.start();
        }
        Thread t2 = new Thread(new GameLogic());

      //  new AppView();
       // Dealer d = new Dealer(5);
     //   d.dealCardsToPlayers();





    }
}
