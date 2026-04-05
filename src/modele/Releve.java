package modele;

import java.time.LocalDateTime;
import java.util.UUID;

public class Releve {
    private String idReleve;
    private double valeur;
    private LocalDateTime horodatage;

    public Releve(double valeur) {
        this.idReleve = UUID.randomUUID().toString();
        this.valeur = valeur;
        this.horodatage = LocalDateTime.now();
    }

    public Releve(String idReleve, double valeur, LocalDateTime horodatage) {
        this.idReleve = idReleve;
        this.valeur = valeur;
        this.horodatage = horodatage;
    }

    public String getIdReleve() { return idReleve; }
    public double getValeur() { return valeur; }
    public LocalDateTime getHorodatage() { return horodatage; }

    @Override
    public String toString() {
        return horodatage + "|" + valeur;
    }
}
