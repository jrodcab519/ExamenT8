package model.services;

import model.entities.Producto;

import java.util.List;

public interface ProductoService {
    List<Producto> getList();
    Producto getById(int codigo);
    Producto save(Producto producto);
    void update(Producto producto);
    void delete(Producto producto);
}
