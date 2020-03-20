package main.java.apit.uog.view;

import main.java.apit.uog.controller.AppController;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class HomePage extends JPanel {

        private static final String TITLE = "<html><h1><strong><i>Twenty One</i></strong></h1><hr></html>";
        // extra space necessary to stop it looking strange
        private static final String SUB_TITLE = "<html><i>Please select an option below! </i><html>";

    private AppController appController;


    public HomePage(AppController appController) {

        this.appController = appController;

        String name = JOptionPane.showInputDialog(this, "What's your name?");

        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;

        add(new JLabel(TITLE), gbc);
        add(new JLabel(SUB_TITLE), gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel buttons = new JPanel(new GridBagLayout());
        JButton play = new JButton("Play Game");
        play.addActionListener(e -> {
            try {
                appController.startGame(name);
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });
        buttons.add(play, gbc);

        JButton exit = new JButton("Exit");
        exit.addActionListener(e -> System.exit(0));

        buttons.add(exit, gbc);

        gbc.weighty = 1;
        add(buttons, gbc);

    }


}
