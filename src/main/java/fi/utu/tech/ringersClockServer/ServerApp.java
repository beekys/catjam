package fi.utu.tech.ringersClockServer;

public class ServerApp {

	private static ServerApp app;
	private WakeUpService wus;
	private String serverIP = "127.0.0.1";
	private int serverPort = 3000;

	public ServerApp() {

		wus = new WakeUpService();
		new ServerSocketListener(serverIP, serverPort, wus);
		wus.start();
	}

	public static void main(String[] args) {

		System.out.println("Starting server...");
		app = new ServerApp();
	}

}
