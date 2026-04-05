import java.time.Year;

public class Livre {
    private String isbn;
    private String titre;
    private String auteur;
    private int anneePublication;
    private String genre;

    public Livre(String isbn, String titre, String auteur, int anneePublication, String genre) throws LivreInvalideException {
        setIsbn(isbn);
        setTitre(titre);
        setAuteur(auteur);
        setAnneePublication(anneePublication);
        setGenre(genre);
    }

    public String getIsbn() { return isbn; }
    public String getTitre() { return titre; }
    public String getAuteur() { return auteur; }
    public int getAnneePublication() { return anneePublication; }
    public String getGenre() { return genre; }

    public void setIsbn(String isbn) throws LivreInvalideException {
        if (isbn == null || isbn.isBlank())
            throw new LivreInvalideException("L'ISBN ne peut pas être vide.");
        this.isbn = isbn;
    }

    public void setTitre(String titre) throws LivreInvalideException {
        if (titre == null || titre.isBlank())
            throw new LivreInvalideException("Le titre ne peut pas être vide.");
        this.titre = titre;
    }

    public void setAuteur(String auteur) throws LivreInvalideException {
        if (auteur == null || auteur.isBlank())
            throw new LivreInvalideException("L'auteur ne peut pas être vide.");
        this.auteur = auteur;
    }

    public void setAnneePublication(int annee) throws LivreInvalideException {
        int anneeActuelle = Year.now().getValue();
        if (annee < 1450 || annee > anneeActuelle)
            throw new LivreInvalideException("Année invalide : " + annee);
        this.anneePublication = annee;
    }

    public void setGenre(String genre) throws LivreInvalideException {
        if (genre == null || genre.isBlank())
            throw new LivreInvalideException("Le genre ne peut pas être vide.");
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "[" + isbn + "] " + titre + " - " + auteur + " (" + anneePublication + ") - " + genre;
    }
}