package main.java.apit.uog.view;

import main.java.apit.uog.model.Player;

import javax.swing.*;

public class PlayerView extends JPanel {

    private JLabel readyLabel;

    public PlayerView(Player player) {

        JLabel nameLabel = new JLabel("Name: " + player.getName());
        JLabel balanceLabel = new JLabel("Balance: " + player.getBalance());
        readyLabel = new JLabel("Ready!");
        add(readyLabel);
        readyLabel.setVisible(player.isReady());

        JLabel cardsLabel = new JLabel("Cards: " + player.getHand());
        add(cardsLabel);

        setSize(80, 40);
        add(nameLabel);
        add(balanceLabel);
        nameLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBorder(BorderFactory.createBevelBorder(10));
    }

}
