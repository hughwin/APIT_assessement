package main.java.apit.uog.view;

import main.java.apit.uog.controller.AppController;
import main.java.apit.uog.model.Player;

import javax.swing.*;

public class GamePage extends JPanel {

    private AppController appController;

    public GamePage(AppController appController) {

        this.appController = appController;

        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);
    }


        public void addPlayerToView(Player p){
            PlayerView pv = new PlayerView(p);
            pv.setBorder(BorderFactory.createLoweredBevelBorder());
            this.add(pv);
            this.revalidate();
            this.repaint();
        }
    }


