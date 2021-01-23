package fi.utu.tech.ringersClockServer;

import fi.utu.tech.ringersClock.entities.ServerCall;
import fi.utu.tech.ringersClock.entities.ServerCallType;
import fi.utu.tech.ringersClock.entities.WakeUpGroup;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class WakeUpService extends Thread {

	private ServerSocketListener ssl;
	private Map<Integer, WakeUpGroup> groups = Collections.synchronizedMap(new HashMap<Integer, WakeUpGroup>());

	public synchronized void addGroup(int ID, WakeUpGroup wug) {
		if(!groups.containsKey(ID)) {
			groups.put(ID, wug);
			updateExistingGroupsForAllUsers();
			System.out.println("Group + " + wug.getName() + " created by user " + ID + ".");
		} else {
			System.out.println("Group already exists, group founder: " + ID + ".");
		}
	}

	public synchronized void resignGroup(int groupID, int clientID) {
		WakeUpGroup group = groups.get(groupID);
		if (group.userExistsInGroup(clientID)) {
			group.removeUser(clientID);
			System.out.println("User " + clientID + " removed from group " + group.getName() + ".");
		} else {
			System.out.println("No user " + clientID + " found in group " + group.getName() + ".");
		}
	}

	public synchronized void joinGroup(int groupID, int clientID) {
		WakeUpGroup group = groups.get(groupID);
		if (!group.userExistsInGroup(clientID)) {
			group.addUser(clientID);
			System.out.println("User " + clientID + " added to group " + group.getName() + ".");
		} else {
			System.out.println("User " + clientID + " is already in group " + group.getName() + ".");
		}
	}

	public WakeUpService() {

	}

	private synchronized void updateExistingGroupsForAllUsers() {
		WakeUpGroup[] groupList = groups.values().toArray(new WakeUpGroup[groups.size()]);
		ServerSocketListener.sendCommandToEveryClient(
				new ServerCall<WakeUpGroup[]>(
						ServerCallType.UPDATE_EXISTING_GROUPS, groupList)
		);
	}
	public void run() {

	}
}
