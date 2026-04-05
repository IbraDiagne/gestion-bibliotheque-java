import java.util.ArrayList;
import java.util.List;

public class Catalogue {
    private List<Livre> livres = new ArrayList<>();

    public void ajouterLivre(Livre livre) throws LivreInvalideException {
        for (Livre l : livres) {
            if (l.getIsbn().equals(livre.getIsbn()))
                throw new LivreInvalideException("Un livre avec cet ISBN existe déjà : " + livre.getIsbn());
        }
        livres.add(livre);
    }

    public void supprimerLivre(String isbn) throws LivreNonTrouveException {
        Livre aSupprimer = trouverParIsbn(isbn);
        livres.remove(aSupprimer);
    }

    public List<Livre> rechercherParTitre(String titre) {
        List<Livre> resultats = new ArrayList<>();
        for (Livre l : livres) {
            if (l.getTitre().toLowerCase().contains(titre.toLowerCase()))
                resultats.add(l);
        }
        return resultats;
    }

    public List<Livre> rechercherParAuteur(String auteur) {
        List<Livre> resultats = new ArrayList<>();
        for (Livre l : livres) {
            if (l.getAuteur().toLowerCase().contains(auteur.toLowerCase()))
                resultats.add(l);
        }
        return resultats;
    }

    public Livre rechercherParIsbn(String isbn) throws LivreNonTrouveException {
        return trouverParIsbn(isbn);
    }

    private Livre trouverParIsbn(String isbn) throws LivreNonTrouveException {
        for (Livre l : livres) {
            if (l.getIsbn().equals(isbn))
                return l;
        }
        throw new LivreNonTrouveException("Livre non trouvé pour l'ISBN : " + isbn);
    }

    public List<Livre> getLivres() { return livres; }
}