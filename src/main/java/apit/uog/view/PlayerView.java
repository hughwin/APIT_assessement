package main.java.apit.uog.view;

import main.java.apit.uog.model.Player;

import javax.swing.*;
import java.awt.*;

public class PlayerView extends JPanel {

    private final Player player;
    private JLabel readyLabel;

    public PlayerView(Player player) {

        setLayout(new GridLayout(4,1));

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
        nameLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        setBorder(BorderFactory.createBevelBorder(10));
    }

    public void showOutcomeMessage(String message) {
        removeAll();

//                if (bust) {
//            pv.showOutcomeMessage(" has gone bust with the cards \n" + player.getHand() + " totalling " + player.totalOfHand());
//        }
//        if (winner) {
//            pv.showOutcomeMessage(" has won with the cards \n" + player.getHand() + "\n totalling " + player.totalOfHand());
//        }


        JLabel outcomeLabel = new JLabel(player.getName() + message);
        add(outcomeLabel);
        outcomeLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }



}


