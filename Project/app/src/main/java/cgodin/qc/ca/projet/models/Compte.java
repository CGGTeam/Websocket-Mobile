package cgodin.qc.ca.projet.models;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.java_websocket.WebSocket;

@JsonSerialize(as = Compte.class)
@JsonDeserialize(as=CompteImpl.class)
public interface Compte {
    String getCourriel();
    String getAlias();
    Long getAvatarId();
    void setAvatarId(Long id);
    Role getRole();
    Groupe getGroupe();
    boolean isDeshonore();
    int getPoints();
    int getCredits();
}
