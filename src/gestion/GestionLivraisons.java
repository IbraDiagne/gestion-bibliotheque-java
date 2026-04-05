package gestion;
import modele.*;
import exception.*;
import java.util.ArrayList;

public class GestionLivraisons {

    private ArrayList<Vehicule> vehicules;

    public GestionLivraisons() {
        this.vehicules = new ArrayList<>();
    }

    public void ajouterVehicule(Vehicule v) { vehicules.add(v); }

    public void planifierLivraison(String dep, String arr, int livres) {
        for (Vehicule v : vehicules) {

            if (v.isEnService()) {
                try {
                    v.effectuerLivraison(dep, arr, livres);
                    return;
                } catch (VehiculeIndisponibleException e) {
                    System.out.println(e.getMessage());
                }
            }
            
        }
        System.out.println("[ERREUR] Aucun vehicule disponible !");
    }

    public ArrayList<Vehicule> getTournees() { return vehicules; }
}