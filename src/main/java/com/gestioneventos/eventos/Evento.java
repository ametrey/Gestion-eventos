package com.gestioneventos.eventos;

import java.util.Date;

public class Evento {
    private int id;
    private String nombre;
    private String descripcion;
    private Date fecha;
    private String estado;
    private int cantidadTickets;
    private double precio;

    public Evento(String nombre, String descripcion, Date fecha, int cantidadTickets, double precio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.estado = "Pendiente";
        this.cantidadTickets = cantidadTickets;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getEstado() {
        return estado;
    }

    public int getCantidadTickets() {
        return cantidadTickets;
    }

    public double getPrecio() {
        return precio;
    }
}
