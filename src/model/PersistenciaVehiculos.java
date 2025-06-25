package model;

import java.io.*;
import java.time.LocalDate;
import java.util.List;

public class PersistenciaVehiculos {
    private static final String ARCHIVO = "vehiculos.txt";

    public static void guardarInventario(List<Vehiculo> listaVehiculos) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("vehiculos.txt"))) {
            for (Vehiculo v : listaVehiculos) {
                String tipo = v.obtenerTipo();
                String lineaBase = String.join(",",
                        tipo,
                        v.getPlaca(),
                        v.getMarca(),
                        v.getModelo(),
                        String.valueOf(v.getAnio()),
                        v.getColor(),
                        String.valueOf(v.getKilometraje()),
                        v.getFechaIngreso().toString(),
                        (v.getFechaVenta() != null ? v.getFechaVenta().toString() : "null"),
                        String.valueOf(v.getPrecio()),
                        v.getEstado().name(),
                        v.getTipoCombustible().name(),
                        v.getTransmision().name(),
                        String.valueOf(v.getNumeroDuenoAnterior())
                );

                if (v instanceof Carro c) {
                    pw.println(lineaBase + "," + c.getNumeroPuertas());
                } else if (v instanceof Moto m) {
                    pw.println(lineaBase + "," + m.isTieneSidecar());
                }
            }
        } catch (IOException e) {
            System.out.println("Error al guardar inventario: " + e.getMessage());
        }
    }


    public static void cargarInventario(InventarioVehiculos inventario) {
        try (BufferedReader br = new BufferedReader(new FileReader("vehiculos.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                String tipo = datos[0];
                String placa = datos[1];
                String marca = datos[2];
                String modelo = datos[3];
                int anio = Integer.parseInt(datos[4]);
                String color = datos[5];
                double kilometraje = Double.parseDouble(datos[6]);
                LocalDate fechaIngreso = LocalDate.parse(datos[7]);
                LocalDate fechaVenta = datos[8].equals("null") ? null : LocalDate.parse(datos[8]);
                double precio = Double.parseDouble(datos[9]);
                Estado estado = Estado.valueOf(datos[10]);
                Combustible combustible = Combustible.valueOf(datos[11]);
                Transmision transmision = Transmision.valueOf(datos[12]);
                int duenos = Integer.parseInt(datos[13]);

                Vehiculo v;
                if (tipo.equals("Carro")) {
                    int puertas = Integer.parseInt(datos[14]);
                    v = new Carro(placa, marca, modelo, anio, color, kilometraje,
                            fechaIngreso, precio, estado, combustible,
                            transmision, duenos, puertas);
                } else {
                    boolean sidecar = Boolean.parseBoolean(datos[14]);
                    v = new Moto(placa, marca, modelo, anio, color, kilometraje,
                            fechaIngreso, precio, estado, combustible,
                            transmision, duenos, sidecar);
                }

                if (fechaVenta != null) {
                    v.registrarVenta(fechaVenta);
                }

                inventario.agregarVehiculo(v);
            }
        } catch (IOException e) {
            System.out.println("No se encontró el archivo: se cargará inventario vacío.");
        }
    }

}
