/**
 *  @author adrian ferreira
 */
package com.prog11.bbdd;
// imports y package correspondiente
import com.prog11.princ.Prog11_Principal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
//clase que gestiona a los propietarios
public class PropietariosDAO {
// aqui los insertamos
    public static int insertarPropietario(Connection con,int id, String nombre, String dni) {
        String sql = "INSERT INTO propietarios (id_prop, nombre_prop, dni_prop) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, nombre);
            pstmt.setString(3, dni);
            pstmt.executeUpdate();
            return 0;
        } catch (SQLException e) {
            System.err.println("Error Propietario: " + e.getMessage());
            return -1;
        }

    }
// aqui devuelve una lista de los vehiculos que hay por dni
    public static List<String> recuperarVehiculos(Connection con,String dni) {
        List<String> lista = new ArrayList<>();
        // Buscamos por dni_prop y sacamos los datos con los nombres reales de la tabla vehiculo
        String sql = "SELECT v.mat_veh, v.marca_veh, v.kms_veh, v.precio_veh FROM vehiculos v " +
                "JOIN propietarios p ON v.id_prop = p.id_prop WHERE p.dni_prop = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, dni);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                lista.add(String.format("Matrícula: %s, Marca: %s, Kms: %d, Precio: %.2f",
                        rs.getString("mat_veh"), rs.getString("marca_veh"),
                        rs.getInt("kms_veh"), rs.getDouble("precio_veh")));
            }
            return lista;
        } catch (SQLException e) {
            return null;
        }
    }
// elimina a los propietarios
    public static int eliminarPropietario(Connection con,String dni) {
        String sql = "DELETE FROM propietarios WHERE dni_prop = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, dni);
            int filas = pstmt.executeUpdate();
            return filas;
        } catch (SQLException e) {
            System.err.println("Error al eliminar: " + e.getMessage());
            return 0;
        }
    }
}