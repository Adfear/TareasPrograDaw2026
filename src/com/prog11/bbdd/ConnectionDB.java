/**
 *  @author adrian ferreira
 */

package com.prog11.bbdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
// imports y package correspondiente

// clase para realizar la conexion con la BBDD
public class ConnectionDB {
//datos para acceder a la bbdd como la contraseña y el usuario
    private static final String URL = "jdbc:mariadb://localhost:3306/concesionario";
    private static final String USER = "root";
    private static final String PASS = "123Abc.";

    // Variable para guardar el estado de la conexión
    private Connection con;

    /**
     * Método para abrir la conexión con la bbdd
     * @return El objeto Connection abierto o null si no abre bien
     */
    public Connection openConnection() {
        try {
            // Intentamos establecer la conexión
            con = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("-- Conexión a la base de datos establecida");
        } catch (SQLException e) {
            System.err.println("-- Error al abrir la conexión: " + e.getMessage());
        }
        return con;
    }

    /**
     * Método para cerrar la conexión con la bbdd
     */
    public void closeConnection() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
                System.out.println("-- Conexión cerrada correctamente");
            }
        } catch (SQLException e) {
            System.err.println("-- Error al cerrar: " + e.getMessage());
        }
    }
}