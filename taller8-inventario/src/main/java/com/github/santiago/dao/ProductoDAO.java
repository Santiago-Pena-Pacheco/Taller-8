package com.github.santiago.dao;

import com.github.santiago.model.Producto;
import java.sql.SQLException;
import java.util.List;

public interface ProductoDAO {

    // Insertar un producto
    boolean insertar(Producto p) throws SQLException;

    // Listar todos los productos (con su categoría)
    List<Producto> listar() throws SQLException;

    // Buscar un producto por su llave primaria
    Producto buscarPorId(int id) throws SQLException;

    // Actualizar un producto
    boolean actualizar(Producto p) throws SQLException;

    // Eliminar un producto por su id
    boolean eliminar(int id) throws SQLException;
}
