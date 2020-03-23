package main.java.apit.uog.view;

import main.java.apit.uog.model.Player;

import javax.swing.*;
import java.awt.*;

public class PlayerView extends JPanel {

    private final Player player;
    private JLabel readyLabel;

    public PlayerView(Player player) {

        this.player = player;

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

    public void showBustMessage(){
        removeAll();
        System.out.println("Bust message!");
        JLabel bustMessage = new JLabel(player.getName() + " has gone bust with the cards \n" + player.getHand() + " totalling " + player.totalOfHand());
        add(bustMessage);
        bustMessage.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

}
