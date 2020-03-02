package main.java.apit.uog.server;

import java.io.IOException;

import java.net.Socket;

public class HostAvailability {


    public static boolean hostAvailabilityCheck() {
        try (Socket s = new Socket("localhost", 1235)) {
            return true;
        } catch (IOException ex) {
            /* ignore */
        }
        return false;
    }
}
