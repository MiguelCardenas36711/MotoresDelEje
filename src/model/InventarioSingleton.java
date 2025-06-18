package model;

public class InventarioSingleton {
    private static InventarioVehiculos instancia;

    public static InventarioVehiculos getInstancia() {
        if (instancia == null) {
            instancia = new InventarioVehiculos();
            PersistenciaVehiculos.cargarInventario(instancia); // Carga al inicio
        }
        return instancia;
    }
}

