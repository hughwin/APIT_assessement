package main.java.apit.uog.model;

public class StandCheck implements Runnable {

    private volatile boolean running = true;

    public StandCheck(GameState gameState) {
    }

    public void terminate() { running = false; }


    @Override
    public void run() {

        while(true)


        System.out.println("Starting");
    }
}
