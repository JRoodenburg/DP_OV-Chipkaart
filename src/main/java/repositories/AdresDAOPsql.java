package repositories;

import domain.Adres;
import domain.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql implements AdresDao {
    private Connection conn;
    public AdresDAOPsql(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Adres> findAll() {
        String query = "Select * from adres";
        List<Adres> adresList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                int adres_id = resultSet.getInt(1);
                String postcode = resultSet.getString(2);
                String huisnummer = resultSet.getString(3);
                String straat = resultSet.getString(4);
                String woonplaats = resultSet.getString(5);
                int reiziger_id = resultSet.getInt(6);

               Adres adres = new Adres(adres_id,postcode,huisnummer,straat,woonplaats,reiziger_id);
                adresList.add(adres);
            }
            resultSet.close();
            preparedStatement.close();
            return adresList;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
    }

    @Override
    public boolean save(Adres a) {
        String query = "INSERT INTO adres values (?,?,?,?,?,?);";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);

            preparedStatement.setInt(1,a.getAdres_id());
            preparedStatement.setString(2,a.getPostcode());
            preparedStatement.setString(3,a.getHuisnummer());
            preparedStatement.setString(4,a.getStraat());
            preparedStatement.setString(5,a.getWoonplaats());
            preparedStatement.setInt(6,a.getReiziger_id());

            preparedStatement.execute();
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }

    @Override
    public boolean update(Adres a) {
        String query = "UPDATE adres SET postcode = ?, huisnummer = ?, straat = ?, woonplaats = ?, reiziger_id = ? WHERE adres_id = ?;";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);

            preparedStatement.setString(1,a.getPostcode());
            preparedStatement.setString(2,a.getHuisnummer());
            preparedStatement.setString(3,a.getStraat());
            preparedStatement.setString(4,a.getWoonplaats());
            preparedStatement.setInt(5,a.getReiziger_id());
            preparedStatement.setInt(6,a.getAdres_id());

            preparedStatement.execute();
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }

    @Override
    public boolean delete(Adres a) {
        try {
            String query = "DELETE FROM adres WHERE adres_id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1,a.getAdres_id());
            preparedStatement.execute();
            preparedStatement.close();
            return true;
        } catch (SQLException e){
            System.err.println(e);
            return false;
        }

    }

    public Adres findById(int id){
       List<Adres> all = new ArrayList<>();
       all = findAll();
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getAdres_id() == id){
                return all.get(i);
            }
        }
        return null;
    }
    @Override
    public Adres findByReiziger(Reiziger r) {
       try {
           if (r != null){
               String query = "SELECT * FROM adres WHERE reiziger_id = ?";
               PreparedStatement preparedStatement = conn.prepareStatement(query);
               preparedStatement.setInt(1, r.getReiziger_id());
               preparedStatement.execute();

               ResultSet resultSet = preparedStatement.getResultSet();
               if (resultSet.next()){
                   int adres_id = resultSet.getInt(1);
                   String postcode = resultSet.getString(2);
                   String huisnummer = resultSet.getString(3);
                   String straat = resultSet.getString(4);
                   String woonplaats = resultSet.getString(5);
                   int reiziger_id = resultSet.getInt(6);

                   Adres adres = new Adres(adres_id,postcode,huisnummer,straat,woonplaats,reiziger_id);
                   resultSet.close();
                   preparedStatement.close();
                   return adres;
               } else {
                   System.out.println("no result, adres does not exist?");
                   resultSet.close();
                   preparedStatement.close();
                   return null;
               }
           } else {
               System.out.println("r=null");
           }

       } catch (SQLException e) {
           System.err.println(e);
           return null;
       }

    return null;
    }

}
