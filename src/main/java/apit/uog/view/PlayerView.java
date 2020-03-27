package main.java.apit.uog.view;

import main.java.apit.uog.model.Player;

import javax.swing.*;
import java.awt.*;

public class PlayerView extends JPanel {

    private final JLabel readyLabel;
    private final JLabel cardsLabel;
    private final JLabel balanceLabel;
    private final int  playerID;

    public PlayerView(Player player) {

        setLayout(new GridLayout(4,1));
        this.playerID = player.getID();

        JLabel nameLabel = new JLabel("Name: " + player.getName());
        balanceLabel = new JLabel("Balance: " + player.getBalance());
        readyLabel = new JLabel("");
        add(readyLabel);

        cardsLabel = new JLabel("Cards: " + player.getHand());
        add(cardsLabel);

        setSize(80, 40);
        add(nameLabel);
        add(balanceLabel);
        nameLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        setBorder(BorderFactory.createBevelBorder(10));
    }

    public void setReadyLabelText(String message) {
        readyLabel.setText(message);
    }

    public void setCardsLabelText(String message) {
        cardsLabel.setText(message);
    }

    public void setBalanceLabelText(String message) {
        balanceLabel.setText(message);
    }

    public int getPlayerID() {
        return playerID;
    }
}


