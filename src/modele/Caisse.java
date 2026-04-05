package modele;

import exception.PaiementInvalideException;
import java.util.ArrayList;
import java.util.List;

public class Caisse {
    private double solde;
    private List<Amende> amendes;

    public Caisse() {
        this.solde   = 0.0;
        this.amendes = new ArrayList<>();
    }

    public Amende enregistrerAmende(Emprunt emprunt) {
        if (emprunt == null)
            throw new PaiementInvalideException("L'emprunt doit exister.");

        String idAmende = "AME" + String.format("%03d", amendes.size() + 1);
        Amende amende = new Amende(idAmende, emprunt);
        amendes.add(amende);
        System.out.println("[Caisse] Amende créée : " + amende);
        return amende;
    }

    public void payerAmende(Amende amende, double montant) {
        if (amende == null)
            throw new PaiementInvalideException("L'amende doit exister.");
        amende.payer(montant);
        calculerSolde();
        System.out.println("[Caisse] Paiement enregistré. Solde : " + solde + " FCFA");
    }

    private void calculerSolde() {
        double total = 0.0;
        for (Amende a : amendes)
            total += a.getMontantPaye();
        this.solde = total;
    }

    public List<Amende> getAmendesImpayees() {
        List<Amende> impayees = new ArrayList<>();
        for (Amende a : amendes)
            if (!a.getEstSoldee())
                impayees.add(a);
        return impayees;
    }

    public boolean aAmendeDue(String idMembre) {
        for (Amende a : amendes)
            if (a.getEmprunt().getMembre().getIdMembre().equals(idMembre) && !a.getEstSoldee())
                return true;
        return false;
    }

    public double getSolde()          { return solde; }
    public List<Amende> getAmendes()  { return new ArrayList<>(amendes); }

    @Override
    public String toString() {
        return "Caisse(solde= " + solde + " FCFA, amendes= " + amendes.size() +
               ", impayées= " + getAmendesImpayees().size() + ")";
    }
}
