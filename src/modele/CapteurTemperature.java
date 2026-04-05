package modele;

import exception.AlerteConservationException;

public class CapteurTemperature extends Capteur {
    private static final double SEUIL_MIN = 15;
    private static final double SEUIL_MAX = 22;

    public CapteurTemperature(String idCapteur, String localisation) {
        super(idCapteur, localisation);
    }

    @Override
    public double lireValeur() {
        return 10 + Math.random() * 20;
    }

    @Override
    
    public void verifierSeuil() throws AlerteConservationException {
        double valeur = lireValeur();
        Releve r = new Releve(valeur);
        historique.add(r);
        this.derniereValeur = valeur;
        if (valeur < SEUIL_MIN || valeur > SEUIL_MAX) {
            throw new AlerteConservationException("Temperature hors seuil : " + valeur + "°C");
        }
    }
}