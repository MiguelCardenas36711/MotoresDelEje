package model;

import java.time.LocalDate;

public class Moto extends Vehiculo {
    private boolean tieneSidecar;

    public Moto(String placa, String marca, String modelo, int anio, String color, double kilometraje,
                LocalDate fechaIngreso, double precio, Estado estado, Combustible combustible,
                Transmision transmision, int numeroDuenoAnterior, boolean tieneSidecar) {
        super(placa, marca, modelo, anio, color, kilometraje, fechaIngreso, precio,
                estado, combustible, transmision, numeroDuenoAnterior);
        this.tieneSidecar = tieneSidecar;
    }

    @Override
    public String obtenerTipo() {
        return "Moto";
    }

    public boolean isTieneSidecar() { return tieneSidecar; }
    public void setTieneSidecar(boolean tieneSidecar) { this.tieneSidecar = tieneSidecar; }
}
