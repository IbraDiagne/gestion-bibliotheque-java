package dao;

import modele.Membre;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MembreDAO {

    private Connection conn;

    public MembreDAO() {
        this.conn = ConnexionDB.getConnection();
    }

    public void save(Membre membre) {
        String sql = "INSERT INTO membres (id_membre, nom, prenom, email, date_inscription) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, membre.getIdMembre());
            ps.setString(2, membre.getNom());
            ps.setString(3, membre.getPrenom());
            ps.setString(4, membre.getEmail());
            ps.setString(5, membre.getDateInscription().toString());
            ps.executeUpdate();
            System.out.println("[MembreDAO] Sauvegardé : " + membre.getNom());
        } catch (SQLException e) {
            System.out.println("[MembreDAO] Erreur save : " + e.getMessage());
        }
    }

    public Membre findById(String id) {
        String sql = "SELECT * FROM membres WHERE id_membre = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
		if (rs.next()) {
    try {
        return new Membre(
            rs.getString("id_membre"),
            rs.getString("nom"),
            rs.getString("prenom"),
            rs.getString("email"),
            LocalDate.parse(rs.getString("date_inscription"))
        );
    } catch (exception.MembreInvalideException e) {
        System.out.println("[MembreDAO] Données invalides : " + e.getMessage());
    }
}

        } catch (SQLException e) {
            System.out.println("[MembreDAO] Erreur findById : " + e.getMessage());
        }
        return null;
    }

    public List<Membre> findAll() {
        List<Membre> liste = new ArrayList<>();
        String sql = "SELECT * FROM membres";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

while (rs.next()) {
    try {
        liste.add(new Membre(
            rs.getString("id_membre"),
            rs.getString("nom"),
            rs.getString("prenom"),
            rs.getString("email"),
            LocalDate.parse(rs.getString("date_inscription"))
        ));
    } catch (exception.MembreInvalideException e) {
        System.out.println("[MembreDAO] Données invalides : " + e.getMessage());
    }
}
        } catch (SQLException e) {
            System.out.println("[MembreDAO] Erreur findAll : " + e.getMessage());
        }
        return liste;
    }

    public void update(Membre membre) {
        String sql = "UPDATE membres SET nom = ?, prenom = ?, email = ? WHERE id_membre = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, membre.getNom());
            ps.setString(2, membre.getPrenom());
            ps.setString(3, membre.getEmail());
            ps.setString(4, membre.getIdMembre());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("[MembreDAO] Erreur update : " + e.getMessage());
        }
    }

    public void delete(String id) {
        String sql = "DELETE FROM membres WHERE id_membre = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("[MembreDAO] Erreur delete : " + e.getMessage());
        }
    }
}
