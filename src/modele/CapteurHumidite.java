package modele;

import exception.AlerteConservationException;

public class CapteurHumidite extends Capteur {

    private static final double SEUIL_MIN = 40;
    private static final double SEUIL_MAX = 60;

    public CapteurHumidite(String idCapteur, String localisation) {
        super(idCapteur, localisation);
    }

    @Override
    public double lireValeur() {
        return 30 + Math.random() * 40;
    }

    @Override

    public void verifierSeuil() throws AlerteConservationException {

        double valeur = lireValeur();
        Releve r = new Releve(valeur);
        historique.add(r);
        this.derniereValeur = valeur;

        if (valeur < SEUIL_MIN || valeur > SEUIL_MAX) {
            throw new AlerteConservationException("Humidite hors seuil : " + valeur + "%");
        }
    }
}