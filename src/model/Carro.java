package model;

import java.time.LocalDate;

public class Carro extends Vehiculo {
    private int numeroPuertas;

    public Carro(String placa, String marca, String modelo, int anio, String color, double kilometraje,
                 LocalDate fechaIngreso, double precio, Estado estado, Combustible combustible,
                 Transmision transmision, int numeroDuenoAnterior, int numeroPuertas) {
        super(placa, marca, modelo, anio, color, kilometraje, fechaIngreso, precio,
                estado, combustible, transmision, numeroDuenoAnterior);
        this.numeroPuertas = numeroPuertas;
    }

    @Override
    public String obtenerTipo() {
        return "Carro";
    }

    public int getNumeroPuertas() { return numeroPuertas; }
    public void setNumeroPuertas(int numeroPuertas) { this.numeroPuertas = numeroPuertas; }
}
