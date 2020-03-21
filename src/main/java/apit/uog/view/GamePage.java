package main.java.apit.uog.view;

import main.java.apit.uog.controller.AppController;
import main.java.apit.uog.model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePage extends JPanel {

    private AppController appController;
    private JPanel buttonPanel = new JPanel();
    private JButton playButton = new JButton("Play ");
    private JButton hitButton = new JButton("Hit  ");
    private JButton standButton = new JButton("Stand");
    private JPanel outputPanel = new JPanel();
    private JTextArea playerArea = new JTextArea();
    private JTextArea dealerArea = new JTextArea();

    public GamePage(AppController appController) {

        this.appController = appController;

        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);
        outputPanel.setLayout(new BorderLayout());

        add(buttonPanel,  BorderLayout.SOUTH);
        buttonPanel.add(playButton);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                appController.setReady();
            }
        });


        buttonPanel.add(hitButton, null);
        buttonPanel.add(standButton, null);


        add(outputPanel, BorderLayout.CENTER);
        outputPanel.setLayout(new FlowLayout());
        outputPanel.add(playerArea, null);
        outputPanel.add(dealerArea, null);
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

    public JPanel getOutPutPanel(){
        return outputPanel;
    }
}


