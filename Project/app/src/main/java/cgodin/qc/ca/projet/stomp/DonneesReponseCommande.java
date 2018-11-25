package cgodin.qc.ca.projet.stomp;

public class DonneesReponseCommande {
    private Object[] parametres;
    private String typeCommande;
    private SanitizedLobbyUser de;

    public Object[] getParametres() {
        return parametres;
    }

    public String getTypeCommande() {
        return typeCommande;
    }

    public SanitizedLobbyUser getDe() {
        return de;
    }
}
