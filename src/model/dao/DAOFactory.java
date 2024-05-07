package model.dao;

import datasource.DataSource;

public class DAOFactory {
    public static ProductoDAO createProductoDao(){
        return  new ProductoDAOImpl(DataSource.getConnection());
    }
}
