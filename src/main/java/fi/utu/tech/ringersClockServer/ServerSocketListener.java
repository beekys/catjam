package fi.utu.tech.ringersClockServer;

import fi.utu.tech.ringersClock.entities.WakeUpGroup;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class ServerSocketListener extends Thread {

	private String host;
	private int port;
	private WakeUpService wup;
	private List<ClientHandler> ch = Collections.synchronizedList(new ArrayList<>());


	public ServerSocketListener(String host, int port, WakeUpService wup) {
		this.host = host;
		this.port = port;
		this.wup = wup;

	}

	public void run() {
		try {
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

	}

	public void send(WakeUpGroup wug) {
		for(ClientHandler obj : ch) {
			obj.send(wug);
		}
	}

}
