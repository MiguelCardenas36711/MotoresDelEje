package model;

public class InventarioSingleton {
    private static InventarioVehiculos instancia;

    private InventarioSingleton() {}

    public static InventarioVehiculos getInstancia() {
        if (instancia == null) {
            instancia = new InventarioVehiculos();
        }
        return instancia;
    }
}

// .