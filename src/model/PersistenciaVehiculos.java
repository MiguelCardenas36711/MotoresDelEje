package model;

import java.io.*;
import java.util.List;

public class PersistenciaVehiculos {
    private static final String ARCHIVO = "vehiculos.txt";

    public static void guardarInventario(List<Vehiculo> listaVehiculos) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO))) {
            for (Vehiculo v : listaVehiculos) {
                String tipo = v instanceof Carro ? "Carro" : "Moto";
                String detalle = (v instanceof Carro c)
                        ? c.getNumeroPuertas() + ""
                        : (v instanceof Moto m) ? (m.isTieneSidecar() ? "true" : "false") : "";

                pw.println(tipo + "," + v.getMarca() + "," + v.getModelo() + "," + v.getAnio() + "," + v.getPrecio() + "," + detalle);
            }
        } catch (IOException e) {
            System.out.println("Error al guardar el inventario: " + e.getMessage());
        }
    }

    public static void cargarInventario(InventarioVehiculos inventario) {
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                String tipo = datos[0];
                String marca = datos[1];
                String modelo = datos[2];
                int anio = Integer.parseInt(datos[3]);
                double precio = Double.parseDouble(datos[4]);

                Vehiculo v = tipo.equals("Carro")
                        ? new Carro(marca, modelo, anio, precio, Integer.parseInt(datos[5]))
                        : new Moto(marca, modelo, anio, precio, Boolean.parseBoolean(datos[5]));

                inventario.agregarVehiculo(v);
            }
        } catch (IOException e) {
            System.out.println("No se encontró el archivo, iniciando vacío.");
        }
    }
}
