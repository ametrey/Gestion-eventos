package com.gestioneventos.dao;

import com.gestioneventos.model.compras.Compra;
import com.gestioneventos.model.compras.DetalleCompra;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompraDAO {
    private Connection connection;
    private DetalleCompraDAO detalleCompraDAO;

    public CompraDAO() {
        try {
            connection = DatabaseConnection.getConnection();
            detalleCompraDAO = new DetalleCompraDAO();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addCompra(Compra compra) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO compra (usuario_id, fecha, total, estado) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, compra.getUsuarioId());
            ps.setDate(2, new java.sql.Date(compra.getFecha().getTime()));
            ps.setDouble(3, compra.getTotal());
            ps.setString(4, compra.getEstado());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                compra.setId(rs.getInt(1));
            }

            for (DetalleCompra detalle : compra.getDetalles()) {
                detalleCompraDAO.addDetalleCompra(detalle, compra.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Compra> getComprasByUsuarioId(int usuarioId) {
        List<Compra> compras = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT * FROM compra WHERE usuario_id = ?");
            ps.setInt(1, usuarioId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Compra compra = new Compra(
                        rs.getInt("usuario_id"),
                        rs.getDate("fecha"),
                        rs.getDouble("total"),
                        rs.getString("estado"));
                compra.setId(rs.getInt("id"));
                compra.setDetalles(detalleCompraDAO.getDetallesCompra(compra.getId()));
                compras.add(compra);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return compras;
    }
}
