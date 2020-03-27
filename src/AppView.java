import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


public class AppView extends JFrame {

    private static final String WINDOW_TITLE = "Twenty One";
    private final Client client;
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel mainPanel = new JPanel(cardLayout);
    private final GamePage gamePage;

    public AppView(Client client) {
        super();
        WindowListener exitListener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showOptionDialog(
                        null, "Are You Sure to Close Application?",
                        "Exit Confirmation", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == 0) {
                    client.quitGame(); // Handles the removal of the client from the server.
                    System.exit(0);
                }
            }
        };
        this.addWindowListener(exitListener);

        this.client = client;
        setTitle(WINDOW_TITLE);
        setSize(600, 400);
        add(mainPanel);
        mainPanel.add(new HomePage(this.client), "home");

        gamePage = new GamePage(client);
        mainPanel.add(gamePage, "game");
        setVisible(true);

    }

    public GamePage getGamePage() {
        return gamePage;
    }

    public void setPageView(String constraint) {
        cardLayout.show(mainPanel, constraint);
    }
}
