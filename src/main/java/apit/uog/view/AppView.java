package main.java.apit.uog.view;

import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class AppView extends JFrame {

    private static final String WINDOW_TITLE = "Twenty One";
    private JPanel mainPanel = new HomePage();


    public AppView(){
        super();
        setTitle(WINDOW_TITLE);
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(mainPanel);
        setVisible(true);
    }

}
