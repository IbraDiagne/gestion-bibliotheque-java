package modele;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Emprunt {

    private String idEmprunt;
    private Membre membre;
    private Exemplaire exemplaire;
    private LocalDate dateEmprunt;
    private LocalDate dateRetourPrevue;
    private LocalDate dateRetourReelle;

    public Emprunt(String idEmprunt, Membre membre, Exemplaire exemplaire,
                   LocalDate dateEmprunt, LocalDate dateRetourPrevue, LocalDate dateRetourReelle) {
        this.idEmprunt        = idEmprunt;
        this.membre           = membre;
        this.exemplaire       = exemplaire;
        this.dateEmprunt      = dateEmprunt;
        this.dateRetourPrevue = dateRetourPrevue;
        this.dateRetourReelle = dateRetourReelle;
    }

    public boolean estEnRetard() {
        if (dateRetourReelle != null) {
            return dateRetourReelle.isAfter(dateRetourPrevue);
        }
        return LocalDate.now().isAfter(dateRetourPrevue);
    }

    public long calculerJoursRetard() {
        if (dateRetourReelle != null) {
            return ChronoUnit.DAYS.between(dateRetourPrevue, dateRetourReelle);
        }
        return ChronoUnit.DAYS.between(dateRetourPrevue, LocalDate.now());
    }

    public void setDateRetourReelle(LocalDate date) { this.dateRetourReelle = date; }

    public String getIdEmprunt()             { return idEmprunt; }
    public Membre getMembre()                { return membre; }
    public Exemplaire getExemplaire()        { return exemplaire; }
    public LocalDate getDateEmprunt()        { return dateEmprunt; }
    public LocalDate getDateRetourPrevue()   { return dateRetourPrevue; }
    public LocalDate getDateRetourReelle()   { return dateRetourReelle; }

    @Override
    public String toString() {
        return "[" + idEmprunt + "]" +
               " Membre : " + membre.getNom() +
               " | Date emprunt : " + dateEmprunt +
               " | Retour prévu : " + dateRetourPrevue +
               " | En retard : " + (estEnRetard() ? "OUI" : "NON");
    }
}
