package fi.utu.tech.ringersClockServer;

import fi.utu.tech.ringersClock.entities.WakeUpGroup;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {

    private ObjectOutputStream out;
    private ObjectInputStream in;
    private WakeUpService wup;

    public ClientHandler(Socket cs, WakeUpService wup, ServerSocketListener ssl) {
        try {
            this.wup = wup;
            InputStream iin = cs.getInputStream();
            OutputStream oout = cs.getOutputStream();
            out = new ObjectOutputStream(oout);
            in = new ObjectInputStream(iin);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

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
