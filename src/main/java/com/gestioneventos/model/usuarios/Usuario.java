package com.gestioneventos.model.usuarios;

public class Usuario {
    private int id;
    private String usuario;
    private String password;
    private String nombre;
    private String apellido;
    private String dni;
    private int tipoUsuario; // 0 = Comprador, 1 = Organizador

    public Usuario(int id, String usuario, String password, String nombre, String apellido, String dni,
            int tipoUsuario) {
        this.id = id;
        this.usuario = usuario;
        this.password = password;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.tipoUsuario = tipoUsuario;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public int getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(int tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    // Método para iniciar sesión
    public static Usuario iniciarSesion(String usuario, String password) {
        // Aquí se realizará la lógica para verificar las credenciales del usuario
        // y devolver una instancia de Usuario si las credenciales son correctas.
        // Esta lógica debería moverse a UsuarioDAO en una implementación completa de
        // MVC.
        return null; // Placeholder
    }
}
