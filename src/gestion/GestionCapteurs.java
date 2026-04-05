package gestion;

import modele.*;
import exception.*;
import java.util.ArrayList;

public class GestionCapteurs {
    
    private ArrayList<Capteur> capteurs;

    public GestionCapteurs() {
        this.capteurs = new ArrayList<>();
    }

    public void ajouterCapteur(Capteur c) { capteurs.add(c); }

    public void lancerReleve(Capteur c) throws AlerteConservationException {
        c.verifierSeuil();
    }

    public void lancerReleveTous() {
        for (Capteur c : capteurs) {
            try {
                c.verifierSeuil();
            } catch (AlerteConservationException e) {
                System.out.println("[ALERTE] " + e.getMessage());
            }
        }
    }

    public void verifierAlertes() {
        System.out.println("=== Verification des capteurs ===");
        lancerReleveTous();
    }
}
