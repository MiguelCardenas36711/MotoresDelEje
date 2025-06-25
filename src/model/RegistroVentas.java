package model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

public class RegistroVentas {
    private static final String ARCHIVO = "ventas.txt";

    public static void registrarVenta(Cliente cliente, Vehiculo vehiculo, LocalDate fechaVenta) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO, true))) {
            pw.printf("%s;%s;%s;%s;%s;%s;%.0f;%s%n",
                    fechaVenta,
                    cliente.getCedula(),
                    cliente.getNombre() + " " + cliente.getApellido(),
                    vehiculo.getPlaca(),
                    vehiculo.getMarca() + " " + vehiculo.getModelo(),
                    vehiculo.getColor(),
                    vehiculo.getPrecio(),
                    vehiculo.getTipoCombustible()
            );
        } catch (IOException e) {
            System.out.println("Error al registrar la venta: " + e.getMessage());
        }
    }
}
