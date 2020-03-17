package main.java.apit.uog.controller;

import main.java.apit.uog.model.GameLogic;
import main.java.apit.uog.model.Player;
import main.java.apit.uog.view.AppView;

import java.util.ArrayList;


public class AppController {

    private AppView appView;
    private GameLogic gameLogic;

    public AppController() {
        gameLogic = new GameLogic();
        appView = new AppView(this);
    }

    public void startGame() {
        appView.setPageView("game");
    }

    public ArrayList<Player> getPlayers(){
        ArrayList<Player> a = gameLogic.getActivePlayer();
        return gameLogic.getActivePlayer();
    }

}
