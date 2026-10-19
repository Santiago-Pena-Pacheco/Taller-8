package com.github.santiago;

import com.github.santiago.conexion.Conexion;
import com.github.santiago.vista.VentanaInventario;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class App {

    public static void main(String[] args) {
        // Prueba de la conexión antes de abrir la ventana
        try (Connection con = Conexion.getConexion()) {
            System.out.println("Conexión exitosa");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return;
        }

        java.awt.EventQueue.invokeLater(() -> new VentanaInventario().setVisible(true));
    }
}
