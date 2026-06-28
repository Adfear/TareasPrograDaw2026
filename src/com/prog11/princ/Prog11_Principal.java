/**
 *  @author adrian ferreira
 */
package com.prog11.princ;
// imports y package correspondiente
import com.prog11.bbdd.*;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
// clase principal donde se ejecuta el programa
public class Prog11_Principal {

    public static void main(String[] args) {
        // 1. Instanciamos la clase del paquete bbdd
        ConnectionDB db = new ConnectionDB();
        // 2. CREAMOS la conexión aquí (como dice el enunciado)
        Connection con = db.openConnection();

        if (con != null) {
            System.out.println("--- INICIANDO PRUEBAS DEL SISTEMA ---");
            // 1: Insertar Propietarios primero
            // Usamos IDs altos para no repetir  con lo que ya tengas en la BBDD
            PropietariosDAO.insertarPropietario(con, 11, "Adrian", "11111111A");
            PropietariosDAO.insertarPropietario(con, 21, "Maria", "22222222B");
            // para ver que se guarde bien porque el resto los elimina si ejecutamos todo de seguido
            // PropietariosDAO.insertarPropietario(22, "Mariana", "22232222B");
            System.out.println("\n ---- Propietarios añadidos");
            VehiculosDAO.insertarVehiculo(con, "1234BBB", "Seat", 5000, 15000, 11);
            VehiculosDAO.insertarVehiculo(con, "5678CCC", "BMW", 100, 40000, 11);
            VehiculosDAO.insertarVehiculo(con, "9012DDD", "Seat", 80000, 5000, 21);
            // para ver que se guarde bien porque el resto los elimina
            // VehiculosDAO.insertarVehiculo("9012DlD", "Seat", 80000, 5000, 22);
            System.out.println("\n ---- Vehiculos añadidos");
            // 2 Listar todos los vehículos
            System.out.println("\n2. Listado inicial de todos los vehículos:");
            mostrarLista(VehiculosDAO.recuperarTodosVehiculos(con));
            // 3 Actualizar propietario de un vehículo (Cambiamos el Seat de Maria a Adrian)
            System.out.println("\n3. Actualizando propietario del vehículo 9012DDD...");
            int propActu = VehiculosDAO.actualizarPropietarioVehiculo(con,"9012DDD", 11);
            System.out.println(propActu == 0 ? " Propietario actualizado." : " Error al actualizar.");
            // 4 Listar todos los vehículos
            System.out.println("\n4. Listado tras la actualización:");
            mostrarLista(VehiculosDAO.recuperarTodosVehiculos(con));
            // 5  Eliminar un vehículo que exista
            System.out.println("\n5. Eliminando vehículo existente (1234BBB)...");
            int eliminarVehi = VehiculosDAO.eliminarVehiculo(con,"1234BBB");
            System.out.println(eliminarVehi == 0 ? " Vehículo eliminado." : " El vehículo no existía.");
            // 6 Eliminar un vehículo que no exista
            System.out.println("\n6. Intentando eliminar vehículo inexistente (0000ZZZ)...");
            int eliminarVehiNo = VehiculosDAO.eliminarVehiculo(con,"0000ZZZ");
            System.out.println(eliminarVehiNo == 0 ? " Vehículo eliminado." : " El vehículo no existe (Comportamiento esperado).");
            // 7 Listar todos los vehículos
            System.out.println("\n7. Listado tras los borrados:");
            mostrarLista(VehiculosDAO.recuperarTodosVehiculos(con));
            // 8 Listar los vehículos de una marca (Seat)
            System.out.println("\n8. Listando vehículos de la marca 'Seat':");
            mostrarLista(VehiculosDAO.recuperarVehiculosPorMarca(con,"Seat"));
            // 9 Listar todos los vehículos de un propietario (Adrian)
            System.out.println("\n9. Listando vehículos del propietario con DNI 11111111A:");
            mostrarLista(PropietariosDAO.recuperarVehiculos(con,"11111111A"));
            // 10 Eliminar un propietario con vehículos (Adrian todavía tiene coches)
            System.out.println("\n10. Intentando eliminar propietario con vehículos (DNI: 11111111A)...");
            int eliminarPropCon = PropietariosDAO.eliminarPropietario(con,"11111111A");
            System.out.println(eliminarPropCon > 0 ? " Propietario eliminado." : " No se pudo eliminar (Posible restricción de integridad).");
            // 11 Eliminar un propietario sin vehículos (Maria ya no tiene coches tras el update)
            System.out.println("\n11. Eliminando propietario sin vehículos (DNI: 22222222B)...");
            int eliminarPropSin = PropietariosDAO.eliminarPropietario(con, "22222222B");
            System.out.println(eliminarPropSin > 0 ? " Propietario eliminado." : " No se pudo eliminar.");
            System.out.println("\n---  PRUEBAS FINALIZADAS ---");
            db.closeConnection();
        } else {
            System.err.println("No se pudo iniciar el programa: Sin conexión.");
        }

        System.out.println("\n--- PRUEBAS FINALIZADAS ---");
    }

    /**
     * Método  para imprimir las listas por consola
     */
    private static void mostrarLista(List<String> lista) {
        if (lista == null) {
            System.out.println("lista  nula");
        } else if (lista.isEmpty()) {
            System.out.println(" ista vacía");
        } else {
            for (String i : lista) {
                System.out.println("   • " + i);
            }
        }
    }
}