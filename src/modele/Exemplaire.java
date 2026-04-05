package modele;

import exception.LivreInvalideException;

public class Exemplaire {
    private String idExemplaire;
    private EtatExemplaire etat;
    private Livre livre;

    public Exemplaire(String idExemplaire, Livre livre, EtatExemplaire etat) {
        this.idExemplaire = idExemplaire;
        this.livre = livre;
        this.etat = etat;
    }

    public String getIdExemplaire() { return idExemplaire; }
    public EtatExemplaire getEtat() { return etat; }
    public Livre getLivre() { return livre; }

    public void setEtat(EtatExemplaire etat) { this.etat = etat; }

    public boolean estDisponible() {
        return this.etat == EtatExemplaire.DISPONIBLE;
    }

    @Override
    public String toString() {
        return "Exemplaire #" + idExemplaire + " | " + etat + " | " + livre.getTitre();
    }
}
