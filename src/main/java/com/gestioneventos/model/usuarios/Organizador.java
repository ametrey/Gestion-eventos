package com.gestioneventos.model.usuarios;

import com.gestioneventos.dao.EventoDAO;
import com.gestioneventos.model.eventos.Evento;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Organizador extends Usuario {
    private EventoDAO eventoDAO;

    public Organizador(int id, String usuario, String password, String nombre, String apellido, String dni) {
        super(id, usuario, password, nombre, apellido, dni, 1); // 1 para tipoUsuario Organizador
        this.eventoDAO = new EventoDAO();
    }

    public void crearEvento(Evento evento) {
        evento.setOrganizadorId(this.getId());
        eventoDAO.addEvento(evento);
    }

    public List<Evento> listarEventos() {
        try {
            return eventoDAO.getEventosByOrganizador(this.getId());
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void modificarEvento(Evento evento) {
        try {
            eventoDAO.updateEvento(evento);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarEvento(int eventoId) {
        eventoDAO.deleteEvento(eventoId);
    }
}
