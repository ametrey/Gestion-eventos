package com.gestioneventos.compras;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gestioneventos.usuarios.Comprador;

public class Compra {
    private int id;
    private Comprador comprador;
    private Date fecha;
    private double total;
    private String estado;
    private List<DetalleCompra> detalles;

    public Compra(int id, Comprador comprador, Date fecha, double total, String estado) {
        this.id = id;
        this.comprador = comprador;
        this.fecha = fecha;
        this.total = total;
        this.estado = estado;
        this.detalles = new ArrayList<>();
    }

    public void agregarDetalle(int eventoId, int cantidad, double precioUnitario, String nombreEvento) {
        this.detalles.add(new DetalleCompra(eventoId, cantidad, precioUnitario, nombreEvento));
    }

    public int getId() {
        return id;
    }

    public Comprador getComprador() {
        return comprador;
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
}
