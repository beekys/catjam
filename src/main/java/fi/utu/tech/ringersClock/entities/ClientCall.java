package fi.utu.tech.ringersClock.entities;

import java.io.Serializable;

public class ClientCall<T extends Serializable> implements Serializable {
    private static final long serialVersionUID = 1L;
    private ClientCallType command;
    private T payload;

    public ClientCall(ClientCallType command, T payload){
        this.command = command;
        this.payload = payload;
    }

    public ClientCall(ClientCallType command) {
        this.command = command;
        this.payload = null;
    }

    public ClientCallType getCommand() { return this.command; }

    public T getPayload() { return this.payload; }

}
