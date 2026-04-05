package dao;

import modele.Livre;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivreDAO {

    private Connection conn;

    public LivreDAO() {
        this.conn = ConnexionDB.getConnection();
    }

    public void save(Livre livre) {
        String sql = "INSERT INTO livres (isbn, titre, auteur, annee, genre) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, livre.getIsbn());
            ps.setString(2, livre.getTitre());
            ps.setString(3, livre.getAuteur());
            ps.setInt(4, livre.getAnneePublication());
            ps.setString(5, livre.getGenre());
            ps.executeUpdate();
            System.out.println("[LivreDAO] Sauvegardé : " + livre.getTitre());
        } catch (SQLException e) {
            System.out.println("[LivreDAO] Erreur save : " + e.getMessage());
        }
    }

    public Livre findById(String isbn) {
        String sql = "SELECT * FROM livres WHERE isbn = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, isbn);
            ResultSet rs = ps.executeQuery();
	    if (rs.next()) {
    try {
        return new Livre(
            rs.getString("isbn"),
            rs.getString("titre"),
            rs.getString("auteur"),
            rs.getInt("annee"),
            rs.getString("genre")
        );
    } catch (exception.LivreInvalideException e) {
        System.out.println("[LivreDAO] Données invalides : " + e.getMessage());
    }
}

        } catch (SQLException e) {
            System.out.println("[LivreDAO] Erreur findById : " + e.getMessage());
        }
        return null;
    }

    public List<Livre> findAll() {
        List<Livre> liste = new ArrayList<>();
        String sql = "SELECT * FROM livres";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
while (rs.next()) {
    try {
        liste.add(new Livre(
            rs.getString("isbn"),
            rs.getString("titre"),
            rs.getString("auteur"),
            rs.getInt("annee"),
            rs.getString("genre")
        ));
    } catch (exception.LivreInvalideException e) {
        System.out.println("[LivreDAO] Données invalides : " + e.getMessage());
    }
}

        } catch (SQLException e) {
            System.out.println("[LivreDAO] Erreur findAll : " + e.getMessage());
        }
        return liste;
    }

    public void update(Livre livre) {
        String sql = "UPDATE livres SET titre = ?, auteur = ?, annee = ?, genre = ? WHERE isbn = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, livre.getTitre());
            ps.setString(2, livre.getAuteur());
            ps.setInt(3, livre.getAnneePublication());
            ps.setString(4, livre.getGenre());
            ps.setString(5, livre.getIsbn());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("[LivreDAO] Erreur update : " + e.getMessage());
        }
    }

    public void delete(String isbn) {
        String sql = "DELETE FROM livres WHERE isbn = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, isbn);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("[LivreDAO] Erreur delete : " + e.getMessage());
        }
    }
}
