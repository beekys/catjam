package fi.utu.tech.ringersClockServer;

import fi.utu.tech.ringersClock.entities.WakeUpGroup;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {

    private ObjectOutputStream out;
    private ObjectInputStream in;


    public ClientHandler(Socket cs, WakeUpService wup, ServerSocketListener serverSocketListener) {
        try {
            InputStream is = cs.getInputStream();
            OutputStream os = cs.getOutputStream();
            out = new ObjectOutputStream(os);
            in = new ObjectInputStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void send(WakeUpGroup wug) {
        try {
            out.writeObject(wug);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {

        while(true) {
            try {
                Object inObj = in.readObject();

                if (inObj instanceof WakeUpGroup) {
                    WakeUpGroup wug = (WakeUpGroup) inObj;
                }
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}