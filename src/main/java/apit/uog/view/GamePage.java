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
    private JButton playButton = new JButton("Play ");
    private JButton hitButton = new JButton("Hit  ");
    private JButton standButton = new JButton("Stand");
    private JPanel outputPanel = new JPanel();
    private JTextArea playerArea = new JTextArea();
    private JLabel dealerLabel = new JLabel("Dealer's hand: ");

    public GamePage(AppController appController) {

        this.appController = appController;
        setLayout(new BorderLayout());
        add(dealerLabel, BorderLayout.NORTH);
        dealerLabel.setHorizontalAlignment(SwingConstants.CENTER);


        buttonPanel.add(playButton);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                appController.setReady();
                playButton.setEnabled(false);
            }
        });


        buttonPanel.add(hitButton, null);
        buttonPanel.add(standButton, null);

        add(buttonPanel, BorderLayout.SOUTH);


        add(outputPanel, BorderLayout.CENTER);
        outputPanel.setLayout(new FlowLayout());
        outputPanel.add(playerArea, null);
        outputPanel.setAutoscrolls(true);

        hitButton.setEnabled(false);
        standButton.setEnabled(false);

    }

    public void addPlayerToView(Player p) {
        PlayerView pv = new PlayerView(p);
        pv.setBorder(BorderFactory.createLoweredBevelBorder());
        outputPanel.add(pv);
        outputPanel.revalidate();
        outputPanel.repaint();
    }

    public void enableRoundInProgressButtons(boolean roundInProgress){
        hitButton.setEnabled(roundInProgress);
        standButton.setEnabled(roundInProgress);
    }

    public void setDealerArea(ArrayList<Card> dealerHand) {
        dealerLabel.setText("Dealer's hand: " + dealerHand.get(0).toString());
    }

    public JPanel getOutPutPanel(){
        return outputPanel;
    }
}


