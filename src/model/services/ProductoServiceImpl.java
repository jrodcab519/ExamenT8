package model.services;

import model.dao.DAOFactory;
import model.dao.ProductoDAO;
import model.entities.Producto;

import java.util.List;

public class ProductoServiceImpl implements ProductoService{
    private final ProductoDAO dao;

    public ProductoServiceImpl() {
        dao = DAOFactory.createProductoDao();
    }

    @Override
    public List<Producto> getList() {
        return dao.findAll();
    }

    @Override
    public Producto getById(int codigo) {
        return dao.findById(codigo);
    }

    @Override
    public Producto save(Producto producto) {
        return dao.save(producto);
    }

    @Override
    public void update(Producto producto) {
        dao.update(producto);
    }

    @Override
    public void delete(Producto producto) {
        dao.delete(producto);
    }
}
