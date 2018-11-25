package cgodin.qc.ca.projet.models;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.java_websocket.WebSocket;

@JsonSerialize(as = Compte.class)
@JsonDeserialize(as=CompteImpl.class)
public interface Compte extends SanitizedUser{
    boolean isDeshonore();
    int getPoints();
    int getCredits();
}
