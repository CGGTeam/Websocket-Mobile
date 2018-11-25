package cgodin.qc.ca.projet.stomp;

public class ReponseCommande extends Reponse {
    private DonneesReponseCommande donnees;

    public DonneesReponseCommande getDonnees() {
        return donnees;
    }

    public void setDonnees(DonneesReponseCommande donnees) {
        this.donnees = donnees;
    }
}
