package cgodin.qc.ca.projet.stomp;

import cgodin.qc.ca.projet.models.Compte;
import cgodin.qc.ca.projet.models.SanitizedUser;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(as=SanitizedLobbyUser.class)
@JsonDeserialize(as=LobbyUserData.class)
public interface SanitizedLobbyUser extends SanitizedUser{
    LobbyRole getRoleCombat();
    Compte getUser();
}
