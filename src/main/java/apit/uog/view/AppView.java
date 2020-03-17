package main.java.apit.uog.view;

import main.java.apit.uog.controller.AppController;

import javax.swing.*;
import java.awt.*;


public class AppView extends JFrame {

    private AppController appController;

    private static final String WINDOW_TITLE = "Twenty One";
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);

    public AppView(AppController appController){
        super();
        this.appController = appController;
        setTitle(WINDOW_TITLE);
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(mainPanel);
        mainPanel.add(new HomePage(this.appController), "home");
        mainPanel.add(new GamePage(this.appController), "game");
        setVisible(true);
    }

    public void setPageView(String constraint){
        cardLayout.show(mainPanel, constraint);
    }
}
