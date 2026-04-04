package modele;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Emprunt {

    private String idEmprunt;
    private Membre membre;
    private Object exemplaire;        // sera typé Exemplaire quand M1 fusionne
    private LocalDate dateEmprunt;
    private LocalDate dateRetourPrevue;
    private LocalDate dateRetourReelle;
    public Emprunt(String idEmprunt, Membre membre, Object exemplaire) {

        this.idEmprunt = idEmprunt;
        this.membre = membre;
        this.exemplaire = exemplaire;
        this.dateEmprunt = LocalDate.now();
        this.dateRetourPrevue = LocalDate.now().plusDays(14);
        this.dateRetourReelle = null;
    }
    public boolean estEnRetard() {
        return dateRetourReelle == null && 
               LocalDate.now().isAfter(dateRetourPrevue);
    }

    public long calculerJoursRetard() {
        if (dateRetourReelle == null) {
            return ChronoUnit.DAYS.between(dateRetourPrevue, LocalDate.now());
        }
        return ChronoUnit.DAYS.between(dateRetourPrevue, dateRetourReelle);
    }

    public void setDateRetourReelle(LocalDate date) {
        this.dateRetourReelle = date;
    }
    public String getIdEmprunt() { return idEmprunt; }
    public Membre getMembre() { return membre; }
    public Object getExemplaire() { return exemplaire; }
    public LocalDate getDateEmprunt() { return dateEmprunt; }
    public LocalDate getDateRetourPrevue() { return dateRetourPrevue; }
    public LocalDate getDateRetourReelle() { return dateRetourReelle; }

    @Override
    public String toString() {
        return "[" + idEmprunt + "]" +
               " Membre : " + membre.getNom() +
               " | Date emprunt : " + dateEmprunt +
               " | Retour prévu : " + dateRetourPrevue +
               " | En retard : " + (estEnRetard() ? "OUI" : "NON");
    }
}