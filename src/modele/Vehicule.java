package modele;

import exception.VehiculeIndisponibleException;

public class Vehicule {

    private String immatriculation;
    private String modele;
    private double kilometrage;
    private boolean enService;

    public Vehicule(String immatriculation, String modele) {

        if (immatriculation == null || immatriculation.isEmpty()) {
            throw new IllegalArgumentException("Immatriculation invalide");
        }

        this.immatriculation = immatriculation;
        this.modele = modele;
        this.kilometrage = 0;
        this.enService = true;
    }

    public Vehicule(String immatriculation, String modele, double kilometrage, boolean enService) {
        this(immatriculation, modele);
        this.kilometrage = kilometrage;
        this.enService = enService;
    }

    public void effectuerLivraison(String dep, String arr, int livres) throws VehiculeIndisponibleException {

        if (!enService) throw new VehiculeIndisponibleException("Vehicule " + immatriculation + " Indisponible !");
        this.kilometrage = 50;
        System.out.println("[LIVRAISON] " + immatriculation + " : " + dep + " -> " + arr + " (" + livres + " livres)");
    }

    public void effectuerLivraison(String dep, String arr, double livres) throws VehiculeIndisponibleException {
        effectuerLivraison(dep, arr, (int) livres);
    }

    public void mettreEnMaintenance() {
        this.enService = false;
        System.out.println("[MAINTENANCE] " + immatriculation + " mis en maintenance.");
    }

    public String getImmatriculation() { 
        return immatriculation; 
    }

    public String getModele() { 
        return modele; 
    }

    public double getKilometrage() { 
        return kilometrage; 
    }

    public boolean isEnService() { 
        return enService; 
    }


    @Override
    public String toString() {
        return "Vehicule{" + immatriculation + ", " + modele + ", km=" + kilometrage + ", enService=" + enService + "}";
    }
}
