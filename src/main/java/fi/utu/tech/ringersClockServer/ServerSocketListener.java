package fi.utu.tech.ringersClockServer;

import fi.utu.tech.ringersClock.entities.WakeUpGroup;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

public class ServerSocketListener extends Thread {

	private String host;
	private int port;
	private WakeUpService wup;
	private CopyOnWriteArrayList<ClientHandler> chList = new CopyOnWriteArrayList<ClientHandler>();

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
			ServerSocket sc = new ServerSocket(port);

			while (true) {
				Socket cs = sc.accept();
				chList.add(new ClientHandler(cs, wup, this));

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
