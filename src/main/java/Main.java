import domain.Adres;
import domain.Reiziger;
import repositories.AdresDAOPsql;
import repositories.AdresDao;
import repositories.ReizigerDAOPsql;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;

public class Main {
    private static Connection conn;

    public static void main(String[] args) {
        try {
            getConnection();
            ReizigerDAOPsql rdao = new ReizigerDAOPsql(conn);
            AdresDao adao = new AdresDAOPsql(conn);

            testReizigerDAO(rdao, adao);

            closeConnection();
        } catch (SQLException e) {
//            System.err.println(e);
        }


    }

    private static void getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost/ovchip";
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "23051999");
//        props.setProperty("ssl", "true");
        conn = DriverManager.getConnection(url, props);

    }

    public static void closeConnection() throws SQLException {
        conn.close();
    }

    /**
     * P2. Reiziger DAO: persistentie van een klasse
     * <p>
     * Deze methode test de CRUD-functionaliteit van de Reiziger DAO
     *
     * @throws SQLException
     */
    private static void testReizigerDAO(ReizigerDAOPsql rdao, AdresDao adao) throws SQLException {
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_BLUE = "\u001B[34m";
        final String ANSI_PURPLE = "\u001B[35m";

        System.out.println(ANSI_PURPLE + "\n---------- Test ReizigerDAO -------------" + ANSI_PURPLE);
        System.out.println(ANSI_BLUE + "Preparing db...." + ANSI_RESET);
        //Clean db for next test.
        Reiziger r77 = rdao.findById(77);
        if (r77 != null){ // see if reiziger exists
            System.out.print("Delete 77 : ");
             // delete if so
            System.out.println(rdao.delete(r77) ? "success" : "error");
        }
        Reiziger r20 = rdao.findById(20);
        if (r20 != null) { // see if reiziger exists
            Adres adres = adao.findByReiziger(r20);
            rdao.delete(r20); // delete reiziger
            if (adres != null){ // see if adres linked to user exists
                System.out.println("delete 20");
                adao.delete(adres); // delete adres
            }
        }
        Reiziger r25 = rdao.findById(25);
        Adres a6 = adao.findById(6);
        if (a6 != null){
            adao.delete(a6);
        }
        if (r25 != null){
                rdao.delete(r25);
        }



        Reiziger r8 = rdao.findById(8);
        if (r8 != null){ // see if reiziger exists
            System.out.println("Delete 8");
            rdao.delete(r8); // delete if so
        }
        System.out.println(ANSI_BLUE + "Done!" + ANSI_RESET);

        System.out.println(ANSI_GREEN + "\n[Test] ReizigerDAO.findAll() geeft de volgende reizigers:"+ANSI_RESET);
        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print(ANSI_GREEN + "\n[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() " + ANSI_RESET);
        rdao.save(sietske);

        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + ANSI_GREEN +" reizigers" + ANSI_RESET);
//
        System.out.println(ANSI_GREEN + "\n[Test] reizigerDAO.findByID(2) geeft de volgende reiziger:" + ANSI_RESET);
        Reiziger reiziger1 = rdao.findById(2);
        System.out.println("Reiziger met id 2 = " + reiziger1);
//
//
        System.out.println(ANSI_GREEN + "\n[Test] Edit reiziger: " + ANSI_RESET);
        Reiziger reiziger2 = new Reiziger(20, "J", "", "Roodenburg", Date.valueOf("1999-05-23"));
        rdao.save(reiziger2);
        System.out.println("Reiziger met id 20 = " + rdao.findById(20));
        System.out.println(ANSI_BLUE + "Editing reiziger!" + ANSI_RESET);
        reiziger2.setTussenvoegsel("nvt");
        rdao.update(reiziger2);
        System.out.println("Reiziger met id 20 = " + rdao.findById(20));
//

        System.out.println(ANSI_GREEN + "\n[Test] Delete reiziger" + ANSI_RESET);
        System.out.println(rdao.findAll());
        System.out.println(ANSI_BLUE + "Verwijderen van reiziger met id 20" + ANSI_RESET);
        rdao.delete(rdao.findById(20));
        System.out.println(rdao.findAll());

        System.out.println(ANSI_PURPLE + "\n---------- Test adresDAO -------------" + ANSI_RESET);
//
        System.out.println(ANSI_GREEN + "[Test] adresDAO.findall() geeft de volgende adressen: " + ANSI_RESET);
        System.out.println(adao.findAll().toString());

        Reiziger reiziger3 = new Reiziger(25, "J", "van","doorn",Date.valueOf(LocalDate.now()));
        rdao.save(reiziger3);
        Adres adres = new Adres(6,"1234AB","15", "Heidelberglaan","Utrecht", reiziger3.getReiziger_id());
        adao.save(adres);


        System.out.println(ANSI_BLUE + "Adres aan het toevoegen..." + ANSI_RESET);

        System.out.println(ANSI_GREEN + "de nieuwe lijst met adressen: " + ANSI_RESET);
        System.out.println(adao.findAll().toString());

        System.out.println(ANSI_GREEN + "\n[Test] vind adres op reiziger ("+reiziger3.getReiziger_id()+"): " + ANSI_RESET);
        System.out.println(adao.findByReiziger(reiziger3));

        System.out.println(ANSI_GREEN + "\n[Test] update adres (#6)" + ANSI_RESET);
        System.out.println(adao.findByReiziger(reiziger3));
        System.out.println(ANSI_BLUE + "Updating adres..." + ANSI_RESET);
        adres.setHuisnummer("12h");
        adres.setStraat("Stationsplein");
        adao.update(adres);
        System.out.println(adao.findByReiziger(reiziger3));

        System.out.println(ANSI_GREEN + "\n[Test] verwijder adres (#6)" + ANSI_RESET);
        System.out.println(adao.findAll().toString());
        adao.delete(adres);
        System.out.println(ANSI_BLUE + "Verwijderen van adress..." + ANSI_RESET);
        System.out.println(adao.findAll());
        System.out.println(ANSI_PURPLE + "TESTS ARE DONE!" + ANSI_RESET);


    }


}
