package repositories;

import domain.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDao {

    private Connection conn;

    public ReizigerDAOPsql(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Reiziger findById(int id) {
        String query = "SELECT * FROM reiziger WHERE reiziger_id = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            if (resultSet.next()){
                int reiziger_id = resultSet.getInt(1);
                String voorletter = resultSet.getString(2);
                String tussenvoegsel = resultSet.getString(3);
                String achternaam = resultSet.getString(4);
                Date geboortedatum = resultSet.getDate(5);
                return new Reiziger(reiziger_id,voorletter, tussenvoegsel, achternaam, geboortedatum);

            }

            preparedStatement.close();
            resultSet.close();
            return null;

        } catch (SQLException e) {
            System.out.println("Well hello?");
//            System.err.println(e);
            return null;
        }
    }

    @Override
    public List<Reiziger> findAll() {
        String query = "SELECT * from reiziger";
        try {

            List<Reiziger> reizigers = new ArrayList<Reiziger>();
            PreparedStatement preparedStatement = conn.prepareStatement(query);

            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            while (resultSet.next()) {
                int reiziger_id = resultSet.getInt(1);
                String voorletter = resultSet.getString(2);
                String tussenvoegsel = resultSet.getString(3);
                String achternaam = resultSet.getString(4);
                Date geboortedatum = resultSet.getDate(5);
//                String voorletter = "a";
//                String tussenvoegsel = "a";
//                String achternaam = "a";
//                Date geboortedatum = Date.valueOf(LocalDate.now());
                Reiziger reiziger = new Reiziger(reiziger_id,voorletter, tussenvoegsel, achternaam, geboortedatum);
                reizigers.add(reiziger);
            }

            resultSet.close();
            preparedStatement.close();
            return reizigers;

        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
    }

    @Override
    public List<Reiziger> findByGbdatum(String datum) {
        String query = "Select * from reiziger where geboortedatum = ?";
        try {
            List<Reiziger> reizigers = new ArrayList<Reiziger>();
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setDate(1, java.sql.Date.valueOf(datum));

            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                int reiziger_id = resultSet.getInt(1);
                String voorletter = resultSet.getString(2);
                String tussenvoegsel = resultSet.getString(3);
                String achternaam = resultSet.getString(4);
                Date geboortedatum = resultSet.getDate(5);
                Reiziger reiziger = new Reiziger(reiziger_id,voorletter, tussenvoegsel, achternaam, geboortedatum);
                reiziger.setReiziger_id(reiziger_id);
                reizigers.add(reiziger);
            }

            resultSet.close();
            preparedStatement.close();
            return reizigers;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
    }

    @Override
    public boolean save(Reiziger reiziger) {
        String query = "INSERT INTO reiziger (reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum) " +
                "VALUES (?, ?, ?, ?, ?);";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);

            preparedStatement.setInt(1, reiziger.getReiziger_id());
            preparedStatement.setString(2, reiziger.getVoorletters());
            preparedStatement.setString(3, reiziger.getTussenvoegsel());
            preparedStatement.setString(4, reiziger.getAchternaam());
            preparedStatement.setDate(5, reiziger.getGeboorteDatum());

            preparedStatement.executeUpdate();
            preparedStatement.close();

            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }

    @Override
    public boolean update(Reiziger reiziger) {
        String query = "UPDATE reiziger SET voorletters = ?, tussenvoegsel = ?, achternaam = ?, geboortedatum = ?" +
                " WHERE reiziger_id = ?;";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);

            preparedStatement.setString(1, reiziger.getVoorletters());
            preparedStatement.setString(2, reiziger.getTussenvoegsel());
            preparedStatement.setString(3, reiziger.getAchternaam());
            preparedStatement.setDate(4, reiziger.getGeboorteDatum());
            preparedStatement.setInt(5, reiziger.getReiziger_id());
            preparedStatement.executeUpdate();
            preparedStatement.close();

            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        String query = "DELETE FROM reiziger where reiziger_id = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, reiziger.getReiziger_id());
            preparedStatement.executeUpdate();
            preparedStatement.close();

            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }
}
