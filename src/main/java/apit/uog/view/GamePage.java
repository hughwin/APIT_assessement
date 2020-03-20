package main.java.apit.uog.view;

import main.java.apit.uog.controller.AppController;
import main.java.apit.uog.model.Player;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class GamePage extends JPanel {

    private AppController appController;

    public GamePage(AppController appController){

        this.appController = appController;

        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);


        for (Player p : appController.getPlayers()){
            PlayerView pv = new PlayerView(p);
            pv.setBorder(BorderFactory.createLoweredBevelBorder());
            add(pv);
        }
    }

}
