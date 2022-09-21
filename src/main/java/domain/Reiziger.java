package domain;

import java.sql.Date;

public class Reiziger {
    private int reiziger_id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboorteDatum;

    public Reiziger(int reiziger_id, String voorletters, String tussenvoegsel, String achternaam, Date geboorteDatum) {
        //GEEN ID BIJ REIZIGER CONSTRUCTOR. deze word auto increment vanuit db.
        this.reiziger_id = reiziger_id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboorteDatum = geboorteDatum;
    }

    public String getNaam(){
        String naam;
        if (tussenvoegsel == null || tussenvoegsel.isEmpty() ){
            naam = String.format("%s. %s", voorletters, achternaam);
        } else {
            naam = String.format("%s. %s %s", voorletters, tussenvoegsel,achternaam);
        }
        return naam;
    }

    public int getReiziger_id() {
        return reiziger_id;
    }

    public void setReiziger_id(int reiziger_id) {
        this.reiziger_id = reiziger_id;
    }

    public String getVoorletters() {
        return voorletters;
    }

    public void setVoorletters(String voorletters) {
        this.voorletters = voorletters;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public Date getGeboorteDatum() {
        return geboorteDatum;
    }

    public void setGeboorteDatum(Date geboorteDatum) {
        this.geboorteDatum = geboorteDatum;
    }

    @Override
    public String toString() {
        return String.format("(%d) %s %s", reiziger_id, getNaam(), getGeboorteDatum().toString());
    }
}
