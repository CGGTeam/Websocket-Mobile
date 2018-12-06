package cgodin.qc.ca.projet.stomp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(as = SerializableLobby.class)
@JsonDeserialize(as = Lobby.class)
public interface SerializableLobby {
    LobbyUserData[] getAilleurs();
    LobbyUserData[] getSpectateurs();
    LobbyUserData[] getCombattants();
    LobbyUserData getBlanc();
    LobbyUserData getRouge();
    LobbyUserData getArbitre();
}
