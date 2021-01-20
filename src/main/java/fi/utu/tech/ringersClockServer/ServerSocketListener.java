package fi.utu.tech.ringersClockServer;

import fi.utu.tech.ringersClock.entities.WakeUpGroup;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
<<<<<<< HEAD
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
=======
import java.util.concurrent.CopyOnWriteArrayList;
>>>>>>> 74e0ab27bf5f527a57347c38f46d8d056a8a52f7

public class ServerSocketListener extends Thread {

	private String host;
	private int port;
	private WakeUpService wup;
<<<<<<< HEAD
	private List<ClientHandler> ch = Collections.synchronizedList(new ArrayList<>());

=======
	private CopyOnWriteArrayList<ClientHandler> chList = new CopyOnWriteArrayList<ClientHandler>();
>>>>>>> 74e0ab27bf5f527a57347c38f46d8d056a8a52f7

	public ServerSocketListener(String host, int port, WakeUpService wup) {
		this.host = host;
		this.port = port;
		this.wup = wup;

	}

	public void send(WakeUpGroup wug) {
		for(ClientHandler item : chList) {
			item.send(wug);
		}
	}

	public void run() {
		try {
<<<<<<< HEAD
			ServerSocket sk = new ServerSocket(port);

			while(true) {
				Socket cs = sk.accept();
				ClientHandler chTemp = new ClientHandler(cs, wup, this);
				ch.add(chTemp);
				chTemp.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
=======
			ServerSocket sc = new ServerSocket(port);

			while (true) {
				Socket cs = sc.accept();
				chList.add(new ClientHandler(cs, wup, this));
>>>>>>> 74e0ab27bf5f527a57347c38f46d8d056a8a52f7

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void send(WakeUpGroup wug) {
		for(ClientHandler obj : ch) {
			obj.send(wug);
		}
	}

}
