package main.java.apit.uog.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePageActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Exit")){
            System.exit(0); // Quits the application
        }
    }
}
