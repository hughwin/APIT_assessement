package main.java.apit.uog.server;// Java implementation of  Server side
// It contains two classes : Server and ClientHandler 
// Save file as Server.java 

import main.java.apit.uog.server.ClientHandler;

import java.io.*;
import java.util.*;
import java.net.*;

// Server class 
public class Server implements Runnable {

    // Vector to store active clients 
    static Vector<ClientHandler> ar = new Vector<>();

    // counter for clients 
    static int i = 0;

    private Socket s;
    private ServerSocket ss;

    public Server() {

        // server is listening on port 1234
        this.ss = null;
        try {
            ss = new ServerSocket(1235);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        System.out.println("Running");
        // running infinite loop for getting
        // client request
        while (true) {
            // Accept the incoming request
            try {
                s = ss.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("New client request received : " + s);

            // obtain input and output streams
            DataInputStream dis = null;
            try {
                dis = new DataInputStream(s.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            DataOutputStream dos = null;
            try {
                dos = new DataOutputStream(s.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Creating a new handler for this client...");

            // Create a new handler object for handling this request.
            ClientHandler mtch = new ClientHandler(s, "client " + i, dis, dos);

            // Create a new Thread with this object.
            Thread t = new Thread(mtch);

            System.out.println("Adding this client to active client list");

            // add this client to active clients list
            ar.add(mtch);

            // start the thread.
            t.start();

            // increment i for new client.
            // i is used for naming only, and can be replaced
            // by any naming scheme
            i++;

        }
    }
}
