package main.java.apit.uog.view;

import main.java.apit.uog.controller.AppController;
import main.java.apit.uog.model.Card;
import main.java.apit.uog.model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GamePage extends JPanel {

    private AppController appController;
    private JPanel buttonPanel = new JPanel();
    private JButton hitButton = new JButton("Hit");
    private JButton standButton = new JButton("Stand");
    private JButton betBeforeRoundButton = new JButton("Bet");
    private JPanel outputPanel = new JPanel();
    private JTextArea playerArea = new JTextArea();
    private JLabel dealerLabel = new JLabel("Dealer's hand: ");
    private JLabel playerTurnLabel = new JLabel();

    public GamePage(AppController appController) {

        setGamePage();

        hitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                appController.hit();
            }
        });
        buttonPanel.add(hitButton);

        standButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                appController.stand();
            }
        });

        betBeforeRoundButton.addActionListener(actionEvent -> {
            String betAmount = JOptionPane.showInputDialog("Place your bet", "Bet amount");
            betBeforeRoundButton.setEnabled(false);
            appController.placeBet(betAmount);
        });

    }

    public void setGamePage(){
        removeAll();
        setLayout(new BorderLayout());

        add(dealerLabel, BorderLayout.NORTH);
        dealerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        add(playerTurnLabel, BorderLayout.NORTH);
        playerTurnLabel.setHorizontalAlignment(SwingConstants.CENTER);
        playerTurnLabel.setVisible(false);

        add(outputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        enableRoundInProgressButtons(false);

        buttonPanel.add(standButton);
        buttonPanel.add(betBeforeRoundButton);

    }

    public void addPlayerToView(Player p, boolean eliminated) {

        PlayerView pv = new PlayerView(p);
        outputPanel.add(pv);
        if (eliminated) {
            pv.showBustMessage();
        }
        pv.setBorder(BorderFactory.createLoweredBevelBorder());
        outputPanel.revalidate();
        outputPanel.repaint();
    }

    public void enableRoundInProgressButtons(boolean boo) {
        hitButton.setEnabled(boo);
        standButton.setEnabled(boo);
    }

    public void setDealerArea(ArrayList<Card> dealerHand) {
        dealerLabel.setText("Dealer's hand: " + dealerHand.get(0).toString());
    }

    public void setPlayerTurnLabelText(String activePlayer) {
        System.out.println(activePlayer);
        playerTurnLabel.setVisible(true);
        playerTurnLabel.setText(activePlayer + "'s turn");
        System.out.println(activePlayer + "'s turn");
    }

    public JPanel getOutPutPanel() {
        return outputPanel;
    }

    public JButton getBetBeforeRoundButton() {
        return betBeforeRoundButton;
    }

    public void badBetPlaced() {
        JOptionPane.showMessageDialog(null, "You're bet either exceeded your balance, or was entered incorrectly! " +
                "Please enter a positive whole number.", "Error!", JOptionPane.INFORMATION_MESSAGE);
        betBeforeRoundButton.setEnabled(true);
    }

}


