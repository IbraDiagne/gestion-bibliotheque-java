package modele;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import exception.MembreInvalideException;

public class Membre {

    private String idMembre;
    private String nom;
    private String prenom;
    private String email;
    private LocalDate dateInscription;
    private List<Object> empruntsActifs; // sera typé Emprunt plus tard

    public Membre(String idMembre, String nom, String prenom, String email) 
                  throws MembreInvalideException {

        if (nom == null || nom.trim().isEmpty()) {
            throw new MembreInvalideException("Le nom ne peut pas être vide.");
        }
        if (prenom == null || prenom.trim().isEmpty()) {
            throw new MembreInvalideException("Le prénom ne peut pas être vide.");
        }
        if (email == null || !email.contains("@")) {
            throw new MembreInvalideException("Email invalide : " + email);
        }

        this.idMembre = idMembre;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.dateInscription = LocalDate.now();
        this.empruntsActifs = new ArrayList<>();
    }

    public boolean peutEmprunter() {
        return empruntsActifs.size() < 3;
    }

    public void ajouterEmprunt(Object emprunt) {
        empruntsActifs.add(emprunt);
    }

    public void retirerEmprunt(Object emprunt) {
        empruntsActifs.remove(emprunt);
    }

    public String getIdMembre() { return idMembre; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getEmail() { return email; }
    public LocalDate getDateInscription() { return dateInscription; }
    public List<Object> getEmpruntsActifs() { return empruntsActifs; }

    @Override
    public String toString() {
        return "[" + idMembre + "] " + prenom + " " + nom + 
               " | Email : " + email + 
               " | Inscrit le : " + dateInscription + 
               " | Emprunts actifs : " + empruntsActifs.size();
    }
}