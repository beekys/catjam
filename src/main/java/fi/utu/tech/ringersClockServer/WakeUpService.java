package fi.utu.tech.ringersClockServer;

import fi.utu.tech.ringersClock.entities.WakeUpGroup;

public class WakeUpService extends Thread {

	private ServerSocketListener ssl;
	public WakeUpService() {

	}

	public void setSSL(ServerSocketListener ssl) {
		this.ssl = ssl;
	}
	public void run() {

	}

	public void handleTest(WakeUpGroup wug) {
		ssl.send(wug);
	}


}
