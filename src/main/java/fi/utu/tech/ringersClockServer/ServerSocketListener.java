package fi.utu.tech.ringersClockServer;

import fi.utu.tech.ringersClock.entities.ServerCall;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ServerSocketListener extends Thread {

	private final String host;
	private static ServerSocketListener instance;
	private int port;
	private WakeUpService wus;
	private ServerSocket serverSocket;
	private Map<Integer,ClientHandler> clientHandlers = Collections.synchronizedMap(new HashMap<Integer,ClientHandler>());
	private Thread thread;

	public ServerSocketListener(String host, int port, WakeUpService wus) {
		this.host = host;
		this.port = port;
		this.wus = wus;

		ServerSocketListener.instance = this;
		this.thread = new Thread(this);
		this.thread.start();
	}

	public void run() {
		try {
			this.serverSocket = new ServerSocket(port);
			System.out.println("Server is online");
		} catch (IOException e) { e.printStackTrace(); }
		while (!serverSocket.isClosed()) {
			try {
				Socket clientSocket = serverSocket.accept();
				var ch = new ClientHandler(clientSocket, wus);
				clientHandlers.put(clientSocket.getLocalPort(), ch);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void sendCommandToEveryClient(ServerCall<?> cmd)
	{
		for(var entry : instance.clientHandlers.entrySet())
		{
			entry.getValue().send(cmd);
		}
	}

}