package dao;

import modele.Releve;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReleveDAO {

    private Connection conn;

    public ReleveDAO() {
        this.conn = ConnexionDB.getConnection();
    }

    public void save(String idCapteur, String typeCapteur, Releve releve) {
        String sql = "INSERT INTO releves (id_releve, id_capteur, type_capteur, valeur, horodatage) " +
                     "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, releve.getIdReleve());
            ps.setString(2, idCapteur);
            ps.setString(3, typeCapteur);
            ps.setDouble(4, releve.getValeur());
            ps.setString(5, releve.getHorodatage().toString());
            ps.executeUpdate();
            System.out.println("[ReleveDAO] Relevé sauvegardé : capteur " + idCapteur);
        } catch (SQLException e) {
            System.out.println("[ReleveDAO] Erreur save : " + e.getMessage());
        }
    }

    public List<Releve> findByCapteur(String idCapteur) {
        List<Releve> liste = new ArrayList<>();
        String sql = "SELECT * FROM releves WHERE id_capteur = ? ORDER BY horodatage";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idCapteur);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                liste.add(new Releve(
                    rs.getString("id_releve"),
                    rs.getDouble("valeur"),
                    LocalDateTime.parse(rs.getString("horodatage"))
                ));
            }
        } catch (SQLException e) {
            System.out.println("[ReleveDAO] Erreur findByCapteur : " + e.getMessage());
        }
        return liste;
    }

    public List<Releve> findAll() {
        List<Releve> liste = new ArrayList<>();
        String sql = "SELECT * FROM releves ORDER BY horodatage";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                liste.add(new Releve(
                    rs.getString("id_releve"),
                    rs.getDouble("valeur"),
                    LocalDateTime.parse(rs.getString("horodatage"))
                ));
            }
        } catch (SQLException e) {
            System.out.println("[ReleveDAO] Erreur findAll : " + e.getMessage());
        }
        return liste;
    }
}
