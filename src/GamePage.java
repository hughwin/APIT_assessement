import javax.swing.*;
import java.awt.*;

import java.util.ArrayList;

public class GamePage extends JPanel {

    private ArrayList<PlayerView> playerViews = new ArrayList<>();
    private final JPanel buttonPanel = new JPanel();
    private final JButton hitButton = new JButton("Hit");
    private final JButton standButton = new JButton("Stand");
    private final JButton betBeforeRoundButton = new JButton("Bet");
    private final JPanel outputPanel = new JPanel();
    private final JLabel dealerLabel = new JLabel("Dealer's hand: ");
    private final JLabel dealerScore = new JLabel("Dealer's score: ");
    private final  JLabel playerTurnLabel = new JLabel();
    private final JLabel scoreLabel = new JLabel();
    private final String SCORE_STRING = "Score: ";
    private final String HAND_SRING = "hand: ";
    private final String DEALER_STRING = "Dealer's ";


    public GamePage(AppController appController) {

        setLayout(new BorderLayout());

        JPanel infoBar = new JPanel();
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

        JPanel dealerPanel = new JPanel();
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

    public void clearPlayerArea(){
        outputPanel.removeAll();
        playerViews.clear();
    }
}


