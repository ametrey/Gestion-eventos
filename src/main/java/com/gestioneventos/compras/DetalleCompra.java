package com.gestioneventos.compras;

public class DetalleCompra {
    private int eventoId;
    private int cantidad;
    private double precioUnitario;
    private String nombreEvento;

    public DetalleCompra(int eventoId, int cantidad, double precioUnitario, String nombreEvento) {
        this.eventoId = eventoId;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.nombreEvento = nombreEvento;
    }

    public int getEventoId() {
        return eventoId;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }
}
