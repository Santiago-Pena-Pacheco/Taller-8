package com.github.santiago.dao;

import com.github.santiago.conexion.Conexion;
import com.github.santiago.model.Categoria;
import com.github.santiago.model.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOImpl implements ProductoDAO {

    private static final String SELECT
            = "SELECT p.id_producto, p.nombre, p.precio, p.cantidad, "
            + "c.id_categoria, c.nombre AS nombre_categoria "
            + "FROM producto p "
            + "JOIN categoria c ON p.id_categoria = c.id_categoria ";

    @Override
    public boolean insertar(Producto p) throws SQLException {
        String sql = "INSERT INTO producto (nombre, precio, cantidad, id_categoria) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection con = Conexion.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getNombre());
            ps.setDouble(2, p.getPrecio());
            ps.setInt(3, p.getCantidad());
            ps.setInt(4, p.getCategoria().getIdCategoria());

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public List<Producto> listar() throws SQLException {
        List<Producto> productos = new ArrayList<>();

        String sql = SELECT + "ORDER BY p.id_producto";

        try (Connection con = Conexion.getConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                productos.add(armarProducto(rs));
            }
        }

        return productos;
    }

    @Override
    public Producto buscarPorId(int id) throws SQLException {
        String sql = SELECT + "WHERE p.id_producto = ?";

        try (Connection con = Conexion.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return armarProducto(rs);
                }
            }
        }

        return null;
    }

    @Override
    public boolean actualizar(Producto p) throws SQLException {
        String sql = "UPDATE producto SET nombre = ?, precio = ?, cantidad = ?, "
                + "id_categoria = ? WHERE id_producto = ?";

        try (Connection con = Conexion.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getNombre());
            ps.setDouble(2, p.getPrecio());
            ps.setInt(3, p.getCantidad());
            ps.setInt(4, p.getCategoria().getIdCategoria());
            ps.setInt(5, p.getIdProducto());

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM producto WHERE id_producto = ?";

        try (Connection con = Conexion.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;
        }
    }

    // Construye un Producto (con su Categoria) a partir de una fila del JOIN
    private Producto armarProducto(ResultSet rs) throws SQLException {
        Categoria categoria = new Categoria(
                rs.getInt("id_categoria"),
                rs.getString("nombre_categoria")
        );
        return new Producto(
                rs.getInt("id_producto"),
                rs.getString("nombre"),
                rs.getDouble("precio"),
                rs.getInt("cantidad"),
                categoria
        );
    }
}
