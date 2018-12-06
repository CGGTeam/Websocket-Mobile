package cgodin.qc.ca.projet.stomp;

import cgodin.qc.ca.projet.models.SanitizedUser;

public class Reponse {
    private Long id;
    private SanitizedUser de;
    private String texte;
    private Long creation;

    public void setCreation(Long creation) {
        this.creation = creation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Reponse(Long id, SanitizedUser de, String texte, Long creation) {
        this.id = id;
        this.de = de;
        this.texte = texte;
        this.creation = creation;
    }

    public Reponse() {
    }


    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public SanitizedUser getDe() {
        return de;
    }

    public void setDe(SanitizedUser de) {
        this.de = de;
    }

    public Long getCreation() {
        return creation;
    }

    public void setTime(Long creation) {
        this.creation = creation;
    }
}


