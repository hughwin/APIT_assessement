package main.java.apit.uog;

import main.java.apit.uog.controller.AppController;
import main.java.apit.uog.model.GameLogic;
import main.java.apit.uog.server.Server;


public class Main {
    public static void main(String[] args) {
        AppController appController = new AppController();
        Thread t =new Thread(new Server(8888));
        t.start();
        try{t.join();}
        catch(InterruptedException e) {e.printStackTrace();}
    }
}
