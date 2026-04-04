import dao.*;
import modele.*;
import gestion.*;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        // ── ÉTAPE 0 : Initialiser la base ──────────────────────────
        InitialisationDB.creerTables();

        // Instancier tous les DAO
        LivreDAO livreDAO         = new LivreDAO();
        ExemplaireDAO exemDAO     = new ExemplaireDAO();
        MembreDAO membreDAO       = new MembreDAO();
        EmpruntDAO empruntDAO     = new EmpruntDAO();
        AmendeDAO amendeDAO       = new AmendeDAO();
        VehiculeDAO vehiculeDAO   = new VehiculeDAO();
        ReleveDAO releveDAO       = new ReleveDAO();

        // Instancier les classes de gestion (codées par M2, M3, M4)
        GestionEmprunts gestionEmprunts = new GestionEmprunts();
        GestionAmendes gestionAmendes   = new GestionAmendes();
        GestionCapteurs gestionCapteurs = new GestionCapteurs();

        System.out.println("\n========== DÉMARRAGE DU SYSTÈME ==========\n");

        // ── ÉTAPE 1 : Ajouter des livres ───────────────────────────
        System.out.println("--- Étape 1 : Ajout des livres ---");
        Livre l1 = new Livre("978-2-07-036024-5", "Le Petit Prince", "Saint-Exupéry", 1943, "Roman");
        Livre l2 = new Livre("978-2-07-040850-4", "L'Étranger", "Albert Camus", 1942, "Roman");
        livreDAO.save(l1);
        livreDAO.save(l2);

        Exemplaire ex1 = new Exemplaire("EX001", l1, EtatExemplaire.DISPONIBLE);
        Exemplaire ex2 = new Exemplaire("EX002", l2, EtatExemplaire.DISPONIBLE);
        exemDAO.save(ex1);
        exemDAO.save(ex2);

        System.out.println("Livres en base : " + livreDAO.findAll().size());

        // ── ÉTAPE 2 : Inscrire des membres ─────────────────────────
        System.out.println("\n--- Étape 2 : Inscription des membres ---");
        Membre m1 = new Membre("M001", "Diallo", "Amadou", "amadou@email.com", LocalDate.now());
        Membre m2 = new Membre("M002", "Sow", "Fatou", "fatou@email.com", LocalDate.now());
        membreDAO.save(m1);
        membreDAO.save(m2);

        System.out.println("Membres en base : " + membreDAO.findAll().size());

        // ── ÉTAPE 3 : Emprunter un livre ───────────────────────────
        System.out.println("\n--- Étape 3 : Emprunt ---");
        try {
            Emprunt emp1 = gestionEmprunts.emprunterLivre(m1, ex1);
            empruntDAO.save(emp1);
            exemDAO.updateEtat(ex1.getIdExemplaire(), EtatExemplaire.EMPRUNTE);
            System.out.println("Emprunt enregistré : " + emp1.getIdEmprunt());
            System.out.println("État exemplaire EX001 : " + exemDAO.findById("EX001").getEtat());
        } catch (Exception e) {
            System.out.println("Erreur emprunt : " + e.getMessage());
        }

        // ── ÉTAPE 4 : Retour en retard ─────────────────────────────
        System.out.println("\n--- Étape 4 : Retour en retard ---");
        Emprunt emp1 = empruntDAO.findById("EMP001");
        if (emp1 != null) {
            // On simule un retour avec 5 jours de retard
            emp1.setDateRetourReelle(emp1.getDateRetourPrevue().plusDays(5));
            empruntDAO.update(emp1);
            exemDAO.updateEtat(ex1.getIdExemplaire(), EtatExemplaire.DISPONIBLE);
            System.out.println("Retour enregistré. Jours de retard : " + emp1.calculerJoursRetard());
        }

        // ── ÉTAPE 5 : Générer et payer une amende ─────────────────
        System.out.println("\n--- Étape 5 : Amende ---");
        if (emp1 != null && emp1.estEnRetard()) {
            Amende amende = gestionAmendes.creerAmende(emp1);
            amendeDAO.save(amende);
            System.out.println("Amende créée : " + amende.getMontantTotal() + "€");

            // Paiement partiel
            amende.payer(1.0);
            amendeDAO.update(amende);
            System.out.println("Après paiement partiel - Restant : " + amende.getMontantRestant() + "€");

            // Paiement total
            amende.payer(amende.getMontantRestant());
            amendeDAO.update(amende);
            System.out.println("Amende soldée : " + amende.isEstSoldee());
        }

        // ── ÉTAPE 6 : Livraison inter-annexes ──────────────────────
        System.out.println("\n--- Étape 6 : Livraison ---");
        Vehicule v1 = new Vehicule("AB-123-CD", "Renault Kangoo", 15000, true);
        vehiculeDAO.save(v1);
        try {
            v1.effectuerLivraison("Annexe Nord", "Annexe Sud", 25.0);
            vehiculeDAO.update(v1);
            System.out.println("Livraison effectuée. Kilométrage : " + v1.getKilometrage());
        } catch (Exception e) {
            System.out.println("Erreur livraison : " + e.getMessage());
        }

        // ── ÉTAPE 7 : Relevé de capteur ────────────────────────────
        System.out.println("\n--- Étape 7 : Capteurs ---");
        CapteurTemperature capteur = new CapteurTemperature("CAP001", "Salle principale");
        try {
            gestionCapteurs.lancerReleve(capteur);
            Releve releve = capteur.getDernierReleve();
            releveDAO.save(capteur.getIdCapteur(), "Temperature", releve);
            System.out.println("Relevé enregistré : " + releve.getValeur() + "°C");
        } catch (Exception e) {
            System.out.println("Alerte capteur : " + e.getMessage());
        }

        // ── FIN ────────────────────────────────────────────────────
        System.out.println("\n========== FIN DU SCÉNARIO ==========");
        System.out.println("Amendes impayées : " + amendeDAO.findImpayees().size());
        System.out.println("Total emprunts en base : " + empruntDAO.findAll().size());

        ConnexionDB.fermer();
    }
}
