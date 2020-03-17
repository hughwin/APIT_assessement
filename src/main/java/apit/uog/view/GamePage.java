package main.java.apit.uog.view;

import main.java.apit.uog.controller.AppController;
import main.java.apit.uog.model.Player;

import javax.swing.*;

public class GamePage extends JPanel {

    private AppController appController;

    public GamePage(AppController appController){

        this.appController = appController;

        for (Player p : appController.getPlayers()){
            PlayerView pv = new PlayerView(p);
            add(pv);
        }
    }


}
