package main.java.apit.uog.view;

import main.java.apit.uog.model.Player;

import javax.swing.*;

public class PlayerView extends JPanel {


    public PlayerView(Player player) {

        JLabel nameLabel = new JLabel(player.getName());
        nameLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBorder(BorderFactory.createBevelBorder(10));
        add(nameLabel);
    }
}
