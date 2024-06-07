package com.gestioneventos.usuarios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import com.gestioneventos.DatabaseConnection;
import com.gestioneventos.compras.Compra;

public class Comprador extends Usuario {
    private List<Compra> comprasRealizadas;

    public Comprador(int id, String usuario, String password, String nombre, String apellido, String dni) {
        super(usuario, password, nombre, apellido, dni, 2); // Asumiendo 2 es el ID para 'Comprador'
        this.id = id;
        this.comprasRealizadas = new ArrayList<>();
    }

    public void listarEventos() {
        String sql = "SELECT * FROM evento";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            if (!rs.isBeforeFirst()) {
                System.out.println("No hay eventos disponibles.");
                return;
            }

            System.out.println("Eventos disponibles:");
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String descripcion = rs.getString("descripcion");
                String fecha = rs.getString("fecha");
                int cantidadTickets = rs.getInt("cantidad_tickets");
                double precio = rs.getDouble("precio");
                System.out.printf("ID: %d, Nombre: %s, Descripción: %s, Fecha: %s, Tickets: %d, Precio: %.2f%n", id,
                        nombre, descripcion, fecha, cantidadTickets, precio);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int buscarEventoPorNombre(String nombreEvento) {
        String sql = "SELECT id FROM evento WHERE nombre = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombreEvento);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Evento no encontrado
    }

    public void comprarTicket(int eventoId, int cantidad) {
        String sqlEvento = "SELECT cantidad_tickets, precio FROM evento WHERE id = ? AND cantidad_tickets >= ?";
        String sqlCompra = "INSERT INTO compra (usuario_id, fecha, total, estado) VALUES (?, NOW(), ?, 'Completo')";
        String sqlDetalleCompra = "INSERT INTO detalle_compra (compra_id, evento_id, cantidad, precio) VALUES (?, ?, ?, ?)";
        String sqlActualizarEvento = "UPDATE evento SET cantidad_tickets = cantidad_tickets - ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmtEvento = conn.prepareStatement(sqlEvento);
                PreparedStatement pstmtCompra = conn.prepareStatement(sqlCompra,
                        PreparedStatement.RETURN_GENERATED_KEYS);
                PreparedStatement pstmtDetalleCompra = conn.prepareStatement(sqlDetalleCompra);
                PreparedStatement pstmtActualizarEvento = conn.prepareStatement(sqlActualizarEvento)) {

            // Verificar disponibilidad de tickets
            pstmtEvento.setInt(1, eventoId);
            pstmtEvento.setInt(2, cantidad);
            ResultSet rsEvento = pstmtEvento.executeQuery();

            if (rsEvento.next()) {
                int cantidadDisponible = rsEvento.getInt("cantidad_tickets");
                double precioUnitario = rsEvento.getDouble("precio");

                if (cantidadDisponible < cantidad) {
                    System.out.println("No hay suficientes tickets disponibles.");
                    return;
                }

                // Crear compra
                double total = cantidad * precioUnitario;
                pstmtCompra.setInt(1, this.id);
                pstmtCompra.setDouble(2, total);
                pstmtCompra.executeUpdate();

                ResultSet generatedKeys = pstmtCompra.getGeneratedKeys();
                if (generatedKeys.next()) {
                    long compraId = generatedKeys.getLong(1);

                    // Insertar detalles de la compra
                    pstmtDetalleCompra.setLong(1, compraId);
                    pstmtDetalleCompra.setInt(2, eventoId);
                    pstmtDetalleCompra.setInt(3, cantidad);
                    pstmtDetalleCompra.setDouble(4, precioUnitario); // Almacenar el precio unitario
                    pstmtDetalleCompra.executeUpdate();

                    // Actualizar cantidad de tickets disponibles
                    pstmtActualizarEvento.setInt(1, cantidad);
                    pstmtActualizarEvento.setInt(2, eventoId);
                    pstmtActualizarEvento.executeUpdate();

                    System.out.println("Compra realizada exitosamente.");
                }
            } else {
                System.out.println("No se pudo verificar la disponibilidad de tickets.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cargarComprasRealizadas() {
        String sqlCompra = "SELECT * FROM compra WHERE usuario_id = ?";
        String sqlDetalleCompra = "SELECT dc.*, e.nombre AS nombre_evento, e.precio AS precio_unitario " +
                "FROM detalle_compra dc " +
                "JOIN evento e ON dc.evento_id = e.id " +
                "WHERE dc.compra_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmtCompra = conn.prepareStatement(sqlCompra);
                PreparedStatement pstmtDetalleCompra = conn.prepareStatement(sqlDetalleCompra)) {

            pstmtCompra.setInt(1, this.id);
            ResultSet rsCompra = pstmtCompra.executeQuery();

            // Limpiar lista de compras realizadas antes de cargar nuevas compras
            comprasRealizadas.clear();

            while (rsCompra.next()) {
                int compraId = rsCompra.getInt("id");
                Date fecha = rsCompra.getTimestamp("fecha");
                double total = rsCompra.getDouble("total");
                String estado = rsCompra.getString("estado");

                Compra compra = new Compra(compraId, this, fecha, total, estado);
                pstmtDetalleCompra.setInt(1, compraId);
                ResultSet rsDetalleCompra = pstmtDetalleCompra.executeQuery();

                while (rsDetalleCompra.next()) {
                    int eventoId = rsDetalleCompra.getInt("evento_id");
                    int cantidad = rsDetalleCompra.getInt("cantidad");
                    double precioUnitario = rsDetalleCompra.getDouble("precio_unitario");
                    String nombreEvento = rsDetalleCompra.getString("nombre_evento");

                    compra.agregarDetalle(eventoId, cantidad, precioUnitario, nombreEvento);
                }

                comprasRealizadas.add(compra);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Compra> getComprasRealizadas() {
        return comprasRealizadas;
    }
}
