package cgodin.qc.ca.projet.stomp;

public class Commande extends Message {
    private TypeCommande typeCommande;
    private String[] parametres;

    public Commande(){}

    public Commande(TypeCommande typeCommande) {
        super();
        this.typeCommande = typeCommande;
    }

    public Commande(TypeCommande typeCommande, String... parametres) {
        super();
        this.typeCommande = typeCommande;
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
