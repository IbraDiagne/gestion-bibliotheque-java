package dao;

import modele.Vehicule;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehiculeDAO {

    private Connection conn;

    public VehiculeDAO() {
        this.conn = ConnexionDB.getConnection();
    }

    public void save(Vehicule vehicule) {
        String sql = "INSERT INTO vehicules (immatriculation, modele, kilometrage, en_service) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, vehicule.getImmatriculation());
            ps.setString(2, vehicule.getModele());
            ps.setDouble(3, vehicule.getKilometrage());
            ps.setInt(4, vehicule.isEnService() ? 1 : 0);
            ps.executeUpdate();
            System.out.println("[VehiculeDAO] Sauvegardé : " + vehicule.getImmatriculation());
        } catch (SQLException e) {
            System.out.println("[VehiculeDAO] Erreur save : " + e.getMessage());
        }
    }

    public Vehicule findById(String immatriculation) {
        String sql = "SELECT * FROM vehicules WHERE immatriculation = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, immatriculation);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Vehicule(
                    rs.getString("immatriculation"),
                    rs.getString("modele"),
                    rs.getDouble("kilometrage"),
                    rs.getInt("en_service") == 1
                );
            }
        } catch (SQLException e) {
            System.out.println("[VehiculeDAO] Erreur findById : " + e.getMessage());
        }
        return null;
    }

    public List<Vehicule> findAll() {
        List<Vehicule> liste = new ArrayList<>();
        String sql = "SELECT * FROM vehicules";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                liste.add(new Vehicule(
                    rs.getString("immatriculation"),
                    rs.getString("modele"),
                    rs.getDouble("kilometrage"),
                    rs.getInt("en_service") == 1
                ));
            }
        } catch (SQLException e) {
            System.out.println("[VehiculeDAO] Erreur findAll : " + e.getMessage());
        }
        return liste;
    }

    public void update(Vehicule vehicule) {
        String sql = "UPDATE vehicules SET kilometrage = ?, en_service = ? WHERE immatriculation = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, vehicule.getKilometrage());
            ps.setInt(2, vehicule.isEnService() ? 1 : 0);
            ps.setString(3, vehicule.getImmatriculation());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("[VehiculeDAO] Erreur update : " + e.getMessage());
        }
    }

    public void delete(String immatriculation) {
        String sql = "DELETE FROM vehicules WHERE immatriculation = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, immatriculation);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("[VehiculeDAO] Erreur delete : " + e.getMessage());
        }
    }
}
