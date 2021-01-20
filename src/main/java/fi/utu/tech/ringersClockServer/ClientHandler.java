package fi.utu.tech.ringersClockServer;

import fi.utu.tech.ringersClock.entities.WakeUpGroup;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {

    private ObjectOutputStream out;
    private ObjectInputStream in;
<<<<<<< HEAD
    private WakeUpService wup;

    public ClientHandler(Socket cs, WakeUpService wup, ServerSocketListener ssl) {
        try {
            this.wup = wup;
            InputStream iin = cs.getInputStream();
            OutputStream oout = cs.getOutputStream();
            out = new ObjectOutputStream(oout);
            in = new ObjectInputStream(iin);

=======


    public ClientHandler(Socket cs, WakeUpService wup, ServerSocketListener serverSocketListener) {
        try {
            InputStream is = cs.getInputStream();
            OutputStream os = cs.getOutputStream();
            out = new ObjectOutputStream(os);
            in = new ObjectInputStream(is);
>>>>>>> 74e0ab27bf5f527a57347c38f46d8d056a8a52f7
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

<<<<<<< HEAD
    public void run() {

        while(true) {
            try {
                Object iobj = in.readObject();

                if (iobj instanceof WakeUpGroup) {
                    WakeUpGroup wug = (WakeUpGroup) iobj;

                    if (wug.getCommand().equals("Test")); {
                        wup.handleTest(wug);
                    }
                }
            }

        }
    }

    public void send(WakeUpGroup wug){
        try {
            out.writeObject(wug);
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
=======
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
>>>>>>> 74e0ab27bf5f527a57347c38f46d8d056a8a52f7
