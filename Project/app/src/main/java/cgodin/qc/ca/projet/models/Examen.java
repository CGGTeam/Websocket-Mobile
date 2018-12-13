package cgodin.qc.ca.projet.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class Examen {
    private Long id;

    @JsonDeserialize(as=CompteImpl.class)
    private Compte professeur;
    @JsonDeserialize(as=CompteImpl.class)
    private Compte eleve;

    private boolean reussi;
    private long temps;
    private Groupe ceinture;

    public Examen() {}

    public Examen(Compte professeur, Compte eleve) {
        this.professeur = professeur;
        this.eleve = eleve;
    }

    public SanitizedUser getProfesseur() {
        return professeur;
    }

    public void setProfesseur(Compte professeur) {
        this.professeur = professeur;
    }

    public SanitizedUser getEleve() {
        return eleve;
    }

    public void setEleve(Compte eleve) {
        this.eleve = eleve;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isReussi() {
        return reussi;
    }

    public void setReussi(boolean reussi) {
        this.reussi = reussi;
    }

    public long getTemps() {
        return temps;
    }

    public void setTemps(long temps) {
        this.temps = temps;
    }

    public Groupe getCeinture() {
        return ceinture;
    }

    public void setCeinture(Groupe ceinture) {
        this.ceinture = ceinture;
    }
}
