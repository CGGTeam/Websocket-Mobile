package cgodin.qc.ca.projet.stomp;

import cgodin.qc.ca.projet.models.Compte;
import cgodin.qc.ca.projet.models.Groupe;
import cgodin.qc.ca.projet.models.Role;

public class LobbyUserData implements SanitizedLobbyUser {
    private String courriel;
    private String motPasse;
    private String alias;
    private Long avatarId;
    private Role role;
    private Groupe groupe;
    private LobbyRole roleCombat;
    private int credits;
    private int points;
    private Compte compte;

    @Override
    public LobbyRole getRoleCombat() {
        return roleCombat;
    }

    @Override
    public Compte getUser() {
        return compte;
    }

    @Override
    public String getCourriel() {
        return courriel;
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public Long getAvatarId() {
        return avatarId;
    }

    @Override
    public void setAvatarId(Long id) {
        avatarId = id;
    }

    @Override
    public Role getRole() {
        return role;
    }

    @Override
    public Groupe getGroupe() {
        return groupe;
    }
}
