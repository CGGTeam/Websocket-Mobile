package cgodin.qc.ca.projet.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(as=Compte.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompteImpl implements Compte {
    private String courriel;
    private String motPasse;
    private String alias;
    private Long avatarId;
    private Role role;
    private Groupe groupe;
    private int points;
    private int credits;
    private boolean deshonore;
    private int chouchou;
    private int entrainement;
    private int talent;
    
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
        this.avatarId = id;
    }

    @Override
    public Role getRole() {
        return role;
    }

    @Override
    public Groupe getGroupe() {
        return groupe;
    }

    @Override
    public boolean isDeshonore() {
        return deshonore;
    }

    @Override
    public int getPoints() {
        return points;
    }

    @Override
    public int getCredits() {
        return credits;
    }
}
