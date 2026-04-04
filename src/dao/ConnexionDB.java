package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionDB {

    private static final String URL = "jdbc:sqlite:bibliotheque.db";
    private static Connection instance = null;

    private ConnexionDB() {}

    public static Connection getConnection() {
        if (instance == null) {
            try {
                instance = DriverManager.getConnection(URL);
                System.out.println("[DB] Connexion OK");
            } catch (SQLException e) {
                System.out.println("[DB] Erreur : " + e.getMessage());
            }
        }
        return instance;
    }

    public static void fermer() {
        if (instance != null) {
            try {
                instance.close();
                instance = null;
            } catch (SQLException e) {
                System.out.println("[DB] Erreur fermeture : " + e.getMessage());
            }
        }
    }
}
