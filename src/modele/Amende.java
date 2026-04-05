package modele;

import exception.PaiementInvalideException;

public class Amende {
    private String idAmende;
    private Emprunt emprunt;
    private double montantTotal;
    private double montantPaye;
    private boolean estSoldee;

    private static final double TARIF_PAR_JOUR = 0.5;

    public Amende() {}

    public Amende(String idAmende, Emprunt emprunt, double montantTotal, double montantPaye, boolean estSoldee) {
        if (idAmende == null || idAmende.isBlank())
            throw new PaiementInvalideException("L'ID de l'amende ne peut pas être vide.");
        if (emprunt == null)
            throw new PaiementInvalideException("L'emprunt associé ne peut pas être null.");

        this.idAmende     = idAmende;
        this.emprunt      = emprunt;
        this.montantTotal = montantTotal;
        this.montantPaye  = montantPaye;
        this.estSoldee    = estSoldee;
    }

    // Constructeur utilisé par Caisse pour créer une amende depuis un emprunt
    public Amende(String idAmende, Emprunt emprunt) {
        if (idAmende == null || idAmende.isBlank())
            throw new PaiementInvalideException("L'ID de l'amende ne peut pas être vide.");
        if (emprunt == null)
            throw new PaiementInvalideException("L'emprunt associé ne peut pas être null.");

        this.idAmende     = idAmende;
        this.emprunt      = emprunt;
        this.montantTotal = emprunt.calculerJoursRetard() * TARIF_PAR_JOUR;
        this.montantPaye  = 0.0;
        this.estSoldee    = false;
    }

    public double getMontantRestant() {
        return montantTotal - montantPaye;
    }

    public void payer(double montant) {
        if (montant <= 0)
            throw new PaiementInvalideException("Le montant doit être supérieur à 0.");
        if (montant > getMontantRestant())
            throw new PaiementInvalideException("Montant " + montant + " dépasse le restant : " + getMontantRestant());
        this.montantPaye += montant;
        if (this.montantPaye >= montantTotal)
            this.estSoldee = true;
    }

    public String getIdAmende()    { return idAmende; }
    public Emprunt getEmprunt()    { return emprunt; }
    public double getMontantTotal(){ return montantTotal; }
    public double getMontantPaye() { return montantPaye; }
    public boolean getEstSoldee()  { return estSoldee; }
    public boolean isEstSoldee()   { return estSoldee; }

    @Override
    public String toString() {
        return "Amende [" + idAmende + "] | Total : " + montantTotal +
               " | Payé : " + montantPaye +
               " | Statut : " + (estSoldee ? "Soldée" : "En attente");
    }
}
