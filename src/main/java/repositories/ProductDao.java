package repositories;

import domain.Adres;
import domain.Product;

public interface ProductDao {
    boolean save(Product p);

    boolean update(Product p);

    boolean delete(Product p);

    Product findByProductNumber(int id);
}
