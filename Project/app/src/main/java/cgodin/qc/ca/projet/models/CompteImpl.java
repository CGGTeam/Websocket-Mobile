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
    private long ancienDepuis;


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

    @Override
    public long getAncienDepuis() {
        return ancienDepuis;
    }

    public void setCourriel(String courriel) {
        this.courriel = courriel;
    }

    public String getMotPasse() {
        return motPasse;
    }

    public void setMotPasse(String motPasse) {
        this.motPasse = motPasse;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public void setDeshonore(boolean deshonore) {
        this.deshonore = deshonore;
    }

    public int getChouchou() {
        return chouchou;
    }

    public void setChouchou(int chouchou) {
        this.chouchou = chouchou;
    }

    public int getEntrainement() {
        return entrainement;
    }

    public void setEntrainement(int entrainement) {
        this.entrainement = entrainement;
    }

    public int getTalent() {
        return talent;
    }

    public void setTalent(int talent) {
        this.talent = talent;
    }

    public void setAncienDepuis(long ancienDepuis) {
        this.ancienDepuis = ancienDepuis;
    }
}
