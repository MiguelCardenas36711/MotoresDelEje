package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
//Inventario de Vehiculos
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

    public List<Vehiculo> buscarPorMarca(String marca) {
        return listaVehiculos.stream()
                .filter(v -> v.getMarca().equalsIgnoreCase(marca))
                .collect(Collectors.toList());
    }

    public void eliminarVehiculo(Vehiculo vehiculo) {
        listaVehiculos.remove(vehiculo);
    }

    public int contarVehiculos() {
        return listaVehiculos.size();
    }

    // Metodo de ejemplo para mostrar el inventario
    public void imprimirInventario() {
        for (Vehiculo v : listaVehiculos) {
            System.out.println(v.mostrarInformacion());
        }
    }
}
