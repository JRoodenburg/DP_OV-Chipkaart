package domain;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Objects;

public class OVChipkaart {
    private final int kaart_nummer;
    private final Date geldig_tot;
    private int reiziger_id;
    private int klasse;
    private double saldo;
    private ArrayList<Product> producten = new ArrayList<>();

    public OVChipkaart(int kaart_nummer, Date geldig_tot, int klasse) {
        this.kaart_nummer = kaart_nummer;
        this.geldig_tot = geldig_tot;
        this.klasse = klasse;
        this.saldo = 0;
    }

    public boolean addProduct(Product product) {
        if (product == null || this.producten.contains(product)) {
            return false; // product already exists
        } else {
            try {
                this.producten.add(product); // add to list
                product.ov_add_p(this);  // creeer link tussen product en ovchipkaart wanneer je een (nieuw) product toevoegt
                return true;// successfully added product
//                selfUpdate();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public boolean p_add_ov(Product p) { // (add) ov to product (comming from product)
        if (p == null || this.producten.contains(p)) {
            return false; // already exists
        } else {
            return this.producten.add(p); // add product. (ov is already added to product when this is run.)
        }
    }

    public boolean p_rem_ov(Product p) {

        if (p == null || !this.producten.contains(p)) {
            return false; // doesn't exist
        } else {
            return this.producten.remove(p); // remove product. (ov is already removed from product when this is run.)
        }
    }

    //ov_rem_p;
    public boolean removeProduct(Product product) {
        if (this.producten.contains(product)) {
            try {
                this.producten.remove(product);
                product.ov_rem_p(this);
//                selfUpdate();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } else {
            for (Product p : producten) {
                if (p.getProductNummer() == product.getProductNummer()) {
                    this.producten.remove(product);
                    product.removeOVChipkaart(this);
//                    selfUpdate();
                    return true;
                }
            }
            return false; // product not linked.
        }
    }

    public boolean updateProduct(Product product) {
        try {
            int index = this.producten.indexOf(this.getByProductNummer(product.getProductNummer()));
            if (index == -1) {
                this.producten.add(product);
                return true;
            } else {
                for (Product p : this.producten) {
                    if (p.getProductNummer() == product.getProductNummer()) {
                        this.producten.set(this.producten.indexOf(p), product);
                    }
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean addSaldo(double saldo) {
        try {
            this.saldo = this.saldo + saldo;
            return true;
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }

    public boolean removeSaldo(double saldo) {
        try {
            this.saldo = this.saldo - saldo;
            return true;
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }

    public Product getByProductNummer(int pn) {
        for (Product product : producten) {
            if (product.getProductNummer() == pn) {
                return product;
            }
        }
        return null;
    }

    public int getKaartnummer() {
        return kaart_nummer;
    }

    public java.sql.Date getGeldig_tot() {
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

    public void setKlasse(int klasse) {
        this.klasse = klasse;
    }

    public void setProducten(ArrayList<Product> producten) {
        this.producten = producten;
    }

    public void setReiziger_id(int reiziger_id) {
        this.reiziger_id = reiziger_id;
    }

    public ArrayList<Product> getProducten() {
        return producten;
    }

    @Override
    public String toString() {
        return "kaartnummer: " + kaart_nummer +
                ", geldig_tot:" + geldig_tot +
                ", klasse:" + klasse +
                ", saldo:" + saldo +
                ", reiziger_id:" + reiziger_id +
                ", producten: " + producten;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OVChipkaart that = (OVChipkaart) o;
        return kaart_nummer == that.kaart_nummer && reiziger_id == that.reiziger_id && klasse == that.klasse && Double.compare(that.saldo, saldo) == 0 && geldig_tot.equals(that.geldig_tot) && Objects.equals(producten, that.producten);
    }

    @Override
    public int hashCode() {
        return Objects.hash(kaart_nummer, geldig_tot, reiziger_id, klasse, saldo, producten);
    }
}
