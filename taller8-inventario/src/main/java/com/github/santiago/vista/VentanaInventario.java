package com.github.santiago.vista;

import com.github.santiago.dao.CategoriaDAO;
import com.github.santiago.dao.CategoriaDAOImpl;
import com.github.santiago.dao.ProductoDAO;
import com.github.santiago.dao.ProductoDAOImpl;
import com.github.santiago.model.Categoria;
import com.github.santiago.model.Producto;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class VentanaInventario extends JFrame {

    ProductoDAO productoDAO = new ProductoDAOImpl();
    CategoriaDAO categoriaDAO = new CategoriaDAOImpl();
    DefaultTableModel modeloTabla;

    // id del producto seleccionado en la tabla (-1 si no hay ninguno)
    int idSeleccionado = -1;

    private JTextField campoNombre;
    private JTextField campoPrecio;
    private JTextField campoCantidad;
    private JComboBox<Categoria> comboCategoria;
    private JButton BtnGuardar;
    private JButton BtnActualizar;
    private JButton BtnEliminar;
    private JButton BtnLimpiar;
    private JTable tablaProductos;

    public VentanaInventario() {
        initComponents();

        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Precio");
        modeloTabla.addColumn("Cant.");
        modeloTabla.addColumn("Categoría");
        tablaProductos.setModel(modeloTabla);

        this.cargarCategorias();
        this.actualizarTabla();
    }

    private void initComponents() {
        setTitle("Inventario — Tienda de Don Ernesto");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(650, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        campoNombre = new JTextField();
        campoPrecio = new JTextField();
        campoCantidad = new JTextField();
        comboCategoria = new JComboBox<>();
        BtnGuardar = new JButton("Guardar");
        BtnActualizar = new JButton("Actualizar");
        BtnEliminar = new JButton("Eliminar");
        BtnLimpiar = new JButton("Limpiar");
        tablaProductos = new JTable();

        JPanel formulario = new JPanel(new GridLayout(0, 2, 5, 5));
        formulario.add(new JLabel("Nombre:"));
        formulario.add(campoNombre);
        formulario.add(new JLabel("Precio:"));
        formulario.add(campoPrecio);
        formulario.add(new JLabel("Cantidad:"));
        formulario.add(campoCantidad);
        formulario.add(new JLabel("Categoría:"));
        formulario.add(comboCategoria);

        JPanel botones = new JPanel();
        botones.add(BtnGuardar);
        botones.add(BtnActualizar);
        botones.add(BtnEliminar);
        botones.add(BtnLimpiar);

        JPanel arriba = new JPanel(new BorderLayout());
        arriba.add(formulario, BorderLayout.CENTER);
        arriba.add(botones, BorderLayout.SOUTH);

        add(arriba, BorderLayout.NORTH);
        add(new JScrollPane(tablaProductos), BorderLayout.CENTER);

        BtnGuardar.addActionListener(evt -> BtnGuardarActionPerformed());
        BtnActualizar.addActionListener(evt -> BtnActualizarActionPerformed());
        BtnEliminar.addActionListener(evt -> BtnEliminarActionPerformed());
        BtnLimpiar.addActionListener(evt -> vaciarCampos());

        tablaProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaProductosMouseClicked();
            }
        });
    }

    private void BtnGuardarActionPerformed() {
        Producto p = this.leerFormulario();
        if (p == null) {
            return;
        }
        try {
            if (productoDAO.insertar(p)) {
                this.mostrarMensaje("Producto guardado correctamente");
            } else {
                this.mostrarMensaje("No se pudo guardar el producto");
            }
            this.actualizarTabla();
            this.vaciarCampos();
        } catch (SQLException e) {
            this.mostrarMensaje(e.getMessage());
        }
    }

    private void BtnActualizarActionPerformed() {
        if (idSeleccionado == -1) {
            this.mostrarMensaje("Seleccione un producto de la tabla");
            return;
        }
        Producto p = this.leerFormulario();
        if (p == null) {
            return;
        }
        p.setIdProducto(idSeleccionado);
        try {
            if (productoDAO.actualizar(p)) {
                this.mostrarMensaje("Producto actualizado con éxito");
            } else {
                this.mostrarMensaje("Producto no encontrado");
            }
            this.actualizarTabla();
            this.vaciarCampos();
        } catch (SQLException e) {
            this.mostrarMensaje(e.getMessage());
        }
    }

    private void BtnEliminarActionPerformed() {
        if (idSeleccionado == -1) {
            this.mostrarMensaje("Seleccione un producto de la tabla");
            return;
        }
        int opcion = JOptionPane.showConfirmDialog(this,
                "¿Seguro que desea eliminar este producto?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            if (productoDAO.eliminar(idSeleccionado)) {
                this.mostrarMensaje("Producto eliminado con éxito");
            } else {
                this.mostrarMensaje("Producto no encontrado");
            }
            this.actualizarTabla();
            this.vaciarCampos();
        } catch (SQLException e) {
            this.mostrarMensaje(e.getMessage());
        }
    }

    // Al hacer clic en una fila se cargan los datos en el formulario
    private void tablaProductosMouseClicked() {
        int fila = tablaProductos.getSelectedRow();
        if (fila < 0) {
            return;
        }
        int id = (int) modeloTabla.getValueAt(fila, 0);
        try {
            Producto p = productoDAO.buscarPorId(id);
            if (p == null) {
                return;
            }
            idSeleccionado = p.getIdProducto();
            campoNombre.setText(p.getNombre());
            campoPrecio.setText(String.valueOf(p.getPrecio()));
            campoCantidad.setText(String.valueOf(p.getCantidad()));
            for (int i = 0; i < comboCategoria.getItemCount(); i++) {
                if (comboCategoria.getItemAt(i).getIdCategoria() == p.getCategoria().getIdCategoria()) {
                    comboCategoria.setSelectedIndex(i);
                }
            }
        } catch (SQLException e) {
            this.mostrarMensaje(e.getMessage());
        }
    }

    // Lee el formulario y arma un Producto; retorna null si hay algún error
    private Producto leerFormulario() {
        String nombre = campoNombre.getText().trim();
        String precio = campoPrecio.getText().trim();
        String cantidad = campoCantidad.getText().trim();

        if (nombre.isEmpty() || precio.isEmpty() || cantidad.isEmpty()) {
            this.mostrarMensaje("Todos los campos son obligatorios");
            return null;
        }
        if (comboCategoria.getSelectedItem() == null) {
            this.mostrarMensaje("No hay categorías disponibles");
            return null;
        }

        try {
            Producto p = new Producto();
            p.setNombre(nombre);
            p.setPrecio(Double.parseDouble(precio));
            p.setCantidad(Integer.parseInt(cantidad));
            p.setCategoria((Categoria) comboCategoria.getSelectedItem());
            return p;
        } catch (NumberFormatException e) {
            this.mostrarMensaje("El precio y la cantidad deben ser numéricos");
        } catch (IllegalArgumentException e) {
            this.mostrarMensaje(e.getMessage());
        }
        return null;
    }

    private void cargarCategorias() {
        try {
            for (Categoria c : categoriaDAO.listar()) {
                comboCategoria.addItem(c);
            }
        } catch (SQLException e) {
            this.mostrarMensaje(e.getMessage());
        }
    }

    private void vaciarCampos() {
        campoNombre.setText("");
        campoPrecio.setText("");
        campoCantidad.setText("");
        if (comboCategoria.getItemCount() > 0) {
            comboCategoria.setSelectedIndex(0);
        }
        idSeleccionado = -1;
        tablaProductos.clearSelection();
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        try {
            for (Producto p : productoDAO.listar()) {
                modeloTabla.addRow(new Object[]{
                    p.getIdProducto(), p.getNombre(), p.getPrecio(),
                    p.getCantidad(), p.getCategoria()});
            }
        } catch (SQLException e) {
            this.mostrarMensaje(e.getMessage());
        }
    }
}
