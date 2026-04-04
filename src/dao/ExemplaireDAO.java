package dao;

import modele.Exemplaire;
import modele.EtatExemplaire;
import modele.Livre;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExemplaireDAO {

    private Connection conn;
    private LivreDAO livreDAO;

    public ExemplaireDAO() {
        this.conn = ConnexionDB.getConnection();
        this.livreDAO = new LivreDAO();
    }

    public void save(Exemplaire ex) {
        String sql = "INSERT INTO exemplaires (id_exemplaire, isbn_livre, etat) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ex.getIdExemplaire());
            ps.setString(2, ex.getLivre().getIsbn());
            ps.setString(3, ex.getEtat().name());
            ps.executeUpdate();
            System.out.println("[ExemplaireDAO] Sauvegardé : " + ex.getIdExemplaire());
        } catch (SQLException e) {
            System.out.println("[ExemplaireDAO] Erreur save : " + e.getMessage());
        }
    }

    public Exemplaire findById(String id) {
        String sql = "SELECT * FROM exemplaires WHERE id_exemplaire = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Livre livre = livreDAO.findById(rs.getString("isbn_livre"));
                EtatExemplaire etat = EtatExemplaire.valueOf(rs.getString("etat"));
                return new Exemplaire(rs.getString("id_exemplaire"), livre, etat);
            }
        } catch (SQLException e) {
            System.out.println("[ExemplaireDAO] Erreur findById : " + e.getMessage());
        }
        return null;
    }

    public List<Exemplaire> findAll() {
        List<Exemplaire> liste = new ArrayList<>();
        String sql = "SELECT * FROM exemplaires";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Livre livre = livreDAO.findById(rs.getString("isbn_livre"));
                EtatExemplaire etat = EtatExemplaire.valueOf(rs.getString("etat"));
                liste.add(new Exemplaire(rs.getString("id_exemplaire"), livre, etat));
            }
        } catch (SQLException e) {
            System.out.println("[ExemplaireDAO] Erreur findAll : " + e.getMessage());
        }
        return liste;
    }

    public void updateEtat(String id, EtatExemplaire nouvelEtat) {
        String sql = "UPDATE exemplaires SET etat = ? WHERE id_exemplaire = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nouvelEtat.name());
            ps.setString(2, id);
            ps.executeUpdate();
            System.out.println("[ExemplaireDAO] Etat mis à jour : " + id + " -> " + nouvelEtat);
        } catch (SQLException e) {
            System.out.println("[ExemplaireDAO] Erreur updateEtat : " + e.getMessage());
        }
    }

    public void delete(String id) {
        String sql = "DELETE FROM exemplaires WHERE id_exemplaire = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("[ExemplaireDAO] Erreur delete : " + e.getMessage());
        }
    }
}
