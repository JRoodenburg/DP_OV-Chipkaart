package repositories;

import domain.OVChipkaart;
import domain.Product;
import domain.Reiziger;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class OVChipkaartDaoPsql implements OVChipkaartDao {
    private final Connection conn;
    private final ProductDaoPsql pdao; // product dao

    public OVChipkaartDaoPsql(Connection conn) {
        this.conn = conn;
        pdao = new ProductDaoPsql(conn);
    }

    public Boolean addProduct(Product p, OVChipkaart o) throws SQLException {
        String checkquery = "Select * FROM ov_chipkaart_product where kaart_nummer = ? AND product_nummer = ?;"; //find relation
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(checkquery);
            preparedStatement.setInt(1, o.getKaartnummer());
            preparedStatement.setInt(2, p.getProductNummer());
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            if (!resultSet.next()) { // if relation doesn't exist; (returns null)
                String query = "insert into ov_chipkaart_product values(?,?,?,?);";
                try {
                    preparedStatement = conn.prepareStatement(query);
                    preparedStatement.setInt(1, o.getKaartnummer());
                    preparedStatement.setInt(2, p.getProductNummer());
                    preparedStatement.setString(3, "ACTIVE");
                    preparedStatement.setDate(4, java.sql.Date.valueOf(LocalDate.now()));
                    return preparedStatement.execute();
                } catch (SQLException e) {
                    System.err.println(e);
                    return false;
                }
            } else {
                // relationship exists, do nothing
            }
            conn.close();
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Boolean removeProduct(Product p, OVChipkaart o) throws SQLException {
        try {
            return setProductStatus("INACTIVE", o, p);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean setProductStatus(String status, OVChipkaart ov, Product p) {
        String updateQuery = "UPDATE ov_chipkaart_product SET status = ?, last_update = ? where kaart_nummer = ? and product_nummer = ?;";
        PreparedStatement preparedStatement;

        try {
            preparedStatement = conn.prepareStatement(updateQuery);
            preparedStatement.setString(1, status);
            preparedStatement.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
            preparedStatement.setInt(3, ov.getKaartnummer());
            preparedStatement.setInt(4, p.getProductNummer());
            return preparedStatement.execute();
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }

    public ArrayList<Product> getProducts(OVChipkaart o) {
        if (o == null) {
            return null;
        }
        String query = "select * from ov_chipkaart_product where kaart_nummer = ?;";
        ArrayList<Product> foundProducts = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);

            preparedStatement.setInt(1, o.getKaartnummer());
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                foundProducts.add(pdao.findByProductNumber(resultSet.getInt(1)));
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return foundProducts;
    }

    @Override
    public OVChipkaart findByKaartnummer(int kn) {
        String query = "Select * from ov_chipkaart WHERE kaart_nummer = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);

            preparedStatement.setInt(1, kn);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            OVChipkaart ovChipkaart = null;

            while (resultSet.next()) {
                int kaart_nummer = resultSet.getInt(1);
                Date geldig_tot = resultSet.getDate(2);
                int klasse = resultSet.getInt(3);
                double saldo = resultSet.getDouble(4);
                int reiziger_id = resultSet.getInt(5);

                ovChipkaart = new OVChipkaart(kaart_nummer, geldig_tot, klasse);
                ovChipkaart.setReiziger_id(reiziger_id);
                ArrayList<Product> producten = getProducts(ovChipkaart);
                for (Product product : producten) {
                    ovChipkaart.addProduct(product);
                }

                ovChipkaart.addSaldo(saldo);
            }
            resultSet.close();
            preparedStatement.close();
            return ovChipkaart;

        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger r) {
        String query = "Select * from ov_chipkaart WHERE reiziger_id = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);

            preparedStatement.setInt(1, r.getReiziger_id());
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            ArrayList<OVChipkaart> chipkaart_lijst = new ArrayList<>();

            while (resultSet.next()) {
                int kaart_nummer = resultSet.getInt(1);
                Date geldig_tot = resultSet.getDate(2);
                int klasse = resultSet.getInt(3);
                double saldo = resultSet.getDouble(4);
                int reiziger_id = resultSet.getInt(5);

                OVChipkaart ovChipkaart = new OVChipkaart(kaart_nummer, geldig_tot, klasse);
                ovChipkaart.setReiziger_id(reiziger_id);
                ArrayList<Product> producten = new ArrayList<>(getProducts(ovChipkaart));
                for (Product product : producten) {
                    ovChipkaart.addProduct(product);
                }

                ovChipkaart.addSaldo(saldo);

                chipkaart_lijst.add(ovChipkaart);
            }
            resultSet.close();
            preparedStatement.close();
            return chipkaart_lijst;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean save(OVChipkaart o) {
        String query = "INSERT INTO ov_chipkaart values (?,?,?,?,?);";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);

            preparedStatement.setInt(1, o.getKaartnummer());
            preparedStatement.setDate(2, (java.sql.Date) o.getGeldig_tot());
            preparedStatement.setInt(3, o.getKlasse());
            preparedStatement.setDouble(4, o.getSaldo());
            preparedStatement.setInt(5, o.getReiziger_id());
            if (!o.getProducten().isEmpty()) {
                for (Product p : o.getProducten()) {
                    addProduct(p, o);
                }
            }
            preparedStatement.execute();
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }

    }


    @Override
    public boolean update(OVChipkaart o) {
        String query = "UPDATE ov_chipkaart SET kaart_nummer = ?, geldig_tot = ?, klasse = ?, saldo = ?, reiziger_id = ? WHERE kaart_nummer = ?;";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);

            preparedStatement.setInt(1, o.getKaartnummer());
            preparedStatement.setDate(2, o.getGeldig_tot());
            preparedStatement.setInt(3, o.getKlasse());
            preparedStatement.setDouble(4, o.getSaldo());
            preparedStatement.setInt(5, o.getReiziger_id());
            preparedStatement.setInt(6, o.getKaartnummer());

            preparedStatement.execute();

            for (Product p : o.getProducten()) {
                String updateQuery = "INSERT INTO ov_chipkaart_product (kaart_nummer, product_nummer, status, last_update) VALUES(?, ?, ?, ?) ON CONFLICT (kaart_nummer, product_nummer) DO UPDATE SET status = ?, last_update = ?";
                preparedStatement = conn.prepareStatement(updateQuery);
                preparedStatement.setInt(1, o.getKaartnummer());
                preparedStatement.setInt(2, p.getProductNummer());
                preparedStatement.setString(3, "actief");
                preparedStatement.setDate(4, java.sql.Date.valueOf(LocalDate.now()));
                preparedStatement.setString(5, "actief");
                preparedStatement.setDate(6, java.sql.Date.valueOf(LocalDate.now()));
                preparedStatement.execute();
            }
            preparedStatement.close();

            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }

    @Override
    public boolean delete(OVChipkaart o) {
        String deleteChipkaart = "DELETE FROM ov_chipkaart WHERE kaart_nummer = ?;";
        String deleteRelation = "DELETE FROM ov_chipkaart_product WHERE kaart_nummer = ?;";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(deleteRelation);
            preparedStatement.setInt(1, o.getKaartnummer());
            preparedStatement.execute();
            preparedStatement = conn.prepareStatement(deleteChipkaart);
            preparedStatement.setInt(1, o.getKaartnummer());
            preparedStatement.execute();
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }
}
