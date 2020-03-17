package main.java.apit.uog.controller;

import main.java.apit.uog.view.AppView;
import main.java.apit.uog.view.HomePage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePageController implements ActionListener {

    private AppView appView;

    public HomePageController(AppView appView) {
        this.appView = appView;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Exit")){
            System.exit(0); // Quits the application
        }
        if (e.getActionCommand().equals("Play Game")){
            appView.setPageView("game");
        }
    }
}
