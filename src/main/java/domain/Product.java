package domain;

import java.util.ArrayList;
import java.util.Objects;

public class Product {

    private int productNummer;
    private String naam;
    private String beschrijving;
    private double prijs;

    private final ArrayList<OVChipkaart> chipkaarten = new ArrayList<>();

    public Product(int productNummer, String naam, String beschrijving, double prijs) {
        this.productNummer = productNummer;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
    }

    public boolean selfUpdate() {
        for (OVChipkaart ovChipkaart : this.chipkaarten) {
            return ovChipkaart.updateProduct(this);
        }
        return false;
    }

    public boolean addOVChipkaart(OVChipkaart ovChipkaart) {
//        if (this.chipkaarten.contains(ovChipkaart)) {
//            return false;
//        } else {
//            for (OVChipkaart ovc: this.chipkaarten){
//                if (ovc.getKaartnummer() == ovChipkaart.getKaartnummer()){
////                    ovChipkaart.addProduct(this);
//                    updateOVChipkaart(ovChipkaart);
//                    return false;
//                }
//            }
//            this.chipkaarten.add(ovChipkaart);
//        }
//        return true;
        if (this.chipkaarten.contains(ovChipkaart)) {
            ovChipkaart.p_add_ov(this); // make sure relationship is accurate (will not do anything if product is already in ov)
            return false; // already exists
        } else {
            this.chipkaarten.add(ovChipkaart);
            ovChipkaart.p_add_ov(this);
            return true;
        }
    }

    public boolean removeOVChipkaart(OVChipkaart ovChipkaart) {
        if (this.chipkaarten.contains(ovChipkaart)) {
            try {
                ovChipkaart.p_rem_ov(this); // remove ov from product
                this.chipkaarten.remove(ovChipkaart); // remove ov from self
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            for (OVChipkaart ovc : this.chipkaarten) {
                if (ovc.getKaartnummer() == ovChipkaart.getKaartnummer()) {
                    ovc.p_rem_ov(this);
                    this.chipkaarten.remove(ovChipkaart);
                    return true;
                }
            }
            return false; // ovchipkaart not linked.
        }
    }

    public boolean updateOVChipkaart(OVChipkaart ovChipkaart) {
        try {
            int index = this.chipkaarten.indexOf(getByKaartNummer(ovChipkaart.getKaartnummer()));
            if (this.chipkaarten.contains(ovChipkaart)) {
                this.chipkaarten.set(index, ovChipkaart);
                ovChipkaart.updateProduct(this);
                return true;
            } else {
                this.chipkaarten.add(ovChipkaart);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean ov_rem_p(OVChipkaart ovChipkaart) {
        if (this.chipkaarten.contains(ovChipkaart)) {
            return this.chipkaarten.remove(ovChipkaart); // remove ov. (p is already removed from ov when this is run.)
        } else {
            return true; // doesn't exist.
        }
    }

    public boolean ov_add_p(OVChipkaart ovChipkaart) {
        if (this.chipkaarten.contains(ovChipkaart)) {

            return true; // already existing
        } else {
            return this.chipkaarten.add(ovChipkaart); // add ov. (p is already added to ov when this is run.)
        }
    }


    OVChipkaart getByKaartNummer(int kn) {
        for (OVChipkaart ov : this.chipkaarten) {
            if (ov.getKaartnummer() == kn) {
                return ov;
            }
        }
        return null;
    }

    public int getProductNummer() {
        return productNummer;
    }

    public void setProductNummer(int productNummer) {
        this.productNummer = productNummer;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public double getPrijs() {
        return prijs;
    }

    public void setPrijs(double prijs) {
        this.prijs = prijs;
    }

    public ArrayList<OVChipkaart> getChipkaarten() {
        return chipkaarten;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productNummer=" + productNummer +
                ", naam='" + naam + '\'' +
                ", beschrijving='" + beschrijving + '\'' +
                ", prijs=" + prijs +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return productNummer == product.productNummer && Double.compare(product.prijs, prijs) == 0 && naam.equals(product.naam) && beschrijving.equals(product.beschrijving) && Objects.equals(chipkaarten, product.chipkaarten);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productNummer, naam, beschrijving, prijs, chipkaarten);
    }
}
