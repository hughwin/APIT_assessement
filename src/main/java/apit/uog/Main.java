package main.java.apit.uog;

import main.java.apit.uog.controller.AppController;
import main.java.apit.uog.model.GameLogic;


public class Main {
    public static void main(String[] args) {

        GameLogic gameLogic = new GameLogic();
        AppController appController = new AppController(gameLogic);


        // Possibly important

//        if(!HostAvailability.hostAvailabilityCheck()){
//            Thread t = new Thread(new Server());
//            t.start();
//        }
//        Thread t2 = new Thread(new GameLogic());

      //  new AppView();
    }
}
