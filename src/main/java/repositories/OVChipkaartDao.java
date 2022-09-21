package repositories;

import domain.OVChipkaart;
import domain.Reiziger;

import java.util.List;

public interface OVChipkaartDao {

    OVChipkaart findByKaartnummer(int kn);
    List<OVChipkaart> findByReiziger(Reiziger r);
    boolean save(OVChipkaart o);

    boolean update(OVChipkaart o);

    boolean delete(OVChipkaart o);
}
