package model;

import java.util.ArrayList;
import java.util.List;

public class InventarioVehiculos {
    private List<Vehiculo> listaVehiculos;

    public InventarioVehiculos() {
        this.listaVehiculos = new ArrayList<>();
    }

    public void agregarVehiculo(Vehiculo vehiculo) {
        listaVehiculos.add(vehiculo);
    }

    public List<Vehiculo> obtenerTodos() {
        return listaVehiculos;
    }

    public void eliminarVehiculo(Vehiculo vehiculo) {
        listaVehiculos.remove(vehiculo);
    }
}
