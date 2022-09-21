package domain;

import java.util.Date;

public class OVChipkaart {

    private final int kaart_nummer;
    private final Date geldig_tot;
    private final int klasse;
    private double saldo;
    private final int reiziger_id;

    public OVChipkaart(int kaart_nummer, Date geldig_tot, int klasse, int reiziger_id) {
        this.kaart_nummer = kaart_nummer;
        this.geldig_tot = geldig_tot;
        this.klasse = klasse;
        this.saldo = 0;
        this.reiziger_id = reiziger_id;
    }

    public boolean addSaldo(double saldo){
        try {
            this.saldo = this.saldo + saldo;
            return true;
        } catch (Exception e){
            System.err.println(e);
            return false;
        }
    }


    public int getKaartnummer() {
        return kaart_nummer;
    }

    public Date getGeldig_tot() {
        return geldig_tot;
    }

    public int getKlasse() {
        return klasse;
    }

    public double getSaldo() {
        return saldo;
    }

    public int getReiziger_id() {
        return reiziger_id;
    }

    @Override
    public String toString() {
        return "kaartnummer: " + kaart_nummer +
                ", geldig_tot:" + geldig_tot +
                ", klasse:" + klasse +
                ", saldo:" + saldo +
                ", reiziger_id:" + reiziger_id;
    }
}
