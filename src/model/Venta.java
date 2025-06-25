package model;

import java.time.LocalDate;

public class Venta {
    private Cliente comprador;
    private Vehiculo vehiculo;
    private LocalDate fechaVenta;

    public Venta(Cliente comprador, Vehiculo vehiculo, LocalDate fechaVenta) {
        this.comprador = comprador;
        this.vehiculo = vehiculo;
        this.fechaVenta = fechaVenta;
    }

    // Getters
    public Cliente getComprador() { return comprador; }
    public Vehiculo getVehiculo() { return vehiculo; }
    public LocalDate getFechaVenta() { return fechaVenta; }
}
