package gestion;

import modele.Membre;
import modele.Exemplaire;
import modele.Emprunt;
import modele.EtatExemplaire;
import exception.EmpruntImpossibleException;
import java.time.LocalDate;
import java.util.UUID;

public class GestionEmprunts {

    public Emprunt emprunterLivre(Membre membre, Exemplaire exemplaire) throws EmpruntImpossibleException {
        if (!membre.peutEmprunter())
            throw new EmpruntImpossibleException("Le membre a atteint la limite de 3 emprunts.");
        if (!exemplaire.estDisponible())
            throw new EmpruntImpossibleException("L'exemplaire n'est pas disponible.");

        exemplaire.setEtat(EtatExemplaire.EMPRUNTE);
        membre.ajouterEmprunt(exemplaire);

        String idEmprunt = "EMP" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        return new Emprunt(idEmprunt, membre, exemplaire,
                           LocalDate.now(),
                           LocalDate.now().plusDays(14),
                           null);
    }

    public void retournerLivre(Emprunt emprunt) {
        emprunt.setDateRetourReelle(LocalDate.now());
        emprunt.getExemplaire().setEtat(EtatExemplaire.DISPONIBLE);
        emprunt.getMembre().retirerEmprunt(emprunt.getExemplaire());
        System.out.println("[GestionEmprunts] Retour enregistré : " + emprunt.getIdEmprunt());
    }
}
