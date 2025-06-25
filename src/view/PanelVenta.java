package view;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class PanelVenta extends JPanel {
    private JComboBox<String> comboVehiculo;
    private final Map<String, Vehiculo> mapaVehiculos = new LinkedHashMap<>();

    private JTextField txtNombre, txtApellido, txtCedula, txtDireccion, txtTelefono, txtCorreo;
    private JComboBox<String> comboSexo;

    public PanelVenta(Runnable onContinuarVenta) {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createTitledBorder("Registrar Venta"));

        JPanel formulario = new JPanel(new GridLayout(0, 2, 10, 8));
        formulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Componentes
        comboVehiculo = new JComboBox<>();
        cargarVehiculosDisponibles();

        txtNombre = new JTextField();
        txtApellido = new JTextField();
        txtCedula = new JTextField();
        comboSexo = new JComboBox<>(new String[]{"M", "F", "Otro"});
        txtDireccion = new JTextField();
        txtTelefono = new JTextField();
        txtCorreo = new JTextField();

        // Añadir al formulario
        formulario.add(new JLabel("Vehículo Disponible:"));
        formulario.add(comboVehiculo);
        formulario.add(new JLabel("Nombre:"));
        formulario.add(txtNombre);
        formulario.add(new JLabel("Apellido:"));
        formulario.add(txtApellido);
        formulario.add(new JLabel("Cédula:"));
        formulario.add(txtCedula);
        formulario.add(new JLabel("Sexo:"));
        formulario.add(comboSexo);
        formulario.add(new JLabel("Dirección:"));
        formulario.add(txtDireccion);
        formulario.add(new JLabel("Teléfono:"));
        formulario.add(txtTelefono);
        formulario.add(new JLabel("Correo:"));
        formulario.add(txtCorreo);

        // Botón continuar
        JButton btnContinuar = new JButton("Continuar con Contrato");
        btnContinuar.addActionListener(e -> {
            Cliente cliente = construirCliente();
            Vehiculo vehiculo = obtenerVehiculoSeleccionado();

            if (cliente == null || vehiculo == null) return;

            // Abrir contrato
            VentanaContrato contrato = new VentanaContrato(cliente, vehiculo, () -> {
                onContinuarVenta.run(); // Cambia de vista o recarga historial
                limpiarCampos();        // Limpia formulario
            });
            contrato.setVisible(true);
        });

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBoton.add(btnContinuar);

        add(formulario, BorderLayout.CENTER);
        add(panelBoton, BorderLayout.SOUTH);
    }

    private void cargarVehiculosDisponibles() {
        for (Vehiculo v : InventarioSingleton.getInstancia().obtenerTodos()) {
            if (v.getEstado() == Estado.DISPONIBLE) {
                String clave = v.getPlaca() + " - " + v.getMarca() + " " + v.getModelo();
                comboVehiculo.addItem(clave);
                mapaVehiculos.put(clave, v);
            }
        }
    }

    private Vehiculo obtenerVehiculoSeleccionado() {
        String clave = (String) comboVehiculo.getSelectedItem();
        if (clave == null) {
            JOptionPane.showMessageDialog(this, "Selecciona un vehículo para vender.");
            return null;
        }
        return mapaVehiculos.get(clave);
    }

    private Cliente construirCliente() {
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String cedula = txtCedula.getText().trim();

        if (nombre.isEmpty() || cedula.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nombre y cédula son obligatorios.");
            return null;
        }

        return new Cliente(
                nombre,
                apellido,
                cedula,
                (String) comboSexo.getSelectedItem(),
                txtDireccion.getText().trim(),
                txtTelefono.getText().trim(),
                txtCorreo.getText().trim()
        );
    }

    public void recargarVehiculos() {
        comboVehiculo.removeAllItems();
        mapaVehiculos.clear();
        for (Vehiculo v : InventarioSingleton.getInstancia().obtenerTodos()) {
            if (v.getEstado() == Estado.DISPONIBLE) {
                String clave = v.getPlaca() + " - " + v.getMarca() + " " + v.getModelo();
                comboVehiculo.addItem(clave);
                mapaVehiculos.put(clave, v);
            }
        }
    }

    private void limpiarCampos() {
        comboVehiculo.setSelectedIndex(0);
        txtNombre.setText("");
        txtApellido.setText("");
        txtCedula.setText("");
        comboSexo.setSelectedIndex(0);
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
    }

}
