package model.dao;

import datasource.DataSource;
import model.entities.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOImpl implements ProductoDAO{
    private final Connection connection;

    public ProductoDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Producto> findAll() {
        List<Producto> productos = new ArrayList<Producto>();

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "select * from producto order by codigo";
            ps = connection.prepareStatement(sql);

            rs = ps.executeQuery();


            while(rs.next()){
                Producto producto = new Producto();
                producto.setCodigo(rs.getInt("codigo"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setStock(rs.getInt("stock"));
                productos.add(producto);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DataSource.closeStatement(ps);
            DataSource.closeResultSet(rs);
        }
        return productos;
    }

    @Override
    public Producto findById(int codigo) {

        Producto producto =null;

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "select * from producto where codigo = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, codigo);

            rs = ps.executeQuery();


            while(rs.next()){
                producto = new Producto();
                producto.setCodigo(rs.getInt("codigo"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setStock(rs.getInt("stock"));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DataSource.closeStatement(ps);
            DataSource.closeResultSet(rs);
        }
        return producto;
    }

    @Override
    public Producto save(Producto producto) {
       PreparedStatement ps = null;

       try{
           String sql = "insert into producto (codigo, descripcion, precio, stock) values (?, ?, ?,?)";
           ps = connection.prepareStatement(sql);
           ps.setInt(1, producto.getCodigo());
           ps.setString(2, producto.getDescripcion());
           ps.setDouble(3, producto.getPrecio());
           ps.setInt(4, producto.getStock());

           ps.executeUpdate();

       } catch (SQLException e) {
           throw new RuntimeException(e);
       } finally {
           DataSource.closeStatement(ps);
       }
        return producto;
    }

    @Override
    public void update(Producto producto) {
        PreparedStatement ps = null;

        try{
            String sql = "update producto set descripcion = ?, precio = ?, stock = ? where codigo = ?";
            ps = connection.prepareStatement(sql);

            ps.setString(1, producto.getDescripcion());
            ps.setDouble(2, producto.getPrecio());
            ps.setInt(3, producto.getStock());
            ps.setInt(4, producto.getCodigo());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DataSource.closeStatement(ps);
        }

    }

    @Override
    public void delete(Producto producto) {
        PreparedStatement ps = null;

        try {
            String sql = "delete from producto where codigo = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, producto.getCodigo());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DataSource.closeStatement(ps);
        }
    }
}
