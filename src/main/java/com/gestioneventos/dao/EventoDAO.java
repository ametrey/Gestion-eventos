package com.gestioneventos.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.gestioneventos.model.eventos.Evento;

public class EventoDAO {
    private Connection connection;

    public EventoDAO() {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addEvento(Evento evento) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO evento (nombre, descripcion, fecha, cantidad_tickets, precio, organizador_id) VALUES (?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, evento.getNombre());
            ps.setString(2, evento.getDescripcion());
            ps.setDate(3, new java.sql.Date(evento.getFecha().getTime()));
            ps.setInt(4, evento.getCantidadTickets());
            ps.setDouble(5, evento.getPrecioTicket());
            ps.setInt(6, evento.getOrganizadorId());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                evento.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Evento> getAllEventos() {
        List<Evento> eventos = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM evento");
            while (rs.next()) {
                Evento evento = new Evento();
                evento.setId(rs.getInt("id"));
                evento.setNombre(rs.getString("nombre"));
                evento.setDescripcion(rs.getString("descripcion"));
                evento.setFecha(rs.getDate("fecha"));
                evento.setCantidadTickets(rs.getInt("cantidad_tickets"));
                evento.setPrecioTicket(rs.getDouble("precio"));
                evento.setOrganizadorId(rs.getInt("organizador_id"));
                eventos.add(evento);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return eventos;
    }

    public List<Evento> getEventosByOrganizador(int organizadorId) throws SQLException {
        List<Evento> eventos = new ArrayList<>();
        String sql = "SELECT * FROM evento WHERE organizador_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, organizadorId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Evento evento = new Evento(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("descripcion"),
                            rs.getTimestamp("fecha"),
                            rs.getInt("cantidad_tickets"),
                            rs.getDouble("precio"),
                            rs.getString("estado"));
                    evento.setOrganizadorId(rs.getInt("organizador_id"));
                    eventos.add(evento);
                }
            }
        }
        return eventos;
    }

    public void updateEvento(Evento evento) throws SQLException {
        String sql = "UPDATE evento SET nombre = ?, descripcion = ?, fecha = ?, cantidad_tickets = ?, precio = ?, organizador_id = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, evento.getNombre());
            ps.setString(2, evento.getDescripcion());
            ps.setTimestamp(3, new java.sql.Timestamp(evento.getFecha().getTime()));
            ps.setInt(4, evento.getCantidadTickets());
            ps.setDouble(5, evento.getPrecioTicket());
            ps.setInt(6, evento.getOrganizadorId());
            ps.setInt(7, evento.getId());
            ps.executeUpdate();
        }
    }

    public void deleteEvento(int eventoId) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "DELETE FROM evento WHERE id = ?");
            ps.setInt(1, eventoId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Evento getEventoById(int eventoId) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM evento WHERE id = ?");
            ps.setInt(1, eventoId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Evento evento = new Evento();
                evento.setId(rs.getInt("id"));
                evento.setNombre(rs.getString("nombre"));
                evento.setDescripcion(rs.getString("descripcion"));
                evento.setFecha(rs.getDate("fecha"));
                evento.setCantidadTickets(rs.getInt("cantidad_tickets"));
                evento.setPrecioTicket(rs.getDouble("precio"));
                evento.setOrganizadorId(rs.getInt("organizador_id"));
                return evento;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
