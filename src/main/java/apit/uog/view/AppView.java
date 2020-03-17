package main.java.apit.uog.view;

import main.java.apit.uog.controller.AppController;

import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;


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
        mainPanel.add(new HomePage(appController), "home");
        mainPanel.add(new GamePage(), "game");
        setVisible(true);
    }

    public void setPageView(String constraint){
        cardLayout.show(mainPanel, constraint);
    }
}
