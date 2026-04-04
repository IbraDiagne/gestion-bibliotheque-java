package gestion;
import modele.Amende;
import modele.Caisse;
import modele.Emprunt;
import exception.PaiementInvalideException;
public class GestionAmendes {
    //constante du tarif
    public static final double tarif_par_jour = 0.5;
    private Caisse caisse;

    public GestionAmendes(Caisse caisse)
    {
        if(caisse == null)
            throw new PaiementInvalideException("La caisse ne peut pas être null . ");
        this.caisse = caisse;
    }

    public double calculerMontant(Emprunt emprunt)
    {
        if(emprunt == null)
            throw new PaiementInvalideException("L'emprunt ne peut pas être null");
        return emprunt.calculerJoursRetard() * tarif_par_jour;

    }

    //creer et enregistrer une amende
    public Amende creerAmende(Emprunt emprunt)
    {
        if(emprunt == null)
            throw new PaiementInvalideException("L'emprunt ne peut pas être null");
        if(!emprunt.estEnRetard())
            throw new PaiementInvalideException("Impossible de créer une amende ; l'emprunt n'est pas en retard! ");

        return caisse.enregistrerAmende(emprunt);
    }

    //payer une amende
    public void payer(Amende amende,double montant)
    {
        caisse.payerAmende(amende, montant);
    }

    //getter
    public Caisse getCaisse() {return caisse ; }
}
