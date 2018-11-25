package cgodin.qc.ca.projet.models;

public class Groupe {
    int id;
    String groupe;

    public Groupe() {
    }

    public Groupe(int id, String groupe) {
        this.id = id;
        this.groupe = groupe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroupe() {
        return groupe;
    }

    public void setGroupe(String groupe) {
        this.groupe = groupe;
    }
}
