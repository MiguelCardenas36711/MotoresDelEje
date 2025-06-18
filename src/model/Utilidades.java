package model;

import java.text.DecimalFormat;

public class Utilidades {
    public static String formatearPrecio(double precio) {
        if (precio >= 1_000_000) {
            double millones = precio / 1_000_000;
            DecimalFormat formato = new DecimalFormat("#,##0.0");
            return "$" + (millones % 1 == 0 ? (int) millones + "M" : formato.format(millones) + "M");
        }
        return "$" + String.format("%,.0f", precio); // Para valores menores a un millón
    }
}
