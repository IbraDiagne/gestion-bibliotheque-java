package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class InitialisationDB {

    public static void creerTables() {
        Connection conn = ConnexionDB.getConnection();

        String livres =
            "CREATE TABLE IF NOT EXISTS livres (" +
            "isbn TEXT PRIMARY KEY, " +
            "titre TEXT NOT NULL, " +
            "auteur TEXT NOT NULL, " +
            "annee INTEGER, " +
            "genre TEXT)";

        String exemplaires =
            "CREATE TABLE IF NOT EXISTS exemplaires (" +
            "id_exemplaire TEXT PRIMARY KEY, " +
            "isbn_livre TEXT NOT NULL, " +
            "etat TEXT NOT NULL, " +
            "FOREIGN KEY (isbn_livre) REFERENCES livres(isbn))";

        String membres =
            "CREATE TABLE IF NOT EXISTS membres (" +
            "id_membre TEXT PRIMARY KEY, " +
            "nom TEXT NOT NULL, " +
            "prenom TEXT NOT NULL, " +
            "email TEXT NOT NULL, " +
            "date_inscription TEXT NOT NULL)";

        String emprunts =
            "CREATE TABLE IF NOT EXISTS emprunts (" +
            "id_emprunt TEXT PRIMARY KEY, " +
            "id_membre TEXT NOT NULL, " +
            "id_exemplaire TEXT NOT NULL, " +
            "date_emprunt TEXT NOT NULL, " +
            "date_retour_prevue TEXT NOT NULL, " +
            "date_retour_reelle TEXT, " +
            "FOREIGN KEY (id_membre) REFERENCES membres(id_membre), " +
            "FOREIGN KEY (id_exemplaire) REFERENCES exemplaires(id_exemplaire))";

        String amendes =
            "CREATE TABLE IF NOT EXISTS amendes (" +
            "id_amende TEXT PRIMARY KEY, " +
            "id_emprunt TEXT NOT NULL, " +
            "montant_total REAL NOT NULL, " +
            "montant_paye REAL DEFAULT 0.0, " +
            "est_soldee INTEGER DEFAULT 0, " +
            "FOREIGN KEY (id_emprunt) REFERENCES emprunts(id_emprunt))";

        String vehicules =
            "CREATE TABLE IF NOT EXISTS vehicules (" +
            "immatriculation TEXT PRIMARY KEY, " +
            "modele TEXT NOT NULL, " +
            "kilometrage REAL DEFAULT 0.0, " +
            "en_service INTEGER DEFAULT 1)";

        String releves =
            "CREATE TABLE IF NOT EXISTS releves (" +
            "id_releve TEXT PRIMARY KEY, " +
            "id_capteur TEXT NOT NULL, " +
            "type_capteur TEXT NOT NULL, " +
            "valeur REAL NOT NULL, " +
            "horodatage TEXT NOT NULL)";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON");
            stmt.execute(livres);
            stmt.execute(exemplaires);
            stmt.execute(membres);
            stmt.execute(emprunts);
            stmt.execute(amendes);
            stmt.execute(vehicules);
            stmt.execute(releves);
            System.out.println("[DB] Tables créées.");
        } catch (SQLException e) {
            System.out.println("[DB] Erreur tables : " + e.getMessage());
        }
    }
}
