package dao;

import modele.Amende;
import modele.Emprunt;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AmendeDAO {

    private Connection conn;
    private EmpruntDAO empruntDAO;

    public AmendeDAO() {
        this.conn = ConnexionDB.getConnection();
        this.empruntDAO = new EmpruntDAO();
    }

    public void save(Amende amende) {
        String sql = "INSERT INTO amendes (id_amende, id_emprunt, montant_total, montant_paye, est_soldee) " +
                     "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, amende.getIdAmende());
            ps.setString(2, amende.getEmprunt().getIdEmprunt());
            ps.setDouble(3, amende.getMontantTotal());
            ps.setDouble(4, amende.getMontantPaye());
            // boolean Java → INTEGER SQLite : true = 1, false = 0
            ps.setInt(5, amende.isEstSoldee() ? 1 : 0);
            ps.executeUpdate();
            System.out.println("[AmendeDAO] Sauvegardée : " + amende.getIdAmende());
        } catch (SQLException e) {
            System.out.println("[AmendeDAO] Erreur save : " + e.getMessage());
        }
    }

    public Amende findById(String id) {
        String sql = "SELECT * FROM amendes WHERE id_amende = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return construireAmende(rs);
            }
        } catch (SQLException e) {
            System.out.println("[AmendeDAO] Erreur findById : " + e.getMessage());
        }
        return null;
    }

    public List<Amende> findAll() {
        List<Amende> liste = new ArrayList<>();
        String sql = "SELECT * FROM amendes";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                liste.add(construireAmende(rs));
            }
        } catch (SQLException e) {
            System.out.println("[AmendeDAO] Erreur findAll : " + e.getMessage());
        }
        return liste;
    }

    // Récupérer uniquement les amendes non payées
    public List<Amende> findImpayees() {
        List<Amende> liste = new ArrayList<>();
        String sql = "SELECT * FROM amendes WHERE est_soldee = 0";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                liste.add(construireAmende(rs));
            }
        } catch (SQLException e) {
            System.out.println("[AmendeDAO] Erreur findImpayees : " + e.getMessage());
        }
        return liste;
    }

    // Mettre à jour le montant payé et l'état soldée après un paiement
    public void update(Amende amende) {
        String sql = "UPDATE amendes SET montant_paye = ?, est_soldee = ? WHERE id_amende = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, amende.getMontantPaye());
            ps.setInt(2, amende.isEstSoldee() ? 1 : 0);
            ps.setString(3, amende.getIdAmende());
            ps.executeUpdate();
            System.out.println("[AmendeDAO] Mis à jour : " + amende.getIdAmende());
        } catch (SQLException e) {
            System.out.println("[AmendeDAO] Erreur update : " + e.getMessage());
        }
    }

    public void delete(String id) {
        String sql = "DELETE FROM amendes WHERE id_amende = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("[AmendeDAO] Erreur delete : " + e.getMessage());
        }
    }

    private Amende construireAmende(ResultSet rs) throws SQLException {
        Emprunt emprunt = empruntDAO.findById(rs.getString("id_emprunt"));
        double montantTotal = rs.getDouble("montant_total");
        double montantPaye = rs.getDouble("montant_paye");
        // INTEGER SQLite → boolean Java : 1 = true, 0 = false
        boolean estSoldee = rs.getInt("est_soldee") == 1;
        return new Amende(rs.getString("id_amende"), emprunt, montantTotal, montantPaye, estSoldee);
    }
}
