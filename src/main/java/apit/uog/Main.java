package main.java.apit.uog;

import main.java.apit.uog.controller.AppController;
import main.java.apit.uog.model.GameLogic;
import main.java.apit.uog.server.Server;


public class Main {
    public static void main(String[] args) {


        AppController appController = new AppController();

        Thread t = new Thread(new Server());
        t.start();


        // Possibly important

//        if(!HostAvailability.hostAvailabilityCheck()){
//            Thread t = new Thread(new Server());
//            t.start();
//        }
//        Thread t2 = new Thread(new GameLogic());

      //  new AppView();
    }
}
