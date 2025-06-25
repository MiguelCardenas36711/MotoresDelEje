package model;

import java.time.LocalDate;

public abstract class Vehiculo {
    protected String placa;
    protected String marca;
    protected String modelo;
    protected int anio;
    protected String color;
    protected double kilometraje;
    protected LocalDate fechaIngreso;
    protected LocalDate fechaVenta; // puede ser null si no se ha vendido
    protected double precio;
    protected Estado estado;
    protected Combustible tipoCombustible;
    protected Transmision transmision;
    protected int numeroDuenoAnterior;

    public Vehiculo(String placa, String marca, String modelo, int anio,
                    String color, double kilometraje, LocalDate fechaIngreso,
                    double precio, Estado estado, Combustible tipoCombustible,
                    Transmision transmision, int numeroDuenoAnterior) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.color = color;
        this.kilometraje = kilometraje;
        this.fechaIngreso = fechaIngreso;
        this.precio = precio;
        this.estado = estado;
        this.tipoCombustible = tipoCombustible;
        this.transmision = transmision;
        this.numeroDuenoAnterior = numeroDuenoAnterior;
        this.fechaVenta = null; // se puede actualizar después
    }

    public abstract String obtenerTipo(); // metodo polimórfico

    public abstract String mostrarInformacion(); // metodo polimórfico

    // Getters y setters
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public int getAnio() { return anio; }
    public void setAnio(int anio) { this.anio = anio; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public double getKilometraje() { return kilometraje; }
    public void setKilometraje(double kilometraje) { this.kilometraje = kilometraje; }

    public LocalDate getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(LocalDate fechaIngreso) { this.fechaIngreso = fechaIngreso; }

    public LocalDate getFechaVenta() { return fechaVenta; }
    public void setFechaVenta(LocalDate fechaVenta) { this.fechaVenta = fechaVenta; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }

    public Combustible getTipoCombustible() { return tipoCombustible; }
    public void setTipoCombustible(Combustible tipoCombustible) { this.tipoCombustible = tipoCombustible; }

    public Transmision getTransmision() { return transmision; }
    public void setTransmision(Transmision transmision) { this.transmision = transmision; }

    public int getNumeroDuenoAnterior() { return numeroDuenoAnterior; }
    public void setNumeroDuenoAnterior(int numeroDuenoAnterior) { this.numeroDuenoAnterior = numeroDuenoAnterior; }


    public void registrarVenta(LocalDate fechaVenta) {
        this.fechaVenta = fechaVenta;
        this.estado = Estado.VENDIDO;
    }
}
