package com.gestioneventos.usuarios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.gestioneventos.DatabaseConnection;

public class Usuario {
    protected int id;
    protected String usuario;
    protected String password;
    protected String nombre;
    protected String apellido;
    protected String dni;
    protected int tipoUsuarioId;

    public Usuario(String usuario, String password, String nombre, String apellido, String dni, int tipoUsuarioId) {
        this.usuario = usuario;
        this.password = password;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.tipoUsuarioId = tipoUsuarioId;
    }

    public void registrarse() {
        String sql = "INSERT INTO usuario (usuario, password, nombre, apellido, dni, tipo_usuario_id) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, usuario);
            pstmt.setString(2, password);
            pstmt.setString(3, nombre);
            pstmt.setString(4, apellido);
            pstmt.setString(5, dni);
            pstmt.setInt(6, tipoUsuarioId);
            pstmt.executeUpdate();

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                this.id = generatedKeys.getInt(1);
            }

            System.out.println("Usuario registrado exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Usuario iniciarSesion(String usuario, String password) {
        String sql = "SELECT * FROM usuario WHERE usuario = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, usuario);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String dni = rs.getString("dni");
                int tipoUsuarioId = rs.getInt("tipo_usuario_id");

                if (tipoUsuarioId == 1) {
                    return new Organizador(id, usuario, password, nombre, apellido, dni);
                } else if (tipoUsuarioId == 2) {
                    return new Comprador(id, usuario, password, nombre, apellido, dni);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Usuario no encontrado o error
    }
}
