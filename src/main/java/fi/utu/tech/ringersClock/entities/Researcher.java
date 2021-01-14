package fi.utu.tech.ringersClock.entities;

import java.io.Serializable;

public class Researcher implements Serializable {

    private Integer rID;
    private boolean isLeader;

    public Researcher(Integer rID) {
        this.rID = rID;
    }

    public Integer getrID() {
        return rID;
    }

    public boolean isLeader() {
        return isLeader;
    }

    public void setLeader(boolean leader) {
        isLeader = leader;
    }
}
