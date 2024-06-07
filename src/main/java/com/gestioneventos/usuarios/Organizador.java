package com.gestioneventos.usuarios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.gestioneventos.DatabaseConnection;
import com.gestioneventos.eventos.Evento;

public class Organizador extends Usuario {

    public Organizador(int id, String usuario, String password, String nombre, String apellido, String dni) {
        super(usuario, password, nombre, apellido, dni, 1); // Asumiendo 1 es el ID para 'Organizador'
        this.id = id;
    }

    public void listarEventos() {
        String sql = "SELECT * FROM evento WHERE organizador_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, this.id);
            ResultSet rs = pstmt.executeQuery();

            if (!rs.isBeforeFirst()) {
                System.out.println("No hay eventos disponibles.");
                return;
            }

            System.out.println("Eventos creados:");
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

    public void crearEvento(Evento evento) {
        String sql = "INSERT INTO evento (nombre, descripcion, fecha, estado, organizador_id, cantidad_tickets, precio) VALUES (?, ?, ?, 'Pendiente', ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, evento.getNombre());
            pstmt.setString(2, evento.getDescripcion());
            pstmt.setTimestamp(3, new java.sql.Timestamp(evento.getFecha().getTime()));
            pstmt.setInt(4, this.id);
            pstmt.setInt(5, evento.getCantidadTickets());
            pstmt.setDouble(6, evento.getPrecio());
            pstmt.executeUpdate();

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int eventoId = generatedKeys.getInt(1);
                evento.setId(eventoId);
            }

            System.out.println("Evento creado exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void modificarEvento(Evento evento) {
        String sql = "UPDATE evento SET nombre = ?, descripcion = ?, fecha = ?, estado = ?, cantidad_tickets = ?, precio = ? WHERE id = ? AND organizador_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, evento.getNombre());
            pstmt.setString(2, evento.getDescripcion());
            pstmt.setTimestamp(3, new java.sql.Timestamp(evento.getFecha().getTime()));
            pstmt.setString(4, evento.getEstado());
            pstmt.setInt(5, evento.getCantidadTickets());
            pstmt.setDouble(6, evento.getPrecio());
            pstmt.setInt(7, evento.getId());
            pstmt.setInt(8, this.id);
            pstmt.executeUpdate();

            System.out.println("Evento modificado exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarEvento(int eventoId) {
        String sql = "DELETE FROM evento WHERE id = ? AND organizador_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, eventoId);
            pstmt.setInt(2, this.id);
            pstmt.executeUpdate();
            System.out.println("Evento eliminado exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
