package model;

public class Moto extends Vehiculo {
    private boolean tieneSidecar;

    public Moto(String marca, String modelo, int anio, double precio, boolean tieneSidecar) {
        super(marca, modelo, anio, precio);
        this.tieneSidecar = tieneSidecar;
    }

    @Override
    public String obtenerTipo() {
        return "Moto";
    }

    @Override
    public String mostrarInformacion() {
        return "Moto: " + marca + " " + modelo + " (" + anio + "), " +
                (tieneSidecar ? "con" : "sin") + " sidecar, $" + precio;
    }

    public boolean isTieneSidecar() { return tieneSidecar; }
    public void setTieneSidecar(boolean tieneSidecar) { this.tieneSidecar = tieneSidecar; }
}
