package com.github.santiago.dao;

import com.github.santiago.model.Categoria;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CategoriaDAO {

    // Listar todas las categorías
    ArrayList<Categoria> listar() throws SQLException;
}
