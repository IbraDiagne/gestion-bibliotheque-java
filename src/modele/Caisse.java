package modele;

import exception.PaiementInvalideException;
import gestion.GestionAmendes;

import java.util.ArrayList;
import java.util.List;

public class Caisse {
    private double solde;
    private List amendes;

 //constructeur sans paramètre
public Caisse()
{
    this.solde = 0.0;
    this.amendes = new ArrayList<>();
}

public Amende enregistrerAmende(Emprunt emprunt)
{
    if(emprunt == null)
        throw new exception.PaiementInvalideException("L'emprunt doit exister");


    //générer un ID unique pour l'amende
    String idAmende = "AME" + String.format("%03d",amendes.size() + 1);

    Amende amende = new Amende(idAmende,emprunt,GestionAmendes.tarif_par_jour);
    amendes.add(amende);

    System.out.println("[Caisse] Amende créée : " + amende);
    retun amende;
}

    //payer une amende
    public void payerAmende(Amende amende , double montant)
    {
        if(amende == null)
            throw new PaiementInvalideException("L'amende doit exister ");
    
    amende.payer(montant); 
    calculerSolde(); //recalculer le solde après paiement
    System.out.println("[Caisse] Paiement enregistré. Solde caisse : " + solde + "FCFA ");

}


//recalcul du solde
private void calculerSolde()
{
    double total = 0.0;
    for(Amende a : amendes)
    {
        total += a.getMontantPaye();

    }
    this.solde = total;
}
 //pour les amendes non soldees
 public List getAmendesImpayees()
 {
    List impayees = new ArrayList<>();
    for(Amende a : amendes)
    {
        if(!a.getEstSoldee())
        {
            impayees.add(a);
        }
    }
    return impayees;
 }
//verifier si un membre a une amende non soldée
public boolean aAmendeDue(String idMembre)
{
    for(Amende a : amendes)
    {
        if(a.getEmprunt().getMembre().getIdMembre().equals(idMembre) && !a.getEstSoldee())
        {
            return true;
        }
    }
    return false;
}

//getteurs
public double getSolde() { return solde ; }
public List getAmendes() { return new ArrayList<>(amendes) ; }

@Override
public String toString()
{
    return "Caisse(solde= "+ solde + "FCFA , amendes= "+ amendes.sizes() + ", impayées= "+ getAmendesImpayees().size() + ") ";
}

}
