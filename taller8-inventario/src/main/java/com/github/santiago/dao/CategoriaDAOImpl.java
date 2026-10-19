package com.github.santiago.dao;

import com.github.santiago.conexion.Conexion;
import com.github.santiago.model.Categoria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoriaDAOImpl implements CategoriaDAO {

    @Override
    public ArrayList<Categoria> listar() throws SQLException {
        ArrayList<Categoria> categorias = new ArrayList<>();

        String sql = "SELECT id_categoria, nombre FROM categoria ORDER BY nombre";

        try (Connection con = Conexion.getConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                categorias.add(new Categoria(
                        rs.getInt("id_categoria"),
                        rs.getString("nombre")
                ));
            }
        }

        return categorias;
    }
}
