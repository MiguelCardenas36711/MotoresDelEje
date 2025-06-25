package view;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class VentanaContrato extends JDialog {
    private final Cliente cliente;
    private final Vehiculo vehiculo;
    private final Runnable onVentaConfirmada;

    private JTextArea areaContrato;
    private JTextField txtFirma;

    public VentanaContrato(Cliente cliente, Vehiculo vehiculo, Runnable onVentaConfirmada) {
        this.cliente = cliente;
        this.vehiculo = vehiculo;
        this.onVentaConfirmada = onVentaConfirmada;

        setTitle("Contrato de Venta");
        setSize(600, 500);
        setModal(true);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(10, 10));

        areaContrato = new JTextArea(generarContrato());
        areaContrato.setEditable(false);
        areaContrato.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JScrollPane scroll = new JScrollPane(areaContrato);

        JPanel firmaPanel = new JPanel(new BorderLayout(5, 5));
        txtFirma = new JTextField();
        JButton btnFirmar = new JButton("Firmar contrato");

        btnFirmar.addActionListener(e -> firmarContrato());

        firmaPanel.add(new JLabel("Digite el nombre para firmar:"), BorderLayout.NORTH);
        firmaPanel.add(txtFirma, BorderLayout.CENTER);
        firmaPanel.add(btnFirmar, BorderLayout.EAST);

        add(scroll, BorderLayout.CENTER);
        add(firmaPanel, BorderLayout.SOUTH);
    }

    private String generarContrato() {
        return String.format("""
        CONTRATO DE COMPRAVENTA DE VEHÍCULO USADO

        Fecha: %s

        VENDEDOR:
        Concesionario Motores del Eje

        COMPRADOR:
        Nombre: %s %s
        Cédula: %s
        Dirección: %s
        Teléfono: %s
        Correo: %s

        VEHÍCULO:
        Placa: %s
        Marca/Modelo: %s %s
        Año: %d
        Color: %s
        Kilometraje: %.0f km
        Precio: $%,.0f

        El comprador declara que ha revisado el estado del vehículo y acepta las condiciones actuales.
        Ambas partes acuerdan la transferencia de propiedad bajo los términos legales aplicables.

        FIRMA DEL COMPRADOR: ______________________________

        """,
                LocalDate.now(),
                cliente.getNombre(), cliente.getApellido(),
                cliente.getCedula(), cliente.getDireccion(),
                cliente.getTelefono(), cliente.getCorreo(),
                vehiculo.getPlaca(), vehiculo.getMarca(),
                vehiculo.getModelo(), vehiculo.getAnio(),
                vehiculo.getColor(), vehiculo.getKilometraje(),
                vehiculo.getPrecio());
    }

    private void firmarContrato() {
        String firma = txtFirma.getText().trim();

        if (!firma.equalsIgnoreCase(cliente.getNombre())) {
            JOptionPane.showMessageDialog(this,
                    "Firma inválida. Debes digitar el mismo nombre que el comprador.",
                    "Error de firma", JOptionPane.ERROR_MESSAGE);
            return;
        }

        vehiculo.setEstado(Estado.VENDIDO);
        vehiculo.setFechaVenta(LocalDate.now());

        //Registro de la venta:
        RegistroVentas.registrarVenta(cliente, vehiculo, LocalDate.now());

        PersistenciaVehiculos.guardarInventario(InventarioSingleton.getInstancia().obtenerTodos());

        JOptionPane.showMessageDialog(this,
                "¡Contrato firmado con éxito!\nVenta registrada.",
                "Venta confirmada", JOptionPane.INFORMATION_MESSAGE);

        onVentaConfirmada.run(); // refresca tabla o vista
        dispose();
    }
}
