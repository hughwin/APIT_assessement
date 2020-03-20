package main.java.apit.uog.view;

import main.java.apit.uog.controller.AppController;
import main.java.apit.uog.model.Player;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class GamePage extends JPanel {

    private AppController appController;

    public GamePage(AppController appController){

        this.appController = appController;

        BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(layout);


        for (Player p : appController.getPlayers()){
            PlayerView pv = new PlayerView(p);
            pv.setBorder(BorderFactory.createLoweredBevelBorder());
            add(pv);
        }
    }

    private class ReadWorker extends SwingWorker<Void, Void>{

        private Socket socket;
        private ObjectInputStream inputStream = null;
        private GamePage gamePage;

        public ReadWorker(GamePage gamePage, Socket socket){
            this.socket = socket;
            this.gamePage = gamePage;
            try{
                inputStream = new ObjectInputStream(this.socket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Void doInBackground() throws Exception {
            System.out.println("Started ReaderWorker in background!");
            return null;
        }
    }


}
