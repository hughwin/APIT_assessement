import javax.swing.*;
import java.awt.*;

public class HomePage extends JPanel {

    private static final String TITLE = "<html><h1><strong><i>Twenty One</i></strong></h1><hr></html>"; // extra space necessary to stop it looking strange
    private static final String SUB_TITLE = "<html><i>Please select an option below! </i><html>";

    private Client client;


    public HomePage(Client client) {

        this.client = client;

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
        play.addActionListener(e -> client.startGame(name));
        buttons.add(play, gbc);

        JButton exit = new JButton("Exit");
        exit.addActionListener(e -> System.exit(0));

        buttons.add(exit, gbc);

        gbc.weighty = 1;
        add(buttons, gbc);

    }


}
