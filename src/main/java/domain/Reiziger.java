package domain;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Reiziger {
    private int reiziger_id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboorteDatum;

    private ArrayList<OVChipkaart> chipkaarten = new ArrayList<>();

    public Reiziger(int reiziger_id, String voorletters, String tussenvoegsel, String achternaam, Date geboorteDatum) {
        this.reiziger_id = reiziger_id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboorteDatum = geboorteDatum;
    }

    public boolean AddOvchipkaart(OVChipkaart ovChipkaart){
        try {
            this.chipkaarten.add(ovChipkaart);
            return true;
        } catch (Exception e) {
            System.err.println(e);
           return false;
        }
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
        return String.format("(%d) %s %s", reiziger_id, getNaam(), geboorteDatum.toString());
    }

    public List<OVChipkaart> getOVChipkaarten() {
        return chipkaarten;
    }
}
