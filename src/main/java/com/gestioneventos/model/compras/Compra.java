package com.gestioneventos.model.compras;

import java.util.Date;
import java.util.List;

public class Compra {
    private int id;
    private int usuarioId;
    private Date fecha;
    private double total;
    private String estado;
    private List<DetalleCompra> detalles;

    public Compra(int usuarioId, Date fecha, double total, String estado) {
        this.usuarioId = usuarioId;
        this.fecha = fecha;
        this.total = total;
        this.estado = estado;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public Date getFecha() {
        return fecha;
    }

    public double getTotal() {
        return total;
    }

    public String getEstado() {
        return estado;
    }

    public List<DetalleCompra> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleCompra> detalles) {
        this.detalles = detalles;
    }
}
