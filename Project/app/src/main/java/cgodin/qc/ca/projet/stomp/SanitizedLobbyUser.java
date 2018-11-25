package cgodin.qc.ca.projet.stomp;

import cgodin.qc.ca.projet.models.SanitizedUser;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(as=SanitizedLobbyUser.class)
public interface SanitizedLobbyUser extends SanitizedUser{
    LobbyRole getRoleCombat();
}
