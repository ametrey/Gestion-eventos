
package com.gestioneventos.model.usuarios;

import com.gestioneventos.dao.CompraDAO;
import com.gestioneventos.dao.EventoDAO;
import com.gestioneventos.model.compras.Compra;
import com.gestioneventos.model.compras.DetalleCompra;
import com.gestioneventos.model.eventos.Evento;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import java.sql.SQLException;

public class Comprador extends Usuario {
    private List<Compra> comprasRealizadas;
    private CompraDAO compraDAO;
    private EventoDAO eventoDAO;
    private static Scanner scanner = new Scanner(System.in);

    public Comprador(int id, String usuario, String password, String nombre, String apellido, String dni) {
        super(id, usuario, password, nombre, apellido, dni, 2);
        this.compraDAO = new CompraDAO();
        this.eventoDAO = new EventoDAO();
        this.comprasRealizadas = new ArrayList<>();
    }

    public boolean comprarTicket(int eventoId, int cantidad) {
        Evento evento = getEventoById(eventoId);
        if (evento == null) {
            System.out.println("Evento no encontrado.");
            return false;
        }

        if (evento.getCantidadTickets() < cantidad) {
            System.out.println("No hay suficientes tickets disponibles.");
            return false;
        }

        double precioTotal = evento.getPrecioTicket() * cantidad;
        System.out.printf("El total es %.2f. ¿Desea confirmar la compra? (s/n): ", precioTotal);
        String confirmacion = scanner.nextLine();

        if (confirmacion.equalsIgnoreCase("s")) {
            Compra compra = new Compra(this.getId(), new Date(), precioTotal, "Completo");

            DetalleCompra detalle = new DetalleCompra(eventoId, cantidad, evento.getPrecioTicket());
            List<DetalleCompra> detalles = new ArrayList<>();
            detalles.add(detalle);
            compra.setDetalles(detalles);

            compraDAO.addCompra(compra);
            this.comprasRealizadas.add(compra);

            // Actualizar la cantidad de tickets disponibles en el evento
            evento.setCantidadTickets(evento.getCantidadTickets() - cantidad);
            try {
                eventoDAO.updateEvento(evento);
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }

            System.out.println("Compra realizada con éxito.");
            return true;
        } else {
            System.out.println("Compra cancelada.");
            return false;
        }
    }

    public void cargarComprasRealizadas() {
        comprasRealizadas = compraDAO.getComprasByUsuarioId(this.getId());
    }

    public List<Compra> getComprasRealizadas() {
        return comprasRealizadas;
    }

    private Evento getEventoById(int eventoId) {
        return eventoDAO.getEventoById(eventoId);
    }

    public List<Evento> listarEventos() {
        return eventoDAO.getAllEventos();
    }
}