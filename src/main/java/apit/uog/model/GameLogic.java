package main.java.apit.uog.model;

import main.java.apit.uog.view.AppView;

import java.util.ArrayList;

public class GameLogic implements Runnable {

    private int round;
    private boolean roundComplete;
    private ArrayList<Player> activePlayer;

    public GameLogic(){
        activePlayer = new ArrayList<>();
        new AppView();
    }

    @Override
    public void run() {
        new GameLogic();
    }
}
