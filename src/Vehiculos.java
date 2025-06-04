public class Vehiculos {

    // ==========
    // Atributos:
    // ==========
    private String placa;
    private String marca;
    private String modelo;
    private int año;
    private String color;
    private int kilometraje;
    private String fechaIngreso;
    private String fechaVenta;
    private double precio;
    private String estado; // Disponible / Vendido / Reservado
    private String tipoCombustible;
    private String transmision; // Manual / Automatica
    private int numeroDueñosAnteriores;

    // ============
    // Constructor:
    // ============
    public Vehiculos(String placa, String marca, String modelo, int año, String color, int kilometraje,
                     String fechaIngreso, String fechaVenta, double precio, String estado,
                     String tipoCombustible, String transmision, int numeroDueñosAnteriores) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.año = año;
        this.color = color;
        this.kilometraje = kilometraje;
        this.fechaIngreso = fechaIngreso;
        this.fechaVenta = fechaVenta;
        this.precio = precio;
        this.estado = estado;
        this.tipoCombustible = tipoCombustible;
        this.transmision = transmision;
        this.numeroDueñosAnteriores = numeroDueñosAnteriores;
    }

    // =========
    // Metodos:
    // =========
    public void actualizarInfoVehiculos(String nuevoColor, int nuevoKilometraje, double nuevoPrecio) {
        this.color = nuevoColor;
        this.kilometraje = nuevoKilometraje;
        this.precio = nuevoPrecio;
    }

    public void venta(String fechaVenta) {
        this.fechaVenta = fechaVenta;
        this.estado = "Vendido";
    }

    public void mostrarDetalles() {
        System.out.println("Vehiculo: " + marca + " " + modelo + " - Placa: " + placa);
        System.out.println("Año: " + año + ", Color: " + color + ", Kilometraje: " + kilometraje);
        System.out.println("Estado: " + estado + ", Precio: $" + precio);
    }

}