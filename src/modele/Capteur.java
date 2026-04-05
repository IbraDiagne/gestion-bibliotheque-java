package modele;

import java.util.ArrayList;
import exception.AlerteConservationException;

public abstract class Capteur {
    protected String idCapteur;
    protected String localisation;
    protected double derniereValeur;
    protected ArrayList<Releve> historique;

    public Capteur(String idCapteur, String localisation) {
        this.idCapteur = idCapteur;
        this.localisation = localisation;
        this.historique = new ArrayList<>();
    }

    public abstract double lireValeur();

    public void verifierSeuil() throws AlerteConservationException {
        double valeur = lireValeur();
        Releve r = new Releve(valeur);
        historique.add(r);
        this.derniereValeur = valeur;
    }

    public ArrayList<Releve> getHistorique() { 
        return historique; 
    }

    public Releve getDernierReleve() {
        
        if (historique.isEmpty()) {
            return null;
        }
        return historique.get(historique.size() - 1);
    }

    public String getIdCapteur() { 
        return idCapteur; 
    }

    public String getLocalisation() { 
        return localisation; 
    }
}
