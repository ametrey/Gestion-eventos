package com.gestioneventos.dao;

import com.gestioneventos.model.compras.DetalleCompra;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetalleCompraDAO {
    private Connection connection;

    public DetalleCompraDAO() {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addDetalleCompra(DetalleCompra detalle, int compraId) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO detalle_compra (compra_id, evento_id, cantidad, precio) VALUES (?, ?, ?, ?)");
            ps.setInt(1, compraId);
            ps.setInt(2, detalle.getEventoId());
            ps.setInt(3, detalle.getCantidad());
            ps.setDouble(4, detalle.getPrecio());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<DetalleCompra> getDetallesCompra(int compraId) {
        List<DetalleCompra> detalles = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT dc.*, e.nombre AS nombre_evento FROM detalle_compra dc " +
                            "JOIN evento e ON dc.evento_id = e.id " +
                            "WHERE dc.compra_id = ?");
            ps.setInt(1, compraId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DetalleCompra detalle = new DetalleCompra(
                        rs.getInt("evento_id"),
                        rs.getInt("cantidad"),
                        rs.getDouble("precio"),
                        rs.getString("nombre_evento") // Obtener el nombre del evento
                );
                detalles.add(detalle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detalles;
    }
}
