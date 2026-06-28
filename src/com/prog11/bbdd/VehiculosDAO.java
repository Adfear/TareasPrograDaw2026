/**
 *  @author adrian ferreira
 */
package com.prog11.bbdd;
// imports y package correspondiente
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
//clase que gestiona los vehiculos que tenemos
public class VehiculosDAO {
// aqui los insertamos en la bbdd
    public static int insertarVehiculo(Connection con, String mat, String marca, int kms, double precio, int idProp) {
        String sql = "INSERT INTO vehiculos (mat_veh, marca_veh, kms_veh, precio_veh, desc_veh, id_prop) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, mat);
            pstmt.setString(2, marca);
            pstmt.setInt(3, kms);
            pstmt.setDouble(4, precio);
            pstmt.setString(5, "");
            pstmt.setInt(6, idProp); // Este ID debe existir en la tabla propietarios
            pstmt.executeUpdate();
            return 0;
        } catch (SQLException e) {
            System.err.println("Error Vehículo (FK): " + e.getMessage());
            return -1;
        }
    }
// actualizamos el propietario
    public static int actualizarPropietarioVehiculo(Connection con, String mat, int idPropNuevo) {
        String sql = "UPDATE vehiculos SET id_prop = ? WHERE mat_veh = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, idPropNuevo);
            pstmt.setString(2, mat);
            int filas = pstmt.executeUpdate();
            return (filas > 0) ? 0 : -1;
        } catch (SQLException e) {
            return -1;
        }
    }
// eliminamos vehiculo
    public static int eliminarVehiculo(Connection con,String mat) {
        String sql = "DELETE FROM vehiculos WHERE mat_veh = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, mat);
            int filas = pstmt.executeUpdate();
            return (filas > 0) ? 0 : -1;
        } catch (SQLException e) {
            return -1;
        }
    }
// mostramos por lista todos los vehiculos uniendo ambsa tablas
    public static List<String> recuperarTodosVehiculos(Connection con) {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT v.mat_veh, v.marca_veh, v.kms_veh, v.precio_veh, p.nombre_prop " +
                "FROM vehiculos v JOIN propietarios p ON v.id_prop = p.id_prop";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(String.format("Matrícula: %s, Marca: %s, Kms: %d, Precio: %.2f, Dueño: %s",
                        rs.getString("mat_veh"), rs.getString("marca_veh"),
                        rs.getInt("kms_veh"), rs.getDouble("precio_veh"), rs.getString("nombre_prop")));
            }
        } catch (SQLException e) { }
        return lista;
    }
// devuelve una lista con los datos de los vehiculos en base a una marca
    public static List<String> recuperarVehiculosPorMarca(Connection con,String marca) {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT v.mat_veh, v.marca_veh, v.kms_veh, v.precio_veh, p.nombre_prop " +
                "FROM vehiculos v JOIN propietarios p ON v.id_prop = p.id_prop WHERE v.marca_veh = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, marca);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                lista.add(String.format("Matrícula: %s, Marca: %s, Kms: %d, Precio: %.2f, Dueño: %s",
                        rs.getString("mat_veh"), rs.getString("marca_veh"),
                        rs.getInt("kms_veh"), rs.getDouble("precio_veh"), rs.getString("nombre_prop")));
            }
        } catch (SQLException e) { }
        return lista;
    }
// devuelve una lista de los vehiculos
    public static List<String> recuperarVehiculos(Connection con) {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT mat_veh, marca_veh, kms_veh, precio_veh FROM vehiculos";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(String.format("Matrícula: %s, Marca: %s, Kms: %d, Precio: %.2f",
                        rs.getString("mat_veh"), rs.getString("marca_veh"),
                        rs.getInt("kms_veh"), rs.getDouble("precio_veh")));
            }
        } catch (SQLException e) { }
        return lista;
    }
}