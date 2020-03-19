package main.java.apit.uog.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;

// ClientHandler class
class ClientHandler implements Runnable {
    final DataInputStream dis;
    final DataOutputStream dos;
    Scanner scn = new Scanner(System.in);
    Socket s;
    private String name;

    // constructor 
    public ClientHandler(Socket s, String name,
                         DataInputStream dis, DataOutputStream dos) {
        this.dis = dis;
        this.dos = dos;
        this.name = name;
        this.s = s;
    }

    @Override
    public void run() {

    }
} 