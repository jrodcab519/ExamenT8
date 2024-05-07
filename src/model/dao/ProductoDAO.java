package model.dao;

import model.entities.Producto;

import java.util.List;

public interface ProductoDAO {
    List<Producto> findAll();
    Producto findById(int codigo);
    Producto save(Producto producto);
    void update(Producto producto);
    void delete(Producto producto);
}
