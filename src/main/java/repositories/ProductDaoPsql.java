package repositories;

import domain.Product;

import java.sql.Connection;

public class ProductDaoPsql implements ProductDao {

    Connection conn;
    public ProductDaoPsql(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean save(Product p) {
        return false;
    }

    @Override
    public boolean update(Product p) {
        return false;
    }

    @Override
    public boolean delete(Product p) {
        return false;
    }

    @Override
    public Product findByProductNumber(int id) {
        return null;
    }
}
