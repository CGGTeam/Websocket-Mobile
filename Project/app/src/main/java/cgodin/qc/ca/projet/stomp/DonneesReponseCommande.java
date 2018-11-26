package cgodin.qc.ca.projet.stomp;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = ReponseCommandeDeserializer.class)
public class DonneesReponseCommande {
    private String[] parametres;
    private String typeCommande;
    private SanitizedLobbyUser de;

    public DonneesReponseCommande() {
    }


    public DonneesReponseCommande(String[] parametres, String typeCommande, SanitizedLobbyUser de) {
        this.parametres = parametres;
        this.typeCommande = typeCommande;
        this.de = de;
    }

    public String[] getParametres() {
        return parametres;
    }

    public TypeCommande getTypeCommande() {
        return TypeCommande.valueOf(typeCommande);
    }

    public SanitizedLobbyUser getDe() {
        return de;
    }
}
