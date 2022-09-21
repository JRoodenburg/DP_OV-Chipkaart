import domain.Adres;
import domain.OVChipkaart;
import domain.Reiziger;
import repositories.*;

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
            OVChipkaartDao odao = new OVChipkaartDaoPsql(conn);
            testReizigerDAO(rdao, adao, odao);

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
    private static void testReizigerDAO(ReizigerDAOPsql rdao, AdresDao adao, OVChipkaartDao odao) throws SQLException {
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_BLUE = "\u001B[34m";
        final String ANSI_PURPLE = "\u001B[35m";

        System.out.println(ANSI_PURPLE + "\n---------- Test ReizigerDAO -------------" + ANSI_PURPLE);
        System.out.println(ANSI_BLUE + "Preparing db...." + ANSI_RESET);
        //Clean db for next test.
        Reiziger r77d = rdao.findById(77);
        if (r77d != null){ // see if reiziger exists
            System.out.print("Delete r77 : ");
             // delete if so
            System.out.println(rdao.delete(r77d) ? "success" : "error");
        }
        Reiziger r20d = rdao.findById(20);
        if (r20d != null) { // see if reiziger exists
            Adres a20d = adao.findByReiziger(r20d);
            if (a20d != null){ // see if adres linked to user exists
                System.out.print("delete a20 : ");
                System.out.println(adao.delete(a20d) ? "success" : "error");
            }
            System.out.print("Delete r20 : ");
            System.out.println(rdao.delete(r20d) ? "success" : "error"); // delete reiziger
        }
        Reiziger r25d = rdao.findById(25);
        Adres a6d = adao.findById(6);
        if (a6d != null){
            System.out.print("Delete a6 : ");
            System.out.println(adao.delete(a6d) ? "success" : "error"); // delete reiziger
        }
        if (r25d != null){
            System.out.print("Delete r25 : ");
            System.out.println( rdao.delete(r25d) ? "success" : "error");
        }
        Reiziger r8d = rdao.findById(8);
        if (r8d != null){ // see if reiziger exists
            System.out.print("Delete r8 : ");
            System.out.println( rdao.delete(r8d) ? "success" : "error");
        }
        Reiziger r78d = rdao.findById(78);
        if (r78d != null){ // see if reiziger exists
            if (r78d.getOVChipkaarten() != null){
                for (OVChipkaart ovChipkaart: r78d.getOVChipkaarten()) {
                    odao.delete(ovChipkaart);
                }
            }
            System.out.print("Delete r78 : ");
            System.out.println(rdao.delete(r78d) ? "success" : "error");
        }
        Reiziger r79d = rdao.findById(79);
        if (r79d != null){ // see if reiziger exists
            if (r79d.getOVChipkaarten() != null){
                for (OVChipkaart ovChipkaart: r79d.getOVChipkaarten()) {
                    odao.delete(ovChipkaart);
                }
            }
            System.out.print("Delete r79 : ");
            System.out.println(rdao.delete(r79d) ? "success" : "error");
        }
        OVChipkaart ov1d = odao.findByKaartnummer(1);
        if (ov1d != null){ // see if reiziger exists
            System.out.print("Delete ov1 : ");
            System.out.println( odao.delete(ov1d) ? "success" : "error");
        }
        OVChipkaart ov2d = odao.findByKaartnummer(2);
        if (ov2d != null){ // see if reiziger exists
            System.out.print("Delete ov2 : ");
            System.out.println( odao.delete(ov2d) ? "success" : "error");
        }
        OVChipkaart ov3d = odao.findByKaartnummer(3);
        if (ov3d != null){ // see if reiziger exists
            System.out.print("Delete ov3 : ");
            System.out.println( odao.delete(ov3d) ? "success" : "error");
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
        System.out.print(ANSI_GREEN + "\n[Test] Eerst " + ANSI_RESET + reizigers.size() + ANSI_GREEN + " reizigers, na ReizigerDAO.save() " + ANSI_RESET);
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

        System.out.println(ANSI_PURPLE + "\n---------- Test P4 CHIPKAART / REIZIGER Een op Veel-------------" + ANSI_RESET);
        Reiziger r78 = new Reiziger(78,"H","van","Dijk",Date.valueOf(LocalDate.now().minusYears(50)));
        Reiziger r79 = new Reiziger(79,"D","de","pleijn",Date.valueOf(LocalDate.now().minusYears(20)));

        OVChipkaart ov1 = new OVChipkaart(1,Date.valueOf(LocalDate.now().plusYears(1)),1,78);
        OVChipkaart ov2 = new OVChipkaart(2,Date.valueOf(LocalDate.now().plusYears(1)),1,78);
        OVChipkaart ov3 = new OVChipkaart(3,Date.valueOf(LocalDate.now().plusYears(1)),2,79);
        r78.AddOvchipkaart(ov1);
        r79.AddOvchipkaart(ov3);

        rdao.save(r78);
        rdao.save(r79);
        odao.save(ov1);
        odao.save(ov3);

        System.out.println(ANSI_GREEN + "\n[Test] Get OVChipkaarten met relatie Reiziger (#78) 1/2" + ANSI_RESET);
        System.out.println(odao.findByReiziger(r78));
        System.out.println(ANSI_GREEN + "\n[Test] Voeg OVChipkaart toe met relatie Reiziger (#78) 2/2" + ANSI_RESET);
        r78.AddOvchipkaart(ov2);
        odao.save(ov2);
        System.out.println(odao.findByReiziger(r78));

        System.out.println(ANSI_GREEN + "\n[Test] Get OVChipkaart met kaartnummer: 3" + ANSI_RESET);
        OVChipkaart ov4 = odao.findByKaartnummer(3);
        System.out.println(ov4);

        System.out.println(ANSI_GREEN + "\n[Test] Get Reiziger vanuit OVChipkaart 3" + ANSI_RESET);
        System.out.println(ANSI_BLUE + ov4 + ANSI_RESET);
        System.out.println(rdao.findById(ov4.getReiziger_id()));

        System.out.println(ANSI_GREEN + "\n[Test] voeg saldo toe op chipkaart (#2)" + ANSI_RESET);
        System.out.println(odao.findByKaartnummer(2));
        System.out.println(ANSI_BLUE + "saldo toegevoegd!" + ANSI_RESET);
        ov2.addSaldo(25.50);
        odao.update(ov2);
        System.out.println(odao.findByKaartnummer(2));
        System.out.println(ANSI_GREEN + "\n[Test] verwijder chipkaart (#2)" + ANSI_RESET);
        System.out.println(ANSI_BLUE + "Reiziger 78 zijn/haar ovchipkaarten" + ANSI_RESET);
        System.out.println(odao.findByReiziger(r78));

        odao.delete(ov2);
        System.out.println(ANSI_BLUE + "Chipkaart #2 verwijderd" + ANSI_RESET);
        System.out.println(odao.findByReiziger(r78));

        System.out.println(ANSI_PURPLE + "TESTS ARE DONE!" + ANSI_RESET);
    }


}
