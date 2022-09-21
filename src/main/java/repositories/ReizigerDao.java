package repositories;

import domain.Reiziger;

import java.util.List;

public interface ReizigerDao {

    Reiziger findById(int id);

    List<Reiziger> findByGbdatum(String datum);

    List<Reiziger> findAll();

    boolean save(Reiziger r);

    boolean update(Reiziger r);

    boolean delete(Reiziger r);
}
