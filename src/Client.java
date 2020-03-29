import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class Client {

    private final AppView appView;
    private final GamePage gamePage;
    private Socket server;
    private final static int PORT = 8888;
    private final static String LOCALHOST = "127.0.0.1";
    private int sessionID = -1;
    private ObjectOutputStream objectOutputStream;
    private GameState gameState;
    private int numberOfPlayers;

    /**
     * The purpose of the Client class is to act as a controller for the user. Its primary function is to create the GUI.
     * Once the user has clicked on "Play game" the startGame method is called and the client connects to the server.
     */
    public Client() {
        appView = new AppView(this);
        gamePage = appView.getGamePage();
    }

    public static void main(String[] args) {
        new Client();
    }

    /**
     * Starts the game. Changes the view to the game page and connects to the server. Also creates a ReadWorker object
     * which is then executed allowing manipulation of the swing GUI away from the event dispatch thread. Importantly, a Player
     * object is sent to the server.
     *
     * @param name name of the player. This is entered when the game starts and is required so a player
     *             can see how they are doing.
     */

        /*
    ~~~ Methods to communicate with the server ~~~
     */

    public void startGame(String name) {
        try {
            appView.setPageView("game");
            System.out.println("Starting client!");
            server = new Socket(LOCALHOST, PORT);

            ReadWorker rw = new ReadWorker(server);
            rw.execute();

            objectOutputStream = new ObjectOutputStream(server.getOutputStream());
            objectOutputStream.writeObject(new Player(name));
            objectOutputStream.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void quitGame() {
        try {
            objectOutputStream.writeObject("quit");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stand() {
        try {
            objectOutputStream.writeObject("stand");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hit() {
        try {
            objectOutputStream.writeObject("hit");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void placeBet(String betAmount) {

        boolean isInt = true;

        try {
            Integer.parseInt(betAmount); // tries to convert the bet amount into an int.
        } catch (NumberFormatException e) {
            isInt = false;  // If an exception is generated, the bet is not an int.
        }

        if (!isInt) {
            appView.getGamePage().badBetPlaced();
            return;
        }
        int betInt = Integer.parseInt(betAmount);
        if (betInt < 1 || betInt > gameState.getActivePlayers().get(sessionID).getBalance()) {
            appView.getGamePage().badBetPlaced();
        } else {
            try {
                objectOutputStream.writeObject("bet " + betAmount);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * To avoid the GUI freezing, the ReadWorker class is responsible for setting up the ObjectInputStream
     * used to listen to data being sent from
     */
    private class ReadWorker extends SwingWorker<Void, Void> {

        private Socket socket;
        private ObjectInputStream inputStream = null;

        public ReadWorker(Socket socket) {
            this.socket = socket;
            try {
                inputStream = new ObjectInputStream(this.socket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void changePlayerView() {
            for (PlayerView playerView : appView.getGamePage().getPlayerViews()) {

                System.out.println(gameState.isRoundOver());

                Player player = gameState.getActivePlayers().get(playerView.getPlayerID());

                String playerName = player.getName();
                String playerScore = player.totalOfHand() + "";
                int playerBalance = player.getBalance();
                String playerHand = player.getHand().toString();

                playerView.setBalanceLabelText(playerBalance + "");
                playerView.setCardsLabelText(playerHand);


                if (player.isReady()) {
                    playerView.setReadyLabelText("Ready!");
                } else {
                    playerView.setReadyLabelText("");
                }

                if (player.isStanding()) {
                    playerView.setReadyLabelText(playerName +
                            " is standing with a score of " + playerScore);
                }

                if (player.isBust()) {
                    playerView.setReadyLabelText(playerName +
                            " is bust with a score of " + playerScore);
                }

                if (player.isWinner()) {
                    playerView.setReadyLabelText(playerName +
                            " has won with a score of " + playerScore);
                }
                if (gameState.isRoundOver() && !player.isWinner()) {
                    playerView.setReadyLabelText(playerName +
                            " has lost their stake with a score of " + playerScore);
                }
                if (gameState.isRoundOver() && playerBalance == 0){
                    System.out.println("Player broke!");
                    playerView.setBalanceLabelText(playerName +
                            " has no money remaining! They will be thrown out of the game!");
                    gamePage.getBetBeforeRoundButton().setEnabled(false);
                    quitGame(); // Throws broke cheapskates with no money out of the game!
                }

            }

        }

        public void setViewRoundOver() {
            gamePage.setPlayerTurnLabelText("Your name: " + gameState.getActivePlayers().get(sessionID).getName());
            gamePage.setDealerRoundOver(gameState.getDealer());
            gamePage.getBetBeforeRoundButton().setEnabled(true);
            gamePage.enableRoundInProgressButtons(false);
        }

        /**
         * The doInBackGround method is a worker thread. Updates the GUI in the background away from the Event Dispatch Thread.
         * The purpose of this thread, is to listen to the server, and update the GUI accordingly.
         *
         * @return null
         */

        @Override
        protected Void doInBackground() {
            System.out.println("Started swing worker!");
            Object input;
            try {
                while ((input = inputStream.readObject()) != null) {
                    if (input instanceof Integer) {
                        sessionID = (int) input;
                    } else {

                        gameState = (GameState) input;

                        // Create new player view if a player joins
                        if (gameState.getActivePlayers().size() != numberOfPlayers) {
                            numberOfPlayers = gameState.getActivePlayers().size();
                            gamePage.clearPlayerArea();
                            gameState.getActivePlayers().forEach((key, player) -> gamePage.addPlayerToView(player));
                        }

                        // If the active player is not null, set it to the active player and show the player's score.
                        if (gameState.getActivePlayer() != null) {
                            gamePage.setScoreLabel(gameState.getActivePlayers().get(sessionID).totalOfHand());
                            gamePage.setPlayerTurnLabelText(gameState.getActivePlayer().getName()+"'s turn");

                            // If it is the player's go, enable the round in progress buttons
                            if (gameState.getActivePlayer().getID() == sessionID) {
                                gamePage.enableRoundInProgressButtons(true);
                            } else {
                                gamePage.enableRoundInProgressButtons(false);
                            }
                        }

                        // Show the dealer's hand if they have cards
                        if (!gameState.getDealer().getHand().isEmpty()) {

                            //Show only first card if game is in progress.
                            if (gameState.isRoundInProgress()) {
                                gamePage.setFirstCard(gameState.getDealer().getHand().get(0));
                                gamePage.setDealerScore(gameState.getDealer().getHand().get(0).getValue());
                            }
                            if (gameState.isRoundOver()) {
                                gamePage.setDealerRoundOver(gameState.getDealer());
                            }
                        }

                        // If the round is over, reset the game
                        if (gameState.isRoundOver()) {
                            setViewRoundOver();
                        }
                            changePlayerView();
                    }
                    // Refreshes the gamepage.
                    appView.getGamePage().revalidateAndRepaint();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}


