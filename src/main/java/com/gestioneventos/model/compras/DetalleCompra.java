package com.gestioneventos.model.compras;

public class DetalleCompra {
    private int eventoId;
    private int cantidad;
    private double precio;
    private String nombreEvento; // Campo para el nombre del evento

    public DetalleCompra(int eventoId, int cantidad, double precio, String nombreEvento) {
        this.eventoId = eventoId;
        this.cantidad = cantidad;
        this.precio = precio;
        this.nombreEvento = nombreEvento;
    }

    // Constructor sin nombreEvento para compatibilidad
    public DetalleCompra(int eventoId, int cantidad, double precio) {
        this(eventoId, cantidad, precio, null);
    }

    // Getters y Setters
    public int getEventoId() {
        return eventoId;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }
}
