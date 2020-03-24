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
    private JLabel dealerLabel = new JLabel("Dealer's hand: ");
    private JLabel playerTurnLabel = new JLabel();
    private JLabel scoreLabel = new JLabel();
    private JPanel infoBar = new JPanel();


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

    public void setGamePage() {
        removeAll();
        setLayout(new BorderLayout());

        infoBar.setLayout(new GridLayout(0,2));
        infoBar.add(playerTurnLabel);
        infoBar.add(scoreLabel);

        add(infoBar, BorderLayout.NORTH);
        infoBar.setBorder(BorderFactory.createRaisedBevelBorder());

        playerTurnLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);


        add(buttonPanel, BorderLayout.SOUTH);
        enableRoundInProgressButtons(false);

        outputPanel.setLayout(new BoxLayout(outputPanel, BoxLayout.Y_AXIS));
        JScrollPane jScrollPane = new JScrollPane(outputPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(jScrollPane, BorderLayout.CENTER);

        buttonPanel.add(standButton);
        buttonPanel.add(betBeforeRoundButton);
        buttonPanel.setBorder(BorderFactory.createRaisedBevelBorder());

    }

    public void addPlayerToView(Player player, boolean bust, boolean winner) {

        PlayerView pv = new PlayerView(player);
        outputPanel.add(pv);
        if (bust) {
            pv.showOutcomeMessage(" has gone bust with the cards \n" + player.getHand() + " totalling " + player.totalOfHand());
        }
        if (winner) {
            pv.showOutcomeMessage(" has won with the cards \n" + player.getHand() + "\n totalling " + player.totalOfHand());
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

    public void setScoreLabel(int score) {
        this.scoreLabel.setText("Score: " + score + "");
    }
}


