package views;

import model.entities.Producto;
import model.services.ProductoService;
import model.services.ProductoServiceImpl;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProdudctoView extends JPanel {
    ProductoService service;
    JTable listadoTable;
    private JPanel formPanel;
    JTextField _codigo;
    JTextField _descripcion;
    JTextField _precio;
    JTextField _stock;
    JButton addButton;
    JButton updateButton;
    JButton deleteButton;
    JButton clearButton;
    public ProdudctoView(){
        service = new ProductoServiceImpl();


        this.setLayout(new GridLayout(2,1));

        listadoTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(listadoTable);
        this.add(scrollPane, BorderLayout.CENTER);

        crearFormulario();

        this.add(formPanel, BorderLayout.SOUTH);
        formPanel.setVisible(true);

        listadoTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = listadoTable.getSelectedRow();
                if (selectedRow >= 0) {
                    showProductForm(selectedRow);
                }
            }
        });

        showProducts();
        this.setVisible(true);
    }

    private void showProducts() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Código");
        model.addColumn("Descripción");
        model.addColumn("Precio");
        model.addColumn("Stock");

        List<Producto> productos = service.getList();

        for (Producto producto : productos){
            model.addRow(new Object[]{
                    producto.getCodigo(),
                    producto.getDescripcion(),
                    producto.getPrecio(),
                    producto.getStock()
            });
        }
        listadoTable.setModel(model);
        formPanel.setVisible(true);
    }

    private void showProductForm(int rowIndex) {
        DefaultTableModel model = (DefaultTableModel) listadoTable.getModel();
        int codigo = (int) model.getValueAt(rowIndex, 0);

        Producto producto = service.getById(codigo);

        _codigo.setText(String.valueOf(producto.getCodigo()));
       _descripcion.setText(producto.getDescripcion());
        _precio.setText(String.valueOf(producto.getPrecio()));
        _stock.setText(String.valueOf(producto.getStock()));
    }

    private void crearFormulario() {
        formPanel = new JPanel(new GridLayout(3, 2));

        formPanel.setBorder(BorderFactory.createTitledBorder("Detalles del Producto"));
        formPanel.add(new JLabel("Código:"));
        _codigo = new JTextField();
        formPanel.add(_codigo);

        formPanel.add(new JLabel("Descripción:"));
        _descripcion = new JTextField();
        formPanel.add(_descripcion);

        formPanel.add(new JLabel("Precio:"));
        _precio = new JTextField();
        formPanel.add(_precio);

        formPanel.add(new JLabel("Stock:"));
        _stock = new JTextField();
        formPanel.add(_stock);

        addButton = new JButton("Nuevo");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProduct();
            }
        });
        formPanel.add(addButton);

        updateButton = new JButton("Actualizar");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateProduct();
            }
        });
        formPanel.add(updateButton);

        deleteButton = new JButton("Eliminar");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteProduct();
            }
        });
        formPanel.add(deleteButton);

        clearButton = new JButton("Limpiar");

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });
        formPanel.add(clearButton);

    }

    private void addProduct() {
        String codigo = _codigo.getText();
        String descripcion = _descripcion.getText();
        String precio = _precio.getText();
        String stock = _stock.getText();


        Producto producto = new Producto();

        producto.setCodigo(Integer.valueOf(codigo));
        producto.setDescripcion(descripcion);
        producto.setPrecio(Double.valueOf(precio));
        producto.setStock(Integer.valueOf(stock));


        DefaultTableModel model = (DefaultTableModel) listadoTable.getModel();

        service.save(producto);

        model.addRow(new Object[]{
                producto.getCodigo(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getStock()

        });

        clearForm();
    }

    private void updateProduct() {

        String codigo = _codigo.getText();
        String descripcion = _descripcion.getText();
        String precio = _precio.getText();
        String stock = _stock.getText();


        Producto producto = new Producto();

        producto.setCodigo(Integer.valueOf(codigo));
        producto.setDescripcion(descripcion);
        producto.setPrecio(Double.valueOf(precio));
        producto.setStock(Integer.valueOf(stock));

        service.update(producto);
        showProducts();
    }

    private void deleteProduct() {
        String codigo = _codigo.getText();

        Producto producto = new Producto();
        producto.setCodigo(Integer.valueOf(codigo));
        service.delete(producto);
        showProducts();
    }

    private void clearForm() {
        _codigo.setText("");
        _descripcion.setText("");
        _precio.setText("");
        _stock.setText("");

    }
}
