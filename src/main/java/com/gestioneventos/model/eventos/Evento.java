package com.gestioneventos.model.eventos;

import java.util.Date;

public class Evento {
    private int id;
    private String nombre;
    private String descripcion;
    private Date fecha;
    private int cantidadTickets;
    private double precioTicket;
    private String estado;
    private int organizadorId;

    public Evento(int id, String nombre, String descripcion, Date fecha, int cantidadTickets, double precioTicket,
            String estado) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.cantidadTickets = cantidadTickets;
        this.precioTicket = precioTicket;
        this.estado = estado;
    }

    public Evento(String nombre, String descripcion, Date fecha, int cantidadTickets, double precioTicket) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.cantidadTickets = cantidadTickets;
        this.precioTicket = precioTicket;
    }

    public Evento() {
    }

    public int getOrganizadorId() {
        return organizadorId;
    }

    public void setOrganizadorId(int organizadorId) {
        this.organizadorId = organizadorId;
    }

    // Otros getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getCantidadTickets() {
        return cantidadTickets;
    }

    public void setCantidadTickets(int cantidadTickets) {
        this.cantidadTickets = cantidadTickets;
    }

    public double getPrecioTicket() {
        return precioTicket;
    }

    public void setPrecioTicket(double precioTicket) {
        this.precioTicket = precioTicket;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Evento{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fecha=" + fecha +
                ", cantidadTickets=" + cantidadTickets +
                ", precioTicket=" + precioTicket +
                ", estado='" + estado + '\'' +
                ", organizadorId=" + organizadorId +
                '}';
    }
}
