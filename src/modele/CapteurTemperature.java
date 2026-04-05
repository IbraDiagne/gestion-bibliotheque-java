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
        double valeur = 10 + Math.random() * 20;
        Releve r = new Releve(valeur);
        historique.add(r);
        this.derniereValeur = valeur;
        return valeur;
    }

    @Override
    public void verifierSeuil() throws AlerteConservationException {
        double valeur = lireValeur();
        if (valeur < SEUIL_MIN || valeur > SEUIL_MAX)
            throw new AlerteConservationException("Temperature hors seuil : " + valeur + "°C");
    }

    public Releve getDernierReleve() {
        if (historique.isEmpty()) return null;
        return historique.get(historique.size() - 1);
    }
}
