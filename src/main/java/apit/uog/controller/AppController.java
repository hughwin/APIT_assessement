package main.java.apit.uog.controller;

import main.java.apit.uog.model.GameLogic;
import main.java.apit.uog.view.AppView;


public class AppController {

    private AppView appView;
    private GameLogic gameLogic;

    public AppController(GameLogic gameLogic) {
        this.appView = new AppView(this);
        this.gameLogic = gameLogic;
    }

    public void startGame() {
        appView.setPageView("game");
    }
}
