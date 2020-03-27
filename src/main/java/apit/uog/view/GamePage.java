package main.java.apit.uog.view;

import main.java.apit.uog.controller.AppController;
import main.java.apit.uog.model.Card;
import main.java.apit.uog.model.Dealer;
import main.java.apit.uog.model.Player;

import javax.swing.*;
import java.awt.*;

import java.util.ArrayList;

public class GamePage extends JPanel {

    private JPanel buttonPanel = new JPanel();
    private JButton hitButton = new JButton("Hit");
    private JButton standButton = new JButton("Stand");
    private JButton betBeforeRoundButton = new JButton("Bet");
    private JPanel outputPanel = new JPanel();
    private JPanel dealerPanel = new JPanel();
    private JLabel dealerLabel = new JLabel("Dealer's hand: ");
    private JLabel dealerScore = new JLabel("Dealer's score: ");
    private JLabel playerTurnLabel = new JLabel();
    private JLabel scoreLabel = new JLabel();
    private JPanel infoBar = new JPanel();
    private ArrayList<PlayerView> playerViews = new ArrayList<>();
    private final String SCORE_STRING = "Score: ";
    private final String HAND_SRING = "hand: ";
    private final String DEALER_STRING = "Dealer's ";


    public GamePage(AppController appController) {

        setLayout(new BorderLayout());

        infoBar.setLayout(new GridLayout(0,2));
        infoBar.add(playerTurnLabel);
        infoBar.add(scoreLabel);

        add(infoBar, BorderLayout.NORTH);
        infoBar.setBorder(BorderFactory.createRaisedBevelBorder());

        playerTurnLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);

        outputPanel.setLayout(new BoxLayout(outputPanel, BoxLayout.Y_AXIS));
        JScrollPane jScrollPane = new JScrollPane(outputPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(jScrollPane, BorderLayout.CENTER);

        JPanel centrePanel = new JPanel();
        centrePanel.setLayout(new GridLayout(2, 1));

        dealerPanel.setLayout(new BoxLayout(dealerPanel, BoxLayout.Y_AXIS));

        dealerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dealerScore.setHorizontalAlignment(SwingConstants.CENTER);

        dealerPanel.add(dealerLabel);
        dealerPanel.add(dealerScore);

        centrePanel.add(dealerPanel);
        centrePanel.add(jScrollPane);

        add(centrePanel);

        add(buttonPanel, BorderLayout.SOUTH);
        enableRoundInProgressButtons(false);

        buttonPanel.add(standButton);
        buttonPanel.add(betBeforeRoundButton);
        buttonPanel.setBorder(BorderFactory.createRaisedBevelBorder());

        hitButton.addActionListener(actionEvent -> {
            enableRoundInProgressButtons(false);
            appController.hit();
        });
        buttonPanel.add(hitButton);

        standButton.addActionListener(actionEvent -> {
            enableRoundInProgressButtons(false);
            appController.stand();
        });

        betBeforeRoundButton.addActionListener(actionEvent -> {
            String betAmount = JOptionPane.showInputDialog("Place your bet", "Bet amount");
            betBeforeRoundButton.setEnabled(false);
            appController.placeBet(betAmount);
        });

    }

    public void addPlayerToView(Player player) {
        playerViews.clear();
        PlayerView pv = new PlayerView(player);
        playerViews.add(pv);
        outputPanel.add(pv);
        pv.setBorder(BorderFactory.createLoweredBevelBorder());
        outputPanel.revalidate();
        outputPanel.repaint();
    }

    public void enableRoundInProgressButtons(boolean boo) {
        hitButton.setEnabled(boo);
        standButton.setEnabled(boo);
    }

    public void setPlayerTurnLabelText(String activePlayer) {
        playerTurnLabel.setVisible(true);
        playerTurnLabel.setText(activePlayer + "'s turn");
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
        scoreLabel.setText(SCORE_STRING + score + "");
    }

    public void setDealerScore(int score){ dealerScore.setText(SCORE_STRING + score);}

    public void setFirstCard(Card card) {
        dealerLabel.setText(DEALER_STRING + HAND_SRING + card.toString());
        dealerScore.setText(DEALER_STRING + SCORE_STRING + card.getValue());
    }

    public void setDealerRoundOver(Dealer dealer){
        dealerLabel.setText(DEALER_STRING + HAND_SRING + dealer.getHand().toString());
        dealerScore.setText(DEALER_STRING + SCORE_STRING + dealer.getDealerScore());
    }

    public ArrayList<PlayerView> getPlayerViews() {
        return playerViews;
    }

    public void revalidateAndRepaint(){
        revalidate();
        repaint();
    }
}


