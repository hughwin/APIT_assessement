package main.java.apit.uog.view;

import main.java.apit.uog.model.Player;

import javax.swing.*;

public class PlayerView extends JPanel {


    public PlayerView(Player player) {
        add(new JLabel(player.getName()));

    }
}
