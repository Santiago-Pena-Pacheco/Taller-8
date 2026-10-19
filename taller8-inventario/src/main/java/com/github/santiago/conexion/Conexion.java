package com.github.santiago.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL
            = "jdbc:mysql://localhost:3306/tienda"
            + "?useSSL=false&serverTimezone=UTC";

    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConexion() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            // Se captura la excepción original y se lanza una con un mensaje claro
            throw new SQLException("No se pudo conectar a la base de datos 'tienda'.\n"
                    + "Verifique que MySQL esté encendido y que el usuario y la clave sean correctos.\n"
                    + "Detalle: " + e.getMessage(), e);
        }
    }
}
