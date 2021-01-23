package fi.utu.tech.ringersClock.entities;

import java.io.Serializable;

public class ServerCall<T extends Serializable> implements Serializable {
    private static final long serialVersionUID = 1L;
    private ServerCallType command;
    private T payload;


    public ServerCall(ServerCallType command, T payload) {
        this.command = command;
        this.payload = payload;
    }

    public ServerCall(ServerCallType command) {
        this.command = command;
        this.payload = null;
    }


    public ServerCallType getCommand() { return this.command; }

    public T getPayload() { return this.payload; }
}
