package dao;

import modele.Emprunt;
import modele.Exemplaire;
import modele.Membre;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmpruntDAO {

    private Connection conn;
    private MembreDAO membreDAO;
    private ExemplaireDAO exemplaireDAO;

    public EmpruntDAO() {
        this.conn = ConnexionDB.getConnection();
        this.membreDAO = new MembreDAO();
        this.exemplaireDAO = new ExemplaireDAO();
    }

    public void save(Emprunt emprunt) {
        String sql = "INSERT INTO emprunts (id_emprunt, id_membre, id_exemplaire, " +
                     "date_emprunt, date_retour_prevue, date_retour_reelle) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, emprunt.getIdEmprunt());
            ps.setString(2, emprunt.getMembre().getIdMembre());
            ps.setString(3, emprunt.getExemplaire().getIdExemplaire());
            ps.setString(4, emprunt.getDateEmprunt().toString());
            ps.setString(5, emprunt.getDateRetourPrevue().toString());
            // La date réelle peut être null si le livre n'est pas encore rendu
            if (emprunt.getDateRetourReelle() != null) {
                ps.setString(6, emprunt.getDateRetourReelle().toString());
            } else {
                ps.setNull(6, Types.VARCHAR);
            }
            ps.executeUpdate();
            System.out.println("[EmpruntDAO] Sauvegardé : " + emprunt.getIdEmprunt());
        } catch (SQLException e) {
            System.out.println("[EmpruntDAO] Erreur save : " + e.getMessage());
        }
    }

    public Emprunt findById(String id) {
        String sql = "SELECT * FROM emprunts WHERE id_emprunt = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return construireEmprunt(rs);
            }
        } catch (SQLException e) {
            System.out.println("[EmpruntDAO] Erreur findById : " + e.getMessage());
        }
        return null;
    }

    public List<Emprunt> findAll() {
        List<Emprunt> liste = new ArrayList<>();
        String sql = "SELECT * FROM emprunts";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                liste.add(construireEmprunt(rs));
            }
        } catch (SQLException e) {
            System.out.println("[EmpruntDAO] Erreur findAll : " + e.getMessage());
        }
        return liste;
    }

    // Récupérer tous les emprunts d'un membre — utile pour le Main
    public List<Emprunt> findByMembre(String idMembre) {
        List<Emprunt> liste = new ArrayList<>();
        String sql = "SELECT * FROM emprunts WHERE id_membre = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idMembre);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                liste.add(construireEmprunt(rs));
            }
        } catch (SQLException e) {
            System.out.println("[EmpruntDAO] Erreur findByMembre : " + e.getMessage());
        }
        return liste;
    }

    // Mettre à jour la date de retour réelle quand le livre est rendu
    public void update(Emprunt emprunt) {
        String sql = "UPDATE emprunts SET date_retour_reelle = ? WHERE id_emprunt = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            if (emprunt.getDateRetourReelle() != null) {
                ps.setString(1, emprunt.getDateRetourReelle().toString());
            } else {
                ps.setNull(1, Types.VARCHAR);
            }
            ps.setString(2, emprunt.getIdEmprunt());
            ps.executeUpdate();
            System.out.println("[EmpruntDAO] Mis à jour : " + emprunt.getIdEmprunt());
        } catch (SQLException e) {
            System.out.println("[EmpruntDAO] Erreur update : " + e.getMessage());
        }
    }

    public void delete(String id) {
        String sql = "DELETE FROM emprunts WHERE id_emprunt = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("[EmpruntDAO] Erreur delete : " + e.getMessage());
        }
    }

    // Méthode privée pour reconstruire un Emprunt depuis une ligne SQL
    private Emprunt construireEmprunt(ResultSet rs) throws SQLException {
        Membre membre = membreDAO.findById(rs.getString("id_membre"));
        Exemplaire exemplaire = exemplaireDAO.findById(rs.getString("id_exemplaire"));
        LocalDate dateEmprunt = LocalDate.parse(rs.getString("date_emprunt"));
        LocalDate dateRetourPrevue = LocalDate.parse(rs.getString("date_retour_prevue"));

        // La date réelle peut être NULL en base si pas encore rendu
        String dateReelleStr = rs.getString("date_retour_reelle");
        LocalDate dateRetourReelle = (dateReelleStr != null) ? LocalDate.parse(dateReelleStr) : null;

        return new Emprunt(
            rs.getString("id_emprunt"),
            membre,
            exemplaire,
            dateEmprunt,
            dateRetourPrevue,
            dateRetourReelle
        );
    }
}
