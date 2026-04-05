package modele;

import exception.PaiementInvalideException;

public class Amende {
    private String idAmende;
    private Emprunt emprunt;
    private double montantTotal;
    private double montantPaye;
    private boolean estSoldee;
    public Amende(){ }
    public Amende(String idAmende, Emprunt emprunt,double montantTotal,double montantPaye,boolean estSoldee)
    {
        if(idAmende == null || idAmende.isBlank())
        {
            throw new PaiementInvalideException("L'ID de l'amende ne peut pas être vide");
        }
        if(emprunt == null)
        {
            throw new PaiementInvalideException("L'emprunt associé ne peut pas être nulle");
        }

        this.idAmende = idAmende;
        this.emprunt = emprunt;
        this.montantPaye = 0.0;
        this.estSoldee = false;

        this.montantTotal = emprunt.calculerJoursRetard() * tarifParJour;

    }

    public double getMontantrestant()
    {
        return montantTotal - montantPaye;
    }

    public void payer(double montant)
    {
        if(montant <=0 )
            throw new PaiementInvalideException("Le montant à payer doit être supérieur à 0. ");
        if(montant > getMontantrestant())
            throw new PaiementInvalideException("Le montant " + montant + " dépasse le restant à payer qui est de "+ getMontantrestant() + "FCFA");
    

    this.montantPaye += montant;
//si tout est payer on solde l'amende
    if(this.montantPaye >= montantTotal)
        this.estSoldee = true;

}
    //Getters
    public String getIdAmende(){return this.idAmende;}
    public Emprunt getEmprunt(){ return this.emprunt;}
    public double getMontantTotal(){ return this.montantTotal;}
    public double getMontantPaye(){ return this.montantPaye;}
    public boolean getEstSoldee(){ return this.estSoldee;}


   @Override
   public String toString()
   {
    return "Détail de l'amende " + 
    "id :"+ idAmende +
    ", emprunt : " +emprunt.getIdEmprunt() + 
    ", Total : " + montantTotal + 
    ", Payé  : " + montantPaye + 
    " , Statut :" + (estSoldee ? "Soldé" : "En Attente "); //Opérateur ternaire

   }




}
    
