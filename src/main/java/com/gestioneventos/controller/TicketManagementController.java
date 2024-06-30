package com.gestioneventos.controller;

import com.gestioneventos.dao.UsuarioDAO;
import com.gestioneventos.model.compras.Compra;
import com.gestioneventos.model.compras.DetalleCompra;
import com.gestioneventos.model.eventos.Evento;
import com.gestioneventos.model.usuarios.Comprador;
import com.gestioneventos.model.usuarios.Organizador;
import com.gestioneventos.model.usuarios.Usuario;

import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TicketManagementController {
    private Usuario usuarioActual;
    private UsuarioDAO usuarioDAO;

    public TicketManagementController() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public void registrarUsuario(String nombre, String apellido, String usuario, String password, String dni,
            int tipoUsuario) {
        int tipoUsuarioBD = tipoUsuario == 1 ? 2 : 1; // 2 para comprador, 1 para organizador
        Usuario nuevoUsuario = new Usuario(0, usuario, password, nombre, apellido, dni, tipoUsuarioBD);
        usuarioDAO.addUsuario(nuevoUsuario);
    }

    public boolean iniciarSesion(String usuario, String password) {
        usuarioActual = usuarioDAO.getUsuarioByCredentials(usuario, password);
        return usuarioActual != null;
    }

    public boolean esComprador() {
        return usuarioActual instanceof Comprador;
    }

    public boolean esOrganizador() {
        return usuarioActual instanceof Organizador;
    }

    public List<Evento> listarEventos() {
        if (usuarioActual instanceof Comprador) {
            Comprador comprador = (Comprador) usuarioActual;
            return comprador.listarEventos();
        } else if (usuarioActual instanceof Organizador) {
            Organizador organizador = (Organizador) usuarioActual;
            return organizador.listarEventos();
        }
        return null;
    }

    public boolean comprarTicket(int eventoId, int cantidad) {
        if (usuarioActual instanceof Comprador) {
            Comprador comprador = (Comprador) usuarioActual;
            return comprador.comprarTicket(eventoId, cantidad);
        }
        return false;
    }

    public List<Compra> cargarComprasRealizadas() {
        if (usuarioActual instanceof Comprador) {
            Comprador comprador = (Comprador) usuarioActual;
            comprador.cargarComprasRealizadas();
            return comprador.getComprasRealizadas();
        }
        return null;
    }

    public String crearEvento(String nombre, String descripcion, String fechaStr, int cantidadTickets,
            double precioTicket) {
        if (usuarioActual instanceof Organizador) {
            try {
                Organizador organizador = (Organizador) usuarioActual;
                Date fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fechaStr);
                Date fechaActual = new Date();
                if (!fecha.after(fechaActual)) {
                    return "La fecha del evento no puede ser igual o menor que la fecha actual.";
                }
                Evento evento = new Evento(nombre, descripcion, fecha, cantidadTickets, precioTicket);
                organizador.crearEvento(evento);
                return "Evento creado con éxito.";
            } catch (ParseException e) {
                return "Formato de fecha incorrecto.";
            }
        }
        return "No tiene permisos para crear un evento.";
    }

    public String modificarEvento(int eventoIdModificar, String nuevoNombre, String nuevaDescripcion,
            String nuevaFechaStr, int nuevaCantidadTickets, double nuevoPrecioTicket) {
        if (usuarioActual instanceof Organizador) {
            try {
                Organizador organizador = (Organizador) usuarioActual;
                Date nuevaFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(nuevaFechaStr);
                Date fechaActual = new Date();
                if (!nuevaFecha.after(fechaActual)) {
                    return "La fecha del evento no puede ser igual o menor que la fecha actual.";
                }
                Evento eventoModificado = new Evento(nuevoNombre, nuevaDescripcion, nuevaFecha,
                        nuevaCantidadTickets, nuevoPrecioTicket); // Establecer estado
                eventoModificado.setId(eventoIdModificar); // Asignar ID al evento modificado
                eventoModificado.setOrganizadorId(organizador.getId()); // Establecer organizador_id
                organizador.modificarEvento(eventoModificado);
                return "Evento modificado con éxito.";
            } catch (ParseException e) {
                return "Formato de fecha incorrecto.";
            }
        }
        return "No tiene permisos para modificar un evento.";
    }

    public String eliminarEvento(int eventoIdEliminar) {
        if (usuarioActual instanceof Organizador) {
            Organizador organizador = (Organizador) usuarioActual;
            organizador.eliminarEvento(eventoIdEliminar);
            return "Evento eliminado con éxito.";
        }
        return "No tiene permisos para eliminar un evento.";
    }

    public String obtenerDetallesCompra() {
        List<Compra> compras = cargarComprasRealizadas();
        if (compras == null || compras.isEmpty()) {
            return "No hay compras realizadas.";
        }

        StringBuilder detallesCompras = new StringBuilder();
        for (Compra compra : compras) {
            detallesCompras.append(String.format("Compra ID: %d, Fecha: %s, Total: %.2f, Estado: %s%n",
                    compra.getId(), compra.getFecha().toString(), compra.getTotal(), compra.getEstado()));

            for (DetalleCompra detalle : compra.getDetalles()) {
                detallesCompras.append(String.format("\tEvento: %s, Cantidad: %d, Precio por entrada: %.2f%n",
                        detalle.getNombreEvento(), detalle.getCantidad(), detalle.getPrecio()));
            }
        }
        return detallesCompras.toString();
    }
}
