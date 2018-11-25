package cgodin.qc.ca.projet.stomp;

public class Commande extends Message {
    private TypeCommande typeCommande;
    private String[] parametres;

    public Commande(){}

    public Commande(String... parametres) {
        super();
        this.parametres = parametres;
    }

    public String[] getParametres() {
        return parametres;
    }

    public void setParametres(String[] parametres) {
        this.parametres = parametres;
    }

    public TypeCommande getTypeCommande() {
        return typeCommande;
    }

    public void setTypeCommande(TypeCommande typeCommande) {
        this.typeCommande = typeCommande;
    }
}
