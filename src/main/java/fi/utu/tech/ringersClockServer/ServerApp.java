package fi.utu.tech.ringersClockServer;

import fi.utu.tech.ringersClock.entities.WakeUpGroup;

public class ServerApp {

	private static ServerApp app;
	private ServerSocketListener listener;
	private WakeUpService wup;
	private String serverIP = "127.0.0.1";
	private int serverPort = 3000;

	public ServerApp() {

		wup = new WakeUpService();
		listener = new ServerSocketListener(serverIP, serverPort, wup);
		wup.setSSL(listener);
		wup.start();
		listener.start();
	}

	public static void main(String[] args) {

		System.out.println("Starting server...");
		app = new ServerApp();
	}

}
