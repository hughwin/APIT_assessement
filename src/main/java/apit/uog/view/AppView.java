package main.java.apit.uog.view;

import main.java.apit.uog.controller.AppController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


public class AppView extends JFrame {

    private static final String WINDOW_TITLE = "Twenty One";
    private AppController appController;
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);
    private GamePage gamePage;

    public AppView(AppController appController) {
        super();
        this.appController = appController;
        setTitle(WINDOW_TITLE);
        setSize(600, 400);
        add(mainPanel);
        mainPanel.add(new HomePage(this.appController), "home");

        gamePage = new GamePage(appController);
        mainPanel.add(gamePage, "game");
        setVisible(true);

        WindowListener exitListener = new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showOptionDialog(
                        null, "Are You Sure to Close Application?",
                        "Exit Confirmation", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == 0) {
                    appController.quitGame();
                    System.exit(0);
                }
            }
        };

        this.addWindowListener(exitListener);

    }

    public GamePage getGamePage() {
        return gamePage;
    }

    WindowListener exitListener = new WindowAdapter() {

        @Override
        public void windowClosing(WindowEvent e) {
            int confirm = JOptionPane.showOptionDialog(
                    null, "Are You Sure to Close Application?",
                    "Exit Confirmation", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, null, null);
            if (confirm == 0) {
                appController.quitGame();
                try {
                    AppView.this.wait(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    };


    public void setPageView(String constraint) {
        cardLayout.show(mainPanel, constraint);
    }
}
