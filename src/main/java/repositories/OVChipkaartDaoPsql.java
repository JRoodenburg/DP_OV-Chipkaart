package repositories;

import domain.Adres;
import domain.OVChipkaart;
import domain.Reiziger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OVChipkaartDaoPsql implements OVChipkaartDao {
    private final Connection conn;

    public OVChipkaartDaoPsql(Connection conn) {
        this.conn = conn;
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

                 ovChipkaart = new OVChipkaart(kaart_nummer, geldig_tot, klasse, reiziger_id);
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

                OVChipkaart ovChipkaart = new OVChipkaart(kaart_nummer, geldig_tot, klasse, reiziger_id);
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
            preparedStatement.setDate(2, (java.sql.Date) o.getGeldig_tot());
            preparedStatement.setInt(3, o.getKlasse());
            preparedStatement.setDouble(4, o.getSaldo());
            preparedStatement.setInt(5, o.getReiziger_id());
            preparedStatement.setInt(6, o.getKaartnummer());

            preparedStatement.execute();
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }

    @Override
    public boolean delete(OVChipkaart o) {
        String query = "DELETE FROM ov_chipkaart WHERE kaart_nummer = ?;";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
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
