package fi.utu.tech.ringersClock.entities;

import java.io.Serializable;
import java.util.ArrayList;

/*
 * Entity class presenting a WakeUpGroup. The class is not complete.
 * You need to add some variables.
 */

public class WakeUpGroup implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private Integer ID;
	private ArrayList<Researcher> researchers;
	private String command;

	public WakeUpGroup(Integer id, String name) {
		super();
		this.ID = id;
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public String getCommand() {
		return this.command;
	}

	public Integer getID() {
		return this.ID;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setID(Integer ID) {
		this.ID = ID;
	}

	public void joinResearchers(Researcher r) {
		researchers.add(r);
	}

	@Override
	public String toString() {
		return this.getName();
	}

}
