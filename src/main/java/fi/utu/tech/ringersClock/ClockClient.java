package fi.utu.tech.ringersClock;

import java.net.Socket;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;

import fi.utu.tech.ringersClock.entities.*;


/*
 * A class for handling network related stuff
 */

public class ClockClient extends Thread {

	private static ClockClient instance = null;
	private MessageHandler mh;
	private String host;
	private int port;
	private Gui_IO gio;
	private Thread thread;
	private Socket server;
	private InputStream inS;
	private OutputStream outS;
	private ObjectOutputStream objOutS;
	private ObjectInputStream objInS;

	public ClockClient(String host, int port, Gui_IO gio) throws IOException {
		this.host = host;
		this.port = port;
		this.gio = gio;
		this.mh = new MessageHandler();
		this.server = new Socket(host, port);
		this.thread = new Thread(this);
		this.thread.start();

		if (ClockClient.instance != null) {
			close();
		}
		ClockClient.instance = this;
	}

	public void run() {
		System.out.println("Connection initiated - Host name: " + host + " Port: " + port + " Gui_IO:" + gio.toString());
		try {
			inS = server.getInputStream();
			outS = server.getOutputStream();
			objOutS = new ObjectOutputStream(outS);
			objInS = new ObjectInputStream(inS);

			while (server.isConnected()) {
				mh.handle(objInS.readObject());
			}

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("Disconnected due to error");
		}
	}

	public static void sendSerializable(Serializable data) {
		if (instance != null) {
			try {
				instance.objOutS.writeObject(data);
				instance.objOutS.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Instance not found, unable to send data.");
		}
	}

	public static int getLocalPort() {
		return instance.server.getLocalPort();
	}

	public static void send(ClientCall<?> command) {
		ClockClient.sendSerializable(command);
	}

	public static void close() throws IOException {
		if (instance != null) {
			instance.objInS.close();
			instance.objOutS.close();
			instance.server.close();
			System.out.println("Connection to server closed:" + instance.server.getRemoteSocketAddress());
		} else {
			System.out.println("Instance not found, unable to close connection to server.");
		}
	}
	private class MessageHandler
	{

		private void handleAlarmTimeResponse(Instant time) { gio.setAlarmTime(time); }

		private void handleAlarmTimeCancelled() { gio.clearAlarmTime(); }

		private void handleAppendToStatus(String msg) { gio.appendToStatus(msg); }

		private void handleUpdateExistingGroups(WakeUpGroup[] groups) { gio.fillGroups(new ArrayList<>(Arrays.asList(groups))); }

		private void handleAlarmUser() { gio.alarm(); }

		private void handleConfirmWakeUpGroupAlarm() { gio.clearAlarmTime(); }

		public void handle(Object obj)
		{
			System.out.println("Server message: "+server.getRemoteSocketAddress()+" - "+ obj.toString());
			try {
				if(obj instanceof ServerCall) {
					var cmd = (ServerCall<?>)obj;
					var command = cmd.getCommand();
					var payload = cmd.getPayload();

					switch  (command) {
						case AlARM_TIME_RESPONCE:
							if(payload instanceof Instant) handleAlarmTimeResponse((Instant)payload);
							break;

						case ALARM_TIME_CANCELLED:
							handleAlarmTimeCancelled();
							break;

						case APPEND_TO_STATUS:
							if(payload instanceof String) handleAppendToStatus((String)payload);
							break;

						case ALARM_USER:
							handleAlarmUser();
							break;


						case CONFIRM_WAKE_UP_GROUP_ALARM:
							handleConfirmWakeUpGroupAlarm();
							break;

						case UPDATE_EXISTING_GROUPS:
							if(payload instanceof WakeUpGroup[]) handleUpdateExistingGroups((WakeUpGroup[])payload);
							break;

						default:
							throw new Exception("Unknown CommandPayloadType: "+command);
					}
				} else {
					throw new Exception("Message from client not handled because its not Command or CommandPayload");
				}
			} catch(Exception e) { e.printStackTrace(); }
		}
	}
}
