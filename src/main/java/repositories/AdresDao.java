package repositories;

import domain.Adres;
import domain.Reiziger;

import java.util.List;

public interface AdresDao {

    List<Adres> findAll();

    boolean save(Adres a);

    boolean update(Adres a);

    boolean delete(Adres a);

    Adres findByReiziger(Reiziger r);

    Adres findById(int i);
}
