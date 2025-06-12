package model;

public class Carro extends Vehiculo {
    private int numeroPuertas;

    public Carro(String marca, String modelo, int anio, double precio, int numeroPuertas) {
        super(marca, modelo, anio, precio);
        this.numeroPuertas = numeroPuertas;
    }

    @Override
    public String obtenerTipo() {
        return "Carro";
    }

    @Override
    public String mostrarInformacion() {
        return "Carro: " + marca + " " + modelo + " (" + anio + "), " +
                numeroPuertas + " puertas, $" + precio;
    }

    public int getNumeroPuertas() { return numeroPuertas; }
    public void setNumeroPuertas(int numeroPuertas) { this.numeroPuertas = numeroPuertas; }
}
