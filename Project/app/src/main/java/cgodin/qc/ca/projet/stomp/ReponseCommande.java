package cgodin.qc.ca.projet.stomp;

import cgodin.qc.ca.projet.models.SanitizedUser;

public class ReponseCommande extends Reponse {
    private DonneesReponseCommande donnees;

    public ReponseCommande(Long id, SanitizedUser de, String texte, Long creation, DonneesReponseCommande donnees) {
        super(id, de, texte, creation);
        this.donnees = donnees;
    }

    public ReponseCommande() {
    }

    public DonneesReponseCommande getDonnees() {
        return donnees;
    }
}
