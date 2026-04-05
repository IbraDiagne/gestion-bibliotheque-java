public class Exemplaire {
    private int idExemplaire;
    private EtatExemplaire etat;
    private Livre livre;

    public Exemplaire(int idExemplaire, Livre livre) {
        this.idExemplaire = idExemplaire;
        this.livre = livre;
        this.etat = EtatExemplaire.DISPONIBLE;
    }

    public int getIdExemplaire() { return idExemplaire; }
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