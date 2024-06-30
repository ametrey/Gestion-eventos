package com.gestioneventos.dao;

import com.gestioneventos.model.usuarios.Comprador;
import com.gestioneventos.model.usuarios.Organizador;
import com.gestioneventos.model.usuarios.Usuario;

import java.sql.*;

public class UsuarioDAO {
    private Connection connection;

    public UsuarioDAO() {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Usuario getUsuarioByCredentials(String username, String password) {
        try {
            PreparedStatement ps = connection
                    .prepareStatement("SELECT * FROM usuario WHERE usuario = ? AND password = ?");
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String dni = rs.getString("dni");
                int tipoUsuario = rs.getInt("tipo_usuario_id");

                if (tipoUsuario == 2) {
                    return new Comprador(id, username, password, nombre, apellido, dni);
                } else if (tipoUsuario == 1) {
                    return new Organizador(id, username, password, nombre, apellido, dni);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addUsuario(Usuario usuario) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO usuario (usuario, password, nombre, apellido, dni, tipo_usuario_id) VALUES (?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, usuario.getUsuario());
            ps.setString(2, usuario.getPassword());
            ps.setString(3, usuario.getNombre());
            ps.setString(4, usuario.getApellido());
            ps.setString(5, usuario.getDni());
            ps.setInt(6, usuario.getTipoUsuario());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                usuario.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
