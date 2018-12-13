package cgodin.qc.ca.projet.models;

public class Combat {
    private Long id;

    private Compte rouge;
    private Compte blanc;
    private Compte arbitre;

    private int pointsRouge;
    private int pointsBlanc;
    private int creditsArbitre;

    private long temps;

    private Groupe ceintureRouge;
    private Groupe ceintureBlanc;

    public Combat() {}

    public Combat(Compte rouge, Compte blanc, Compte arbitre, int pointsRouges, int pointsBlancs, int creditsArbitres, Groupe ceintureBlanc, Groupe ceintureRouge) {
        this.rouge = rouge;
        this.blanc = blanc;
        this.arbitre = arbitre;
        this.pointsRouge = pointsRouges;
        this.pointsBlanc = pointsBlancs;
        this.creditsArbitre = creditsArbitres;
        this.ceintureBlanc = ceintureBlanc;
        this.ceintureRouge = ceintureRouge;
        this.temps = System.currentTimeMillis();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Compte getRouge() {
        return rouge;
    }

    public void setRouge(Compte rouge) {
        this.rouge = rouge;
    }

    public Compte getBlanc() {
        return blanc;
    }

    public void setBlanc(Compte blanc) {
        this.blanc = blanc;
    }

    public Compte getArbitre() {
        return arbitre;
    }

    public void setArbitre(Compte arbitre) {
        this.arbitre = arbitre;
    }

    public Groupe getCeintureRouge() {
        return ceintureRouge;
    }

    public void setCeintureRouge(Groupe ceintureRouge) {
        this.ceintureRouge = ceintureRouge;
    }

    public Groupe getCeintureBlanc() {
        return ceintureBlanc;
    }

    public void setCeintureBlanc(Groupe ceintureBlanc) {
        this.ceintureBlanc = ceintureBlanc;
    }

    public int getPointsRouge() {
        return pointsRouge;
    }

    public void setPointsRouge(int pointsRouge) {
        this.pointsRouge = pointsRouge;
    }

    public int getPointsBlanc() {
        return pointsBlanc;
    }

    public void setPointsBlanc(int pointsBlanc) {
        this.pointsBlanc = pointsBlanc;
    }

    public int getCreditsArbitre() {
        return creditsArbitre;
    }

    public void setCreditsArbitre(int creditsArbitre) {
        this.creditsArbitre = creditsArbitre;
    }

    public long getTemps() {
        return temps;
    }

    public void setTemps(long temps) {
        this.temps = temps;
    }

}
